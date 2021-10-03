package main.world.entities;

import main.Main;
import main.misc.CollisionBox;
import main.misc.CollisionEntity;
import main.world.World;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

public abstract class Entity {
    public static final float DEFAULT_AURA = 10;

    public final PApplet P;
    public final World world;
    public final CollisionBox collider;
    public final PVector position;

    public CollisionEntity burnAura;
    public boolean onFire = false;
    public boolean flammable = false;

    public Entity(PApplet p, World world, CollisionBox collider, PVector position) {
        this.P = p;
        this.world = world;
        this.collider = collider;
        this.position = position;

        if (p != null) burnAura = new CollisionEntity(new CollisionBox(p,
                PVector.add(collider.OFFSET, new PVector(-DEFAULT_AURA, -DEFAULT_AURA)),
                PVector.add(collider.SIZE, new PVector(DEFAULT_AURA, DEFAULT_AURA).mult(2))),
                position);
    }

    public void update() {
        if (onFire) {
//            System.out.println(this);
            ArrayList<Entity> colliding = world.getCollidingEntities(burnAura);
//            System.out.println(colliding);
            for (Entity e: colliding) {
                if (!e.flammable || e == this) continue;
//                System.out.println("burn damn you!");
                if (P.random(60) < 1) {
                    e.onFire = true;
                }
            }
        }
    }

    public abstract void draw();

    public void highlight() {
        P.noFill();
        P.stroke(Color.BLUE.getRGB());
        P.strokeWeight(3);
        P.rect(position.x, position.y, collider.getRightEdge(), collider.getBottomEdge());
        P.strokeWeight(1);
    }
}
