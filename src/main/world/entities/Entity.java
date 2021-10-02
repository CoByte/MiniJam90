package main.world.entities;

import main.misc.CollisionBox;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

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

    public void highlight() {
        P.noFill();
        P.stroke(Color.BLUE.getRGB());
        P.strokeWeight(3);
        P.rect(position.x, position.y, collider.getRightEdge(), collider.getBottomEdge());
        P.strokeWeight(1);
    }
}
