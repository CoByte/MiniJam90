package main.misc;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;

import static main.misc.Utilities.worldPositionToGridPosition;

public class CollisionBox {

    private final PVector POSITION;
    private final PVector SIZE;

    private final PApplet P;

    /**
     * @param position top left of collision box RELATIVE TO OBJECT POSITION
     * @param size length and width of collision box
     */
    public CollisionBox(PApplet p, PVector position, PVector size) {
        P = p;
        POSITION = position;
        SIZE = size;
    }

    public void display(PVector position) {
        P.noFill();
        P.stroke(Color.MAGENTA.getRGB());
        P.rectMode(PConstants.CORNER);
        P.rect(position.x + POSITION.x, position.y + POSITION.y, SIZE.x, SIZE.y);
    }

    public float getRightEdge() {
        return POSITION.x + SIZE.x;
    }

    public float getLeftEdge() {
        return POSITION.x;
    }

    public float getBottomEdge() {
        return POSITION.y + SIZE.y;
    }

    public float getTopEdge() {
        return POSITION.y;
    }

    /**
     * Buffer should be positive.
     * Left to right, top to bottom.
     */
    public IntVector[] getCornerGridPositions(PVector position, float buffer) {
        int ceilBuffer = (int) Math.ceil(buffer);
        return new IntVector[]{
          worldPositionToGridPosition(
            new PVector(position.x + POSITION.x + ceilBuffer, position.y + POSITION.y + ceilBuffer
            )), worldPositionToGridPosition(
            new PVector(position.x + POSITION.x + SIZE.x - ceilBuffer, position.y + POSITION.y + ceilBuffer
            )), worldPositionToGridPosition(
            new PVector(position.x + POSITION.x + ceilBuffer, position.y + POSITION.y + SIZE.y - ceilBuffer
            )), worldPositionToGridPosition(
            new PVector(position.x + POSITION.x + SIZE.x - ceilBuffer, position.y + POSITION.y + SIZE.y - ceilBuffer
            ))
        };
    }
}
