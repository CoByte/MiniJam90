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

    public final World world;

    private final Animator animator;

    public Fire(PApplet p, PVector position, World world) {
        super(p, world, new CollisionBox(p,
                new PVector(SIZE.x / -3, SIZE.y / 8),
                new PVector(SIZE.x / 1.5f, SIZE.y / 4)
                ), position);
        this.world = world;
        this.onFire = true;

        burnAura.collider.OFFSET.add(0, -10);

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
    }
}
