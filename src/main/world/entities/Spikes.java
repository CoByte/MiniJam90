package main.world.entities;

import main.Main;
import main.misc.CollisionBox;
import main.world.World;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Spikes extends Entity {

    private final PImage sprite;

    public Spikes(PApplet p, World world, PVector position) {
        super(p, world, new CollisionBox(
                p,
                new PVector(0, -10),
                new PVector(Main.TILE_SIZE, 10)
        ), position);

        sprite = Main.sprites.get("spikes");
    }

    @Override
    public void draw() {
        P.image(sprite, position.x, position.y - Main.TILE_SIZE, Main.TILE_SIZE, Main.TILE_SIZE);
    }
}
