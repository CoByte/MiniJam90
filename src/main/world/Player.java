package main.world;

import main.Main;
import main.misc.CollisionBox;
import main.misc.IntVector;
import main.misc.Tile;
import main.misc.Utilities;
import main.world.entities.Entity;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Player extends Entity {

    private static final float WALK_SPEED = 2;
    private static final float JUMP_SPEED = -5;
    private static final float ACCELERATION_Y = 0.1f;

    private final PImage SPRITE;
    private final World WORLD;

    private boolean facingLeft;
    private boolean grounded;
    private float velocity_y;

    public Player(PApplet p, PVector position, World world) {
        super(p, new CollisionBox(p, new PVector(40, 52)), position);

        SPRITE = Main.sprites.get("player");
        WORLD = world;
    }

    @Override
    public void update() {
        IntVector axes = Utilities.getAxesFromMovementKeys();

        /*
        I use this instead of `facingLeft = axes.x < 0` because I don't want the player's
        direction to change if they stop moving (axes.x == 0).
         */
        if (axes.x < 0) facingLeft = true;
        else if (axes.x > 0) facingLeft = false;

        //collision with ground
        if (axes.y < 0 && grounded) velocity_y = JUMP_SPEED;
        velocity_y += ACCELERATION_Y;
        if (grounded && velocity_y > 0) velocity_y = 0;

        position.x += axes.x * WALK_SPEED;
        position.y += velocity_y;

        grounded = false;
        ArrayList<Entity> entities = WORLD.getCollidingEntities(this);
        System.out.println(entities);
        for (Entity entity : entities) {
            CollisionBox.Collision offset = collider.calculateOffset(position, entity.position, entity.collider);
            System.out.println(offset);
            switch (offset.direction()) {
                case Up -> position.y += offset.offset();
                case Down -> {
                    position.y -= offset.offset();
                    grounded = true;
                }
                case Left -> position.x += offset.offset();
                case Right -> position.x -= offset.offset();
            }
        }
    }

    @Override
    public void draw() {
        if (facingLeft) { //mirroring
            P.pushMatrix();
            P.translate(position.x, position.y);
            P.scale(-1, 1);
            P.image(SPRITE, -collider.getRightEdge(), 0,
                    collider.getRightEdge(), collider.getBottomEdge());
            P.popMatrix();
        } else P.image(SPRITE, position.x, position.y,
                collider.getRightEdge(), collider.getBottomEdge());

        if (Main.debug) collider.display(position);
    }
}
