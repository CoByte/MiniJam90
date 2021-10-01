package main.world;

import main.Main;
import main.misc.CollisionBox;
import main.misc.IntVector;
import main.misc.Utilities;
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

    private boolean facingLeft;
    private float velocity_y;

    public Player(PApplet p, PVector position) {
        P = p;
        POSITION = position;

        SPRITE = Main.sprites.get("player");
        MAIN_HIT_BOX = new CollisionBox(P, new PVector(40, 52));
    }

    public void update() {
        IntVector axes = Utilities.getAxesFromMovementKeys();
        boolean grounded = POSITION.y >= Main.BOARD_SIZE.y - 200; //todo: temp

        /*
        I use this instead of `facingLeft = axes.x < 0` because I don't want the player's
        direction to change if they stop moving (axes.x == 0).
         */
        if (axes.x < 0) facingLeft = true;
        else if (axes.x > 0) facingLeft = false;

        if (axes.y < 0 && grounded) velocity_y = JUMP_SPEED;
        velocity_y += ACCELERATION_Y;
        if (grounded && velocity_y > 0) velocity_y = 0;

        POSITION.x += axes.x * WALK_SPEED;
        POSITION.y += velocity_y;
    }

    public void display() {
        if (facingLeft) { //mirroring
            P.pushMatrix();
            P.translate(POSITION.x, POSITION.y);
            P.scale(-1, 1);
            P.image(SPRITE, -MAIN_HIT_BOX.getRightEdge(), 0,
                    MAIN_HIT_BOX.getRightEdge(), MAIN_HIT_BOX.getBottomEdge());
            P.popMatrix();
        } else P.image(SPRITE, POSITION.x, POSITION.y,
                MAIN_HIT_BOX.getRightEdge(), MAIN_HIT_BOX.getBottomEdge());
    }
}
