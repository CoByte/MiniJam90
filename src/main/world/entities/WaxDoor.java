package main.world.entities;

import main.Main;
import main.world.World;
import processing.core.PApplet;
import processing.core.PVector;

public class WaxDoor extends Door {
    public WaxDoor(PApplet p, World world, PVector position, PVector moveDir, float speed) {
        super(p, world, position, moveDir, speed);
        flammable = true;
    }

    @Override
    public void update() {
        if (onFire) {
            closed = false;
        }
        super.update();
    }

    @Override
    public void draw() {
        P.fill(255);
        P.rect(position.x, position.y, WIDTH, HEIGHT);
        if (Main.debug) collider.display(position);
    }
}
