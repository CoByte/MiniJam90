package main.world.entities;

import main.Main;
import main.misc.CollisionBox;
import main.world.World;
import processing.core.PApplet;
import processing.core.PVector;

public class Spikes extends Entity {

    public Spikes(PApplet p, World world, PVector position) {
        super(p, world, new CollisionBox(
                p,
                new PVector(0, -10),
                new PVector(Main.TILE_SIZE, 10)
        ), position);
    }

    @Override
    public void draw() {
        P.fill(255, 0, 0);
        P.rect(position.x, position.y - 10, Main.TILE_SIZE, 10);
    }
}
