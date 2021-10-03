package main.particles;

import main.world.World;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Particle {

    protected final PApplet P;
    protected final World world;

    protected PVector position;
    protected PVector velocity;
    protected boolean dead;

    public Particle(PApplet p, float x, float y, World world) {
        P = p;
        position = new PVector(x, y);
        this.world = world;
    }

    public void main() {
        if (dead) world.particles.remove(this);
        move();
        display();
    }

    protected abstract void move();

    protected abstract void display();
}
