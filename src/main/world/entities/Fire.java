package main.world.entities;

import main.Main;
import main.misc.Animator;
import main.misc.CollisionBox;
import main.misc.CollisionEntity;
import main.particles.FloatParticle;
import main.particles.GravityParticle;
import main.world.World;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

public class Fire extends Entity {

    public static final PVector SIZE = new PVector(50, 50);
    public static final PVector OFFSET = PVector.div(SIZE, -2);

    public final World world;

    private final Animator animator;

    public Fire(PApplet p, PVector position, World world) {
        super(p, world, new CollisionBox(p,
                new PVector(SIZE.x / -2, 0), new PVector(SIZE.x, SIZE.y / 2)), position);
        this.world = world;
        this.onFire = true;

        animator = new Animator(Main.animations.get("fire"), 10);
    }

    @Override
    public void draw() {
        animator.update();
        P.imageMode(PConstants.CENTER);
        P.image(animator.getCurrentFrame(), position.x, position.y, SIZE.x, SIZE.y);
        P.imageMode(Main.DEFAULT_MODE);
        if (Main.debug) {
            collider.display(position);
            burnAura.draw();
        }
//        if (P.random(5) < 1) {
//            PVector pos = getRandPos();
//            world.inFrontParticles.add(new FloatParticle(P,
//                    pos.x, pos.y, Color.RED, world.inFrontParticles));
//        }
//        if (P.random(5) < 1) {
//            PVector pos = getRandPos();
//            world.inFrontParticles.add(new FloatParticle(P,
//                    pos.x, pos.y, Color.YELLOW, world.inFrontParticles));
//        }
//        if (P.random(5) < 1) {
//            PVector pos = getRandPos();
//            world.inFrontParticles.add(new GravityParticle(P,
//                    pos.x, pos.y, Color.ORANGE, world.inFrontParticles));
//        }
    }
}
