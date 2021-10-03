package main.world.entities;

import main.Main;
import main.particles.GravityParticle;
import main.world.World;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

public class WaxDoor extends Door {
    public WaxDoor(PApplet p, World world, PVector position, float speed) {
        super(p, world, position, speed);
        flammable = true;
        sprite = Main.sprites.get("wax");
    }

    @Override
    public void update() {
        if (onFire) {
            closed = false;
        }
        if (!closed && P.random(2) < 1) {
            world.behindParticles.add(new GravityParticle(P,
                    P.random(position.x, position.x + WIDTH),
                    P.random(position.y, position.y + HEIGHT),
                    new Color(250, 255, 180), world.behindParticles));
        }
        super.update();
    }
}
