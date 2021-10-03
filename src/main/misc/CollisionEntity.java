package main.misc;

import main.world.entities.Entity;
import processing.core.PApplet;
import processing.core.PVector;

public class CollisionEntity extends Entity {
    public CollisionEntity(CollisionBox collider, PVector position) {
        super(null, null, collider, position);
    }

    @Override
    public void update() {}

    @Override
    public void draw() {
        collider.display(position);
    }
}
