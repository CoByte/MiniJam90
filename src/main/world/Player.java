package main.world;

import main.Main;
import main.misc.CollisionBox;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class Player {

    private final PApplet P;
    private final PVector POSITION;
    private final PImage SPRITE;
    private final CollisionBox MAIN_HIT_BOX;

    public Player(PApplet p, PVector position) {
        P = p;
        POSITION = position;

        SPRITE = Main.sprites.get("player");
        MAIN_HIT_BOX = new CollisionBox(P, new PVector(80, 105));
    }

    public void update() {}

    public void display() {
        P.image(SPRITE, POSITION.x, POSITION.y, MAIN_HIT_BOX.getRightEdge(), MAIN_HIT_BOX.getBottomEdge());
    }
}
