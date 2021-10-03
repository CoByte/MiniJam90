package main.world.entities;

import main.Main;
import main.Main;
import main.misc.CollisionBox;
import main.misc.CollisionEntity;
import main.misc.Trigger;
import main.sound.SoundUtilities;
import main.world.Player;
import main.world.World;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PConstants;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Lever extends Entity {
    private final LeverDoor door;

    public final float maxDrop = 25;
    private final CollisionEntity detection;

    float movement = 0;
    boolean detected = false;

    public boolean collidedWith = false;

    private final PImage handleSprite;
    private final PImage baseSprite;
    private final SoundFile openSound;

    public boolean bottomedOut = false;

    public Trigger pressed;

    public Lever(PApplet p, World world, PVector position, LeverDoor door) {
        super(p, world, new CollisionBox(
                p, new PVector(25, 5)
        ), position);

        this.door = door;

        detection = new CollisionEntity(
                new CollisionBox(p, new PVector(0, -7), new PVector(25, 7)),
                position.copy());

        pressed = new Trigger();

        handleSprite = Main.sprites.get("leverHandle");
        baseSprite = Main.sprites.get("leverBack");

        openSound = Main.sounds.get("door");
    }

    public void drawBack() {
        float size = maxDrop * 2f;
        P.imageMode(PConstants.CENTER);
        P.image(baseSprite,
                collider.getWorldCenter(position).x,
                position.y + maxDrop / 2,
                size, size
        );
        P.imageMode(Main.DEFAULT_MODE);
    }

    @Override
    public void update() {
        if (movement <= 0 && !detected) {
            movement = -1;
        }
        collider.OFFSET.set(collider.OFFSET.x, Math.min(maxDrop, Math.max(0, collider.OFFSET.y + movement)));
        movement = 0;
        detected = false;
        innerUpdate(position);
    }

    public void innerUpdate(PVector testPosition) {
        detection.position.set(testPosition);
        ArrayList<Entity> detectedEntities = world.getCollidingEntities(detection);
        for (Entity e : detectedEntities) {
            if (e instanceof Player || e instanceof MovingPlatform) {
                detected = true;
                break;
            }
        }

        CollisionEntity colliderTest = new CollisionEntity(
                collider,
                testPosition
        );

        ArrayList<Entity> collided = world.getCollidingEntities(colliderTest);
        collidedWith = false;
        for (Entity e : collided) {
            if (!(e instanceof Player || e instanceof MovingPlatform)) { continue; }

            collidedWith = true;

            CollisionBox.Collision offset = collider.calculateOffset(testPosition, e.position, e.collider);

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

            movement = Math.max(offset.offset, movement);
        }

//        if (movement > 0) {
//            collider.OFFSET.set(collider.OFFSET.x, Math.min(maxDrop, Math.max(0, collider.OFFSET.y + movement)));
//        } else if (!detected) {
//            collider.OFFSET.set(collider.OFFSET.x, Math.min(maxDrop, Math.max(0, collider.OFFSET.y - 1)));
//        }

        bottomedOut = collider.OFFSET.y  >= maxDrop;

        pressed.triggerState(collider.OFFSET.y + 4 >= maxDrop);

        if (pressed.rising()) {
            door.activeLevers += 1;
            SoundUtilities.playSoundRandomSpeed(P, openSound, 1);
        }
        if (pressed.falling()) door.activeLevers -= 1;
    }

    @Override
    public void draw() {
        P.image(handleSprite, position.x, position.y + collider.OFFSET.y,
                collider.SIZE.x, collider.SIZE.y);
    }
}
