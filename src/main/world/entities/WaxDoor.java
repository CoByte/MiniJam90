package main.world.entities;

import main.Main;
import main.world.World;
import processing.core.PApplet;
import processing.core.PVector;

public class WaxDoor extends Door {
    public WaxDoor(PApplet p, World world, PVector position, PVector moveDir, float speed) {
        super(p, world, position, moveDir, speed);
        flammable = true;
        sprite = Main.sprites.get("wax");
    }

    @Override
    public void update() {
        if (onFire) {
            closed = false;
        }
        super.update();
    }
}
