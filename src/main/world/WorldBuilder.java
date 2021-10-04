package main.world;

import main.misc.Utilities;
import main.world.entities.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
        switch (num) {
            case 0:
                world = createWorld(p, "test");
                LeverDoor door = new LeverDoor(p, world, new PVector(1200, 650), 4, 1);
                world.entities.addAll(new ArrayList<>(Arrays.asList(
                        new MovingPlatform(p, world,
                                new PVector(400, 400),
                                new PVector(800, 400),
                                3, Utilities.secondsToFrames(1.5f)
                        ),
                        new MovingPlatform(p, world,
                                new PVector(1000, 400),
                                new PVector(1000, 800),
                                3, Utilities.secondsToFrames(1.5f)
                        ),
                        new Fire(p, new PVector(300, 400), world),
                        new WaxDoor(p, world, new PVector(1000, 650), 1),
                        new Lever(p, world, new PVector(300, 700), door),
//                        door,
                        new MovingPlatform(p, world,
                                new PVector(275, 400),
                                new PVector(275, 1000),
                                3, 0),
                        new Spikes(p, world, new PVector(400, 750))
                )));
                world.addDialogue(
//                        new Dialogue("Quickly! Devour the child!"),
//                        new Dialogue("THIS IS THE PA SYSTEM, YOU SHOULD TOTALLY DO IT ITLL BE FUCKIN RAD", 50, 50),
//                        new Dialogue("mmmmmmm, child vore"),
//                        new Dialogue("damn thats the good stuff"),
//                        new Dialogue("very long string, wow so long, incredible, isnt this hot, wow, so thicc and big, its so immense, it just keeps going, truly fantastic, would probably make jesus wet, honestly shocked its still going, someone really needs to put a stop to this, its just too big and girthy owo,")
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
