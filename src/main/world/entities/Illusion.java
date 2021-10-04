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
        if (trueEntity.onFire) onFire = true;
        if (onFire) trueEntity.onFire = true;
        flammable = trueEntity.flammable;
        burnAura = new CollisionEntity(new CollisionBox(P,
                PVector.add(collider.OFFSET, new PVector(-DEFAULT_AURA, -DEFAULT_AURA)),
                PVector.add(collider.SIZE, new PVector(DEFAULT_AURA, DEFAULT_AURA).mult(2))),
                PVector.add(trueEntity.position, offset));
        super.update();

        if (trueEntity instanceof Lever) {
            Lever entity = (Lever) trueEntity;
            entity.innerUpdate(getPosition());
//            if (!entity.collidedWith) {
//                entity.innerUpdate(getPosition());
//                entity.stopFuckingMoving = entity.bottomedOut;
//            }
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
