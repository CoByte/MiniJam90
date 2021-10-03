package main.particles;

import main.world.World;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;

public class FloatParticle extends Particle {

    private static final int RADIUS = 2;
    private static final int ALPHA_CHANGE = 5;

    private final Color COLOR;

    private int alpha;

    public FloatParticle(PApplet p, float x, float y, Color color, World world) {
        super(p, x, y, world);
        COLOR = color;

        position.add(PVector.fromAngle(p.random(PConstants.TWO_PI)).setMag(5));
        alpha = 254;
        velocity = PVector.fromAngle(P.random(PConstants.TWO_PI)).setMag(p.random(0.05f,0.2f));
    }

    @Override
    protected void move() {
        position.add(velocity);
    }

    @Override
    protected void display() {
        P.noStroke();
        P.fill(COLOR.getRGB(), alpha);
        P.rect(position.x, position.y, RADIUS, RADIUS);
        alpha -= ALPHA_CHANGE;
        if (alpha < 0) dead = true;
    }
}
