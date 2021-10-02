package main.world.entities;

import main.misc.CollisionBox;
import main.misc.Timer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class MovingPlatform extends Entity {

    public static final float WIDTH = 150;
    public static final float HEIGHT = 50;

    public final float width;
    public final float height;

    public final float speed;
    public boolean goingToB = false;
    public boolean waiting = true;

    private final PVector pointA;
    private final PVector pointB;

    private final Timer waitTimer;

    public MovingPlatform(PApplet p, float width, float height, PVector pointA, PVector pointB, float speed, int endWait) {
        super(p, new CollisionBox(p, new PVector(WIDTH / 2, HEIGHT / 2)), pointA.copy());

        this.width = width;
        this.height = height;

        this.pointA = pointA;
        this.pointB = pointB;

        this.speed = speed;
        this.waitTimer = new Timer(endWait);
    }

    public MovingPlatform(PApplet p, PVector pointA, PVector pointB, float speed, int endWait) {
        this(p, WIDTH, HEIGHT, pointA, pointB, speed, endWait);
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

        if (PVector.dist(position, target) < speed) {
            position.set(target);
            waiting = true;
        } else {
            position.add(PVector.sub(target, starting).setMag(speed));
        }
    }

    @Override
    public void draw() {
        collider.display(position);
        P.circle(pointA.x, pointA.y, 5);
        P.circle(pointB.x, pointB.y, 5);
    }
}
