package main.world;

import main.Main;
import main.misc.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Player {

    private static final float WALK_SPEED = 2;
    private static final float JUMP_SPEED = -5;
    private static final float ACCELERATION_Y = 0.1f;

    private final PApplet P;
    /**Top left corner*/
    private final PVector POSITION;
    private final PImage SPRITE;
    /**I use this to control the player's size (both collision and visual). Don't set an offset, or it will break.*/
    private final CollisionBox MAIN_HIT_BOX;
    private final World WORLD;
    private final Animator WALK_ANIMATION;

    private boolean facingLeft;
    private float velocity_y;

    public Player(PApplet p, PVector position, World world) {
        P = p;
        POSITION = position;

        SPRITE = Main.sprites.get("player");
        MAIN_HIT_BOX = new CollisionBox(P, new PVector(40, 52));
        WORLD = world;
        WALK_ANIMATION = new Animator(Main.animations.get("walkPlayer"), 8);
    }

    public void update() {
        IntVector axes = Utilities.getAxesFromMovementKeys();
        boolean grounded = false;
        IntVector[] hitBoxCorners = MAIN_HIT_BOX.getCornerGridPositions(POSITION, 0);

        //checks for collision with ground
        for (int i = hitBoxCorners.length / 2; i < hitBoxCorners.length; i++) {
            IntVector pos = hitBoxCorners[i];
            Tile tile = WORLD.TILEMAP.get(pos);
            if (tile.baseName != null) { //todo: temp
                grounded = true;
                break;
            }
        }

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

        if (axes.x != 0 && grounded) WALK_ANIMATION.update();

        POSITION.x += axes.x * WALK_SPEED;
        POSITION.y += velocity_y;
    }

    public void display() {
        if (facingLeft) { //mirroring
            P.pushMatrix();
            P.translate(POSITION.x, POSITION.y);
            P.scale(-1, 1);
            P.image(WALK_ANIMATION.getCurrentFrame(), -MAIN_HIT_BOX.getRightEdge(), 0,
                    MAIN_HIT_BOX.getRightEdge(), MAIN_HIT_BOX.getBottomEdge());
            P.popMatrix();
        } else P.image(WALK_ANIMATION.getCurrentFrame(), POSITION.x, POSITION.y,
                MAIN_HIT_BOX.getRightEdge(), MAIN_HIT_BOX.getBottomEdge());
    }
}
