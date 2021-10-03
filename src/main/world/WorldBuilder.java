package main.world;

import main.misc.Utilities;
import main.world.entities.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;

public class WorldBuilder {

    public static World buildWorld(PApplet p, int num) {
        World world;
        switch (num) {
            case 0:
                world = new World(p, "test");
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
                        new Fire(p, new PVector(300, 300), world),
                        new WaxDoor(p, world, new PVector(1000, 650), 1),
                        new Lever(p, world, new PVector(300, 700), door),
                        door,
                        new MovingPlatform(p, world,
                                new PVector(275, 400),
                                new PVector(275, 1000),
                                3, 0)
                )));
                break;
            default:
                world = null;
        }
        return world;
    }
}
