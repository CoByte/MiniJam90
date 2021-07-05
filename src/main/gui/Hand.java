package main.gui;

import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.sound.SoundUtilities.playSound;

public class Hand {

    private final PApplet P;

    public String held;
    private PImage heldSprite;
    private PVector offset;
    public String displayInfo;
    public int price;

    public final float MIN_ENEMY_DISTANCE;

    public Hand(PApplet p) {
        this.P = p;

        held = "null";
        offset = new PVector(0, 0);
        price = 0;
        displayInfo = "null";

        MIN_ENEMY_DISTANCE = 50;
    }

    public void main() {
        if (inputHandler.leftMousePressedPulse) tryPlace();
        displayHeld();
    }

    private void tryPlace() {
        if (held.equals("null")) return;
        place();
    }

    private void clearHand() {
        if (!held.equals("null")) playSound(sounds.get("clickOut"), 1, 1);
        held = "null";
    }

    /**
     * Shows what's held at reduced opacity
     */
    private void displayHeld() {
        if (!held.equals("null") && heldSprite != null) {
            P.tint(255, 150);
            P.image(heldSprite, (roundToLeft(matrixMousePosition.x, 50)) - (25f / 2) - offset.x + 13, roundToLeft(matrixMousePosition.y, 50) - (25f / 2) - offset.y + 13);
            P.tint(255);
        }
    }

    /**
     * Swaps what's held.
     * @param heldSet name of what should be held
     */
    public void setHeld(String heldSet) {
        if (heldSet.endsWith("TL")) {
            offset = new PVector(0,0);
            price = 0;
            heldSprite = sprites.get(heldSet);
        }
        held = heldSet;
    }

    /**
     * Puts down tower and subtracts price.
     */
    private void place() {
        if (held.endsWith("TL")) {
            Tile tile = tiles.get((roundToLeft(matrixMousePosition.x, 50) / 50), (roundToLeft(matrixMousePosition.y, 50) / 50));
            if (held.endsWith("Ba_TL")) tile.setBase(held);
            else if (held.endsWith("De_TL")) tile.setDecoration(held);
            else if (held.endsWith("Br_TL")) tile.setBreakable(held);
            else if (held.endsWith("Ob_TL")) tile.setObstacle(held);
            else if (held.endsWith("Na_TL")) {
                tile.setDecoration(null);
                tile.setBreakable(null);
                tile.setObstacle(null);
            }
        }
    }
}