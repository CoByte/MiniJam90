package main.world;

import main.Main;
import main.misc.Utilities;
import main.world.entities.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;

public class WorldBuilder {

    private static final ArrayList<String> hasBeenCreated = new ArrayList<>();

    private static World createWorld(PApplet p, String worldName) {
        if (hasBeenCreated.contains(worldName)) {
            hasBeenCreated.add(worldName);
            return new World(p, worldName, true);
        } else {
            return new World(p, worldName, false);
        }
    }

    public static World buildWorld(PApplet p, int num) {
        World world;
        float bottom = Main.BOARD_SIZE.y;
        float right = Main.BOARD_SIZE.x;
        switch (num) {
            case 0:
                world = createWorld(p, "1");
                world.entities.addAll(new ArrayList<>(Arrays.asList(
                        new LeverDoor(p, world, new PVector(50, bottom - 250), 4),
                        new Spikes(p, world, new PVector(500, bottom - 150)),
                        new Spikes(p, world, new PVector(550, bottom - 150)),
                        new Spikes(p, world, new PVector(600, bottom - 150)),
                        new Spikes(p, world, new PVector(650, bottom - 150)),
                        new Spikes(p, world, new PVector(700, bottom - 150)),
                        new Spikes(p, world, new PVector(750, bottom - 150)),
                        new Spikes(p, world, new PVector(800, bottom - 150)),
                        new Spikes(p, world, new PVector(850, bottom - 150))
                )));
                world.addDialogue(
                        new Dialogue("YOU MAY NOW BEGIN YOUR RUNECASTING 101 FINAL", 200, 200),
                        new Dialogue("Ugh, it's too early for this..."),
                        new Dialogue("CHAMBER 1: ILLUSIONS", 200, 200),
                        new Dialogue("Alright, Illusions, these are easy..."),
                        new Dialogue("Just draw the Illusion character!"),
                        new Dialogue("CAST AN ILLUSORY PLATFORM TO CROSS THESE SPIKES", 200, 200),
                        new Dialogue("(WASD to move, ESC to pause)", Utilities.getCenter().x, Utilities.getCenter().y),
                        new Dialogue("(Click to create an illusory duplicate of what you are currently standing on at the mouse cursor)", Utilities.getCenter().x - 200, Utilities.getCenter().y)
                );
                break;
            case 1:
                world = createWorld(p, "test");
                break;
            default:
                world = null;
        }
        return world;
    }
}
