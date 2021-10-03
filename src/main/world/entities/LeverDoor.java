package main.world.entities;

import main.Main;
import main.world.World;
import processing.core.PApplet;
import processing.core.PVector;

public class LeverDoor extends Door {

    private final int numLevers;
    public int activeLevers;

    public LeverDoor(PApplet p, World world, PVector position, float speed, int numLevers) {
        super(p, world, position, speed);
        this.numLevers = numLevers;
        sprite = Main.sprites.get("wax");
    }

    @Override
    public void update() {
        super.update();
        position.y = Math.min(startY + HEIGHT, position.y);

        closed = activeLevers < numLevers;
    }
}
