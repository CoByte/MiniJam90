package main.world.entities;

import main.Main;
import main.misc.CollisionBox;
import main.misc.CollisionEntity;
import main.world.World;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;

public class Fire extends Entity {

    public static final PVector SIZE = new PVector(50, 50);
    public static final PVector OFFSET = PVector.div(SIZE, -2);

    public final World world;

    public Fire(PApplet p, PVector position, World world) {
        super(p, world, new CollisionBox(p, OFFSET, SIZE), position);
        this.world = world;
        this.onFire = true;
    }

    @Override
    public void draw() {
        P.rectMode(PConstants.CENTER);
        P.fill(255,0,0);
        P.rect(position.x, position.y, SIZE.x, SIZE.y);
        if (Main.debug) {
            collider.display(position);
            burnAura.draw();
        }
    }
}
