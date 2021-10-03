package main.world.entities;

import main.misc.CollisionBox;
import processing.core.PVector;

import java.awt.*;

public class Illusion extends Entity {

    public final Entity trueEntity;
    public final PVector offset;

    public Illusion(Entity trueEntity, PVector offset) {
        super(trueEntity.P, trueEntity.collider.copy(), trueEntity.position.copy());
        this.trueEntity = trueEntity;
        this.offset = offset;
    }

    @Override
    public void update() {}

    @Override
    public void draw() {
        P.pushMatrix();
        P.translate(offset.x, offset.y);

        P.tint(new Color(188, 124, 255, 195).getRGB());
        trueEntity.draw();

        P.tint(255);
        P.popMatrix();
    }

    public PVector getPosition() {
        return PVector.add(trueEntity.position, offset);
    }

    public CollisionBox getCollider() {
        return trueEntity.collider;
    }
}
