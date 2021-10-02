package main.world.entities;

import main.Main;
import main.misc.CollisionBox;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Fire extends Entity {

    public static final float WIDTH = 50;
    public static final float HEIGHT = 50;

    public Fire(PApplet p, PVector position) {
        super(p, new CollisionBox(p, new PVector(WIDTH, HEIGHT)), position);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        P.fill(255,0,0);
        P.rect(position.x, position.y, WIDTH, HEIGHT);

        if (Main.debug) collider.display(position);
    }
}
