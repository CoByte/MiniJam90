package main.particles;

import main.world.World;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;

public class GravityParticle extends Particle {

    private static final PVector ACCELERATION = new PVector(0, 0.1f);
    private static final int RADIUS = 2;
    private static final int ALPHA_CHANGE = 15;

    private final Color COLOR;

    private int alpha;

    public GravityParticle(PApplet p, float x, float y, Color color, World world) {
        super(p, x, y, world);
        COLOR = color;

        position.add(PVector.fromAngle(p.random(PConstants.TWO_PI)).setMag(5));
        alpha = 254;
        velocity = PVector.fromAngle(P.random(PConstants.TWO_PI)).setMag(p.random(1,2));
    }

    @Override
    public void move() {
        velocity.add(ACCELERATION);
        position.add(velocity);
    }

    @Override
    public void display() {
        P.noStroke();
        P.fill(COLOR.getRGB(), alpha);
        P.rect(position.x, position.y, RADIUS, RADIUS);
        alpha -= ALPHA_CHANGE;
        if (alpha < 0) dead = true;
    }
}
