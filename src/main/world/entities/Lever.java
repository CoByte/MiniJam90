package main.world.entities;

import main.misc.CollisionBox;
import main.misc.CollisionEntity;
import main.misc.Trigger;
import main.world.Player;
import main.world.World;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Lever extends Entity {
    public final float startingY;
    public final float endingY;

    private final CollisionEntity detection;

    public boolean bottomedOut = false;

    public Trigger pressed;

    public Lever(PApplet p, World world, PVector position) {
        super(p, world, new CollisionBox(
                p, new PVector(25, 5)
        ), position);

        detection = new CollisionEntity(
                new CollisionBox(p, new PVector(0, -5), new PVector(25, 5)),
                position);

        startingY = position.y;
        endingY = startingY + 25;

        pressed = new Trigger();
    }

    @Override
    public void update() {
        super.update();

        move(
                getMovement(world, this, collider, position),
                !getDetected(world, detection)
        );

        bottomedOut = position.y >= endingY;
        pressed.triggerState(bottomedOut);
    }

    public void move(float movement, boolean detected) {
        if (movement > 0) {
            position.y = Math.min(endingY, Math.max(position.y, position.y + movement));
        } else if (detected) {
            position.y = Math.min(endingY, Math.max(startingY, position.y - 1));
        }
    }

    @Override
    public void draw() {
        collider.display(position);
    }

    public static boolean getDetected(World world, CollisionEntity detection) {
        ArrayList<Entity> detectedEntities = world.getCollidingEntities(detection);
        for (Entity e : detectedEntities) {
            if (e instanceof Player || e instanceof MovingPlatform) {
                return true;
            }
        }
        return false;
    }

    public static float getMovement(World world, Entity entity, CollisionBox collider, PVector position) {
        ArrayList<Entity> collided = world.getCollidingEntities(entity);
        float movement = 0;
        for (Entity e : collided) {
            if (!(e instanceof Player || e instanceof MovingPlatform)) { continue; }

            CollisionBox.Collision offset = collider.calculateOffset(position, e.position, e.collider);

            if (offset.direction != CollisionBox.Direction.Up) { continue; }

            if (e instanceof Player) {
                if (((Player) e).velocity_y <= 0) {
                    continue;
                }
            }

            if (e instanceof MovingPlatform) {
                if (((MovingPlatform) e).goingUp) {
                    continue;
                }
            }

            movement += offset.offset;
        }
        return movement;
    }
}
