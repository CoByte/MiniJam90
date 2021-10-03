package main.particles;

import main.world.World;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public abstract class Particle {

    protected final PApplet P;
    protected final ArrayList<Particle> home;

    protected PVector position;
    protected PVector velocity;
    protected boolean dead;

    public Particle(PApplet p, float x, float y, ArrayList<Particle> home) {
        P = p;
        position = new PVector(x, y);
        this.home = home;
    }

    public void main() {
        if (dead) home.remove(this);
        move();
        display();
    }

    protected abstract void move();

    protected abstract void display();
}
