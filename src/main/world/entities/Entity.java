package main.world.entities;

import main.Main;
import main.misc.CollisionBox;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Entity {
    public final PApplet P;
    public final CollisionBox collider;
    public final PVector position;

    public Entity(PApplet p, CollisionBox collider, PVector position) {
        this.P = p;
        this.collider = collider;
        this.position = position;
    }

    public abstract void update();
    public abstract void draw();
}
