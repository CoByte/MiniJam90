package main.world;

import main.Main;
import main.world.entities.Entity;
import main.misc.*;
import main.world.entities.Illusion;
import main.world.entities.MovingPlatform;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Player extends Entity {

    private static final float WALK_SPEED = 2;
    private static final float JUMP_SPEED = -5;
    private static final float ACCELERATION_Y = 0.1f;

    private final World WORLD;
    private final Animator WALK_ANIMATION;
    /**Allows the player to jump if they have just stepped off an edge, this is common in platformers.**/
    private final Timer COYOTE_TIMER;
    private final PVector DETECT_OFFSET;

    private boolean facingLeft;
    private boolean grounded;
    private float velocity_y;
    private Entity standingOn;

    public Player(PApplet p, PVector position, World world) {
        super(p, new CollisionBox(p, new PVector(39, 50)/*, new PVector(0, 2)*/), position);

        WORLD = world;
        WALK_ANIMATION = new Animator(Main.animations.get("walkPlayer"), 8);
        COYOTE_TIMER = new Timer(Utilities.secondsToFrames(0.3f), true);
        DETECT_OFFSET = new PVector(
                collider.getRightEdge() / 2,
                collider.getBottomEdge() + 5
        );
    }

    @Override
    public void update() {
        move();
        handleIllusions();
    }

    private void handleIllusions() {
        if (InputManager.getInstance().leftMouse.falling() && standingOn != null) {
            WORLD.illusion = new Illusion(standingOn, PVector.sub(Main.matrixMousePosition, standingOn.position));
        }
    }

    private void move() {
        IntVector axes = Utilities.getAxesFromMovementKeys();
        /*
        I use this instead of `facingLeft = axes.x < 0` because I don't want the player's
        direction to change if they stop moving (axes.x == 0).
        */
        if (axes.x < 0) facingLeft = true;
        else if (axes.x > 0) facingLeft = false;

        //collision with ground
        if (axes.y < 0 && (grounded || !COYOTE_TIMER.triggered(false))) {
            velocity_y = JUMP_SPEED;
            COYOTE_TIMER.setCurrentTime(COYOTE_TIMER.getAlarmTime());
        }
        velocity_y += ACCELERATION_Y;
        if (grounded && velocity_y > 0) velocity_y = 0;
        if (axes.x != 0 && grounded) WALK_ANIMATION.update();

        position.x += axes.x * WALK_SPEED;
        position.y += velocity_y;

        grounded = false;
        standingOn = null;
        ArrayList<Entity> entities = WORLD.getCollidingEntities(this);
        System.out.println(entities);
        for (Entity entity : entities) {
            CollisionBox otherCollider = entity.collider;
            CollisionBox.Collision offset = collider.calculateOffset(position, entity.position, otherCollider);
            float speed = 0;
            if (entity instanceof MovingPlatform) {
                MovingPlatform mp = (MovingPlatform) entity;
                speed = mp.speed * 2;
                if (!mp.goingToB) speed *= -1;
                if (mp.waiting) speed = 0;
            }
            System.out.println(offset);
            switch (offset.direction) {
                case Up:
                    position.y += offset.offset;
                    //velocity_y = 0;
                    break;
                case Down:
                    position.y -= offset.offset;
                    if (velocity_y > 0) grounded = true;
                    position.x += speed;
                    break;
                case Left: position.x += offset.offset; break;
                case Right: position.x -= offset.offset; break;
            }

            if (otherCollider.pointIsInsideBox(entity.position, PVector.add(position, DETECT_OFFSET))) {
                standingOn = entity;
            }
        }
        if (grounded) COYOTE_TIMER.reset();
        COYOTE_TIMER.update();
    }

    @Override
    public void draw() {
        if (facingLeft) { //mirroring
            P.pushMatrix();
            P.translate(position.x, position.y);
            P.scale(-1, 1);
            P.image(WALK_ANIMATION.getCurrentFrame(), -collider.getRightEdge(), 0,
                    collider.getRightEdge(), collider.getBottomEdge());
            P.popMatrix();
        } else P.image(WALK_ANIMATION.getCurrentFrame(), position.x, position.y,
                collider.getRightEdge(), collider.getBottomEdge());

        if (Main.debug) collider.display(position);

        if (standingOn != null) standingOn.highlight();
    }
}
