package main.world.entities;

import processing.core.PVector;

import java.awt.*;

public class Illusion extends Entity {

    private final Entity trueEntity;
    private final PVector offset;

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

        P.tint(new Color(123, 0, 255, 195).getRGB());
        trueEntity.draw();

        P.tint(255);
        P.popMatrix();
    }
}
