package main.world.entities;

import main.Main;
import main.sound.SoundUtilities;
import main.world.World;
import processing.core.PApplet;
import processing.core.PVector;
import processing.sound.SoundFile;

public class LeverDoor extends Door {

    private final int numLevers;
    public int activeLevers;

    private final SoundFile slamSound;
    private boolean playedSound;

    public LeverDoor(PApplet p, World world, PVector position, float speed, int numLevers) {
        super(p, world, position, speed);
        this.numLevers = numLevers;
        sprite = Main.sprites.get("door");
        slamSound = Main.sounds.get("doorSlam");
    }

    @Override
    public void update() {
        super.update();
        position.y = Math.min(startY + HEIGHT, position.y);
        if (position.y - startY >= HEIGHT) {
            if (!playedSound) SoundUtilities.playSoundRandomSpeed(P, slamSound, 1);
            playedSound = true;
        } else playedSound = false;

        closed = activeLevers < numLevers;
    }
}
