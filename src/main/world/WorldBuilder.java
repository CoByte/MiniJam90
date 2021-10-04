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
            case 1:
                world = createWorld(p, "illusion 1");
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
                        new Dialogue("CHAMBER 1: ILLUSIONS PART 1", 200, 200),
                        new Dialogue("Alright, Illusions, these are easy..."),
                        new Dialogue("Just draw the Illusion character!"),
                        new Dialogue("CAST AN ILLUSORY PLATFORM TO CROSS THESE SPIKES", 200, 200),
                        new Dialogue("(WASD to move, ESC to pause)", Utilities.getCenter().x, Utilities.getCenter().y),
                        new Dialogue("(Click to create an illusory duplicate of what you are currently standing on at the mouse cursor)",
                                Utilities.getCenter().x - 200, Utilities.getCenter().y)
                );
                break;
            case 0:
                world = createWorld(p, "illusion 2");
                world.entities.addAll(new ArrayList<>(Arrays.asList(
                        new LeverDoor(p, world, new PVector(50, bottom - 250), 4),
                        new MovingPlatform(p, world,
                                new PVector(right - 16 * 50, bottom - 4 * 50),
                                new PVector(right - 16 * 50, bottom - 10 * 50),
                                3, 1)
                )));
                world.addDialogue(
                        new Dialogue("Ugh..."),
                        new Dialogue("I partied too hard last night."),
                        new Dialogue("CHAMBER 2: ILLUSIONS PART 2", 200, 200),
                        new Dialogue("REMINDER: ILLUSIONS RETAIN THE PROPERTIES AND MOTION OF THEIR ORIGINAL", 200, 200),
                        new Dialogue("What was in that drink...?")
                );
            case 2:
                world = createWorld(p, "fire1");
                world.entities.addAll(new ArrayList<>(Arrays.asList(
                        new Fire(p, new PVector(300, 250), world),
                        new Fire(p, new PVector(1050, 250), world),
                        new LeverDoor(p, world, new PVector(50, bottom - 250), 4),
                        new WaxDoor(p, world, new PVector(right - 150, bottom - 250), 2)
                )));
                world.addDialogue(
                        new Dialogue("Alright, maybe I'm not going to fail this"),
                        new Dialogue("CHAMBER IDK: FIRE", 200, 200),
                        new Dialogue("CAST A FIRE SPELL TO MELT THE WAX DOOR", 200, 200),
                        new Dialogue("Alright, thats not too hard"),
                        new Dialogue("I'll just draw the fire character and melt the door, easy"),
                        new Dialogue("Step 1: draw the fire character"),
                        new Dialogue("Fire... character..."),
                        new Dialogue("..."),
                        new Dialogue("I CAN'T REMEMBER THE FIRE CHARACTER!!!!!"),
                        new Dialogue("I can't afford to fail this class, there's got to be another way"),
                        new Dialogue("Maybe my fireproof boots can help...")
                );

                break;
            default:
                world = null;
        }
        return world;
    }
}
