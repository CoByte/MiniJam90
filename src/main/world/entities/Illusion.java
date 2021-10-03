package main.world.entities;

import main.misc.CollisionBox;
import main.misc.CollisionEntity;
import main.particles.FloatParticle;
import main.particles.GravityParticle;
import processing.core.PVector;

import java.awt.*;

public class Illusion extends Entity {

    public static final Color PARTICLE_COLOR = new Color(124, 0, 255);

    public final Entity trueEntity;
    public final PVector offset;

    public Illusion(Entity trueEntity, PVector offset) {
        super(trueEntity.P, trueEntity.world, trueEntity.collider.copy(), trueEntity.position.copy());
        this.trueEntity = trueEntity;
        this.offset = offset;

        for (int i = 0; i < 50; i++) {
            PVector pos = getRandPos();
            world.inFrontParticles.add(new GravityParticle(P,
                    pos.x, pos.y, PARTICLE_COLOR, world.inFrontParticles));
        } for (int i = 0; i < 25; i++) {
            PVector pos = trueEntity.getRandPos();
            world.inFrontParticles.add(new GravityParticle(P,
                    pos.x, pos.y, PARTICLE_COLOR, world.inFrontParticles));
        }
    }

    @Override
    public void update() {
        onFire = trueEntity.onFire;
        flammable = trueEntity.flammable;
        burnAura = new CollisionEntity(new CollisionBox(P,
                PVector.add(collider.OFFSET, new PVector(-DEFAULT_AURA, -DEFAULT_AURA)),
                PVector.add(collider.SIZE, new PVector(DEFAULT_AURA, DEFAULT_AURA).mult(2))),
                PVector.add(trueEntity.position, offset));
        super.update();

        //whelp, I tried
        if (trueEntity instanceof Lever) {
            Lever lever = (Lever) trueEntity;
            lever.move(
                    Lever.getMovement(world, lever, lever.collider, getPosition()),
                    Lever.getDetected(world, new CollisionEntity(
                            new CollisionBox(P, new PVector(0, -5), new PVector(25, 5)),
                            getPosition()
                    ))
            );
        }
    }

    @Override
    public void draw() {
        P.pushMatrix();
        P.translate(offset.x, offset.y);

        P.tint(new Color(188, 124, 255, 195).getRGB());
        trueEntity.draw();
        P.tint(255);

        if (P.random(3) < 1) {
            PVector pos = trueEntity.getRandPos().add(offset);
            world.inFrontParticles.add(new FloatParticle(P,
                    pos.x, pos.y, PARTICLE_COLOR, world.inFrontParticles));
        }

        P.popMatrix();
    }

    public void drawLeverBack() {
        Lever lever = (Lever) trueEntity;

        P.pushMatrix();
        P.translate(offset.x, offset.y);

        P.tint(new Color(188, 124, 255, 195).getRGB());
        lever.drawBack();
        P.tint(255);

        if (P.random(6) < 1) {
            PVector pos = getRandPos();
            world.behindParticles.add(new FloatParticle(P,
                    pos.x, pos.y, PARTICLE_COLOR, world.inFrontParticles));
        }

        P.popMatrix();
    }

    public PVector getPosition() {
        return PVector.add(trueEntity.position, offset);
    }

    public CollisionBox getCollider() {
        return trueEntity.collider;
    }

    @Override
    public PVector getRandPos() {
        return trueEntity.getRandPos().add(offset);
    }
}
