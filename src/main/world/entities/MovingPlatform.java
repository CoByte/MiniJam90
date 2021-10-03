package main.world.entities;

import main.Main;
import main.misc.CollisionBox;
import main.misc.Timer;
import main.world.World;
import main.misc.Utilities;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class MovingPlatform extends Entity {

    public static final float WIDTH = 150;
    public static final float HEIGHT = 50;

    public final float width;
    public final float height;

    public final float topSpeed;
    public boolean goingToB = false;
    public boolean waiting = true;

    private final PVector pointA;
    private final PVector pointB;

    private final Timer waitTimer;
    private final PImage sprite;

    public MovingPlatform(
            PApplet p,
            World world,
            float width,
            float height,
            PVector pointA,
            PVector pointB,
            float speed,
            int endWait)
    {
        super(p, world, new CollisionBox(p, new PVector(WIDTH / 2, HEIGHT / 2)), pointA.copy());
        if (pointA.x != pointB.x && pointA.y != pointB.x) throw new RuntimeException("No diagonal platforms allowed :(");

        this.width = width;
        this.height = height;

        this.pointA = pointA;
        this.pointB = pointB;

        this.topSpeed = speed;
        this.waitTimer = new Timer(endWait);

        sprite = Main.sprites.get("movingPlatform");
    }

    public MovingPlatform(
            PApplet p,
            World world,
            PVector pointA,
            PVector pointB,
            float speed,
            int endWait)
    {
        this(p, world, WIDTH, HEIGHT, pointA, pointB, speed, endWait);
    }

    @Override
    public void update() {
        if (waiting) {
            waitTimer.update();
            if (waitTimer.triggered(true)) {
                waiting = false;
                goingToB = !goingToB;
            }
        }

        PVector target = goingToB ? pointB : pointA;
        PVector starting = goingToB ? pointA : pointB;

        if (PVector.dist(position, target) < topSpeed) {
            position.set(target);
            waiting = true;
        } else {
            position.add(PVector.sub(target, starting).setMag(topSpeed));
        }
    }

    public PVector getVelocity() {
        float speed = topSpeed * 2;
        if (goingToB) speed *= -1;
        if (waiting) speed = 0;
        PVector velocity = PVector.sub(pointA, pointB).normalize();
        float angle = Utilities.getAngle(pointA, pointB);
        float proportion = Math.abs(1 - ((angle / PConstants.QUARTER_PI) % 2));
        velocity.div(proportion + 1);
        speed *= velocity.x;
        velocity.y *= -1;
        System.out.println(velocity);
        return new PVector(speed, velocity.y * topSpeed * 2);
    }

    @Override
    public void draw() {
        P.image(sprite, position.x, position.y, collider.getRightEdge(), collider.getBottomEdge());
    }
}
