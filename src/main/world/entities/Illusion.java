package main.world.entities;

import processing.core.PVector;

public class Illusion extends Entity {

    private final Entity trueEntity;
    private final PVector offset  = new PVector();

    public Illusion(Entity trueEntity) {
        super(trueEntity.P, trueEntity.collider.copy(), trueEntity.position.copy());
        this.trueEntity = trueEntity;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {

    }
}
