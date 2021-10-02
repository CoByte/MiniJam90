package main.world.entities;

import main.misc.CollisionBox;
import main.misc.Timer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class MovingPlatform extends Entity {

    public static final float WIDTH = 50;
    public static final float HEIGHT = 20;

    private final PVector pointA;
    private final PVector pointB;

    private final float speed;
    private final Timer waitTimer;
    private boolean waiting = true;

    private boolean goingToB = false;

    public MovingPlatform(PApplet p, PVector pointA, PVector pointB, float speed, int endWait) {
        super(p, new CollisionBox(p, new PVector(WIDTH / 2, HEIGHT / 2)), pointA.copy());

        this.pointA = pointA;
        this.pointB = pointB;

        this.speed = speed;
        this.waitTimer = new Timer(endWait);
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
        P.rectMode(PConstants.CENTER);
        collider.display(position);
        P.circle(pointA.x, pointA.y, 5);
        P.circle(pointB.x, pointB.y, 5);
    }
}
