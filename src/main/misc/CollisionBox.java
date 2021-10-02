package main.misc;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;

import static main.misc.Utilities.*;
import static processing.core.PConstants.HALF_PI;
import static processing.core.PConstants.PI;

public class CollisionBox {

    public final PVector OFFSET;
    public final PVector SIZE;

    private final PApplet P;

    /**
     * @param offset top left of collision box RELATIVE TO OBJECT POSITION
     * @param size length and width of collision box
     */
    public CollisionBox(PApplet p, PVector offset, PVector size) {
        P = p;
        OFFSET = offset;
        SIZE = size;
    }

    public CollisionBox(PApplet p, PVector size) {
        this(p, new PVector(0, 0), size);
    }

    public void display(PVector position) {
        P.noFill();
        P.stroke(Color.MAGENTA.getRGB());
        P.rectMode(PConstants.CORNER);
        P.rect(position.x + OFFSET.x, position.y + OFFSET.y, SIZE.x, SIZE.y);

        PVector center = PVector.sub(position, OFFSET).add(PVector.div(SIZE, 2));
        P.circle(center.x, center.y, 5);
    }

    public float getRightEdge() {
        return OFFSET.x + SIZE.x;
    }

    public float getLeftEdge() {
        return OFFSET.x;
    }

    public float getBottomEdge() {
        return OFFSET.y + SIZE.y;
    }

    public float getTopEdge() {
        return OFFSET.y;
    }

    /**
     * Buffer should be positive.
     * Left to right, top to bottom.
     * Also includes some extra points to make things work
     * I could do it dynamically but I choose not to out of apathy
     */
    public IntVector[] getCornerGridPositions(PVector position, float buffer) {
        int ceilBuffer = (int) Math.ceil(buffer);
        return new IntVector[]{
            worldPositionToGridPosition(
                  new PVector(position.x + OFFSET.x + ceilBuffer, position.y + OFFSET.y + ceilBuffer
            )), worldPositionToGridPosition(
                    new PVector(position.x + OFFSET.x + SIZE.x - ceilBuffer, position.y + OFFSET.y + ceilBuffer
            )), worldPositionToGridPosition(
                    new PVector(position.x + OFFSET.x + ceilBuffer, position.y + OFFSET.y + SIZE.y - ceilBuffer
            )), worldPositionToGridPosition(
                    new PVector(position.x + OFFSET.x + SIZE.x - ceilBuffer, position.y + OFFSET.y + SIZE.y - ceilBuffer
            )), worldPositionToGridPosition(
                    new PVector(position.x + OFFSET.x + ceilBuffer, position.y + OFFSET.y + (SIZE.y - ceilBuffer) / 2)
            ), worldPositionToGridPosition(
                    new PVector(position.x + OFFSET.x + SIZE.x - ceilBuffer, position.y + OFFSET.y + (SIZE.y - ceilBuffer) / 2)
        )};
    }

    /**
     * todo: doesn't always work as expected
     * @param angle angle to check
     * @param position position of box
     * @return angle reflected off sides
     */
    public float collideAngleWithInside(float angle, PVector position) {
        float edgeAngle;
        float angleOfIncidence;
        if (position.x > getRightEdge() && angleIsFacingLeftStandard(angle)) {
            if (!angleIsFacingUpStandard(angle)) return -angle + PI;
            return angle + HALF_PI;
        } if (position.x < getLeftEdge() && !angleIsFacingLeftStandard(angle)) {
            if (angleIsFacingUpStandard(angle)) return angle - HALF_PI;
            else return -(angle - PI);
        } if (position.y > getBottomEdge() && angleIsFacingUpStandard(angle)) {
            edgeAngle = HALF_PI;
            angleOfIncidence = getAngleDifference(angle, edgeAngle);
            return -edgeAngle - angleOfIncidence;
        } if (position.y < getTopEdge() && !angleIsFacingUpStandard(angle)) {
            edgeAngle = PI + HALF_PI;
            angleOfIncidence = getAngleDifference(angle, edgeAngle);
            return -edgeAngle - angleOfIncidence;
        }
        return angle;
    }

    /**
     * Check if a point is inside the box
     * @param position position of box
     * @param point point to check
     * @return if inside
     */
    public boolean pointIsInsideBox(PVector position, PVector point) {
        boolean left = point.x > position.x + getLeftEdge();
        boolean right = point.x < position.x + getRightEdge();
        boolean top = point.y > position.y + getTopEdge();
        boolean bottom = point.y < position.y + getBottomEdge();
        return left && right && top && bottom;
    }

    public boolean intersects(PVector positionA, PVector positionB, CollisionBox boxB) {
        PVector boxATopLeft = new PVector(getLeftEdge(), getTopEdge()).add(positionA);
        PVector boxBTopLeft = new PVector(boxB.getLeftEdge(), boxB.getTopEdge()).add(positionB);
        PVector boxABottomRight = new PVector(getRightEdge(), getBottomEdge()).add(positionA);
        PVector boxBBottomRight = new PVector(boxB.getRightEdge(), boxB.getBottomEdge()).add(positionB);

        return
                boxABottomRight.x > boxBTopLeft.x &&
                boxATopLeft.x < boxBBottomRight.x &&
                boxABottomRight.y > boxBTopLeft.y &&
                boxATopLeft.y < boxBBottomRight.y;
    }

    public Collision calculateOffset(PVector positionA, PVector positionB, CollisionBox boxB) {
        PVector centerA = PVector.sub(positionA, OFFSET).add(PVector.div(SIZE, 2));
        PVector centerB = PVector.sub(positionB, boxB.OFFSET).add(PVector.div(boxB.SIZE, 2));

        float widthDepth = (SIZE.x / 2) + (boxB.SIZE.x / 2) - Math.abs(centerB.x - centerA.x);
        float heightDepth = (SIZE.y / 2) + (boxB.SIZE.y / 2) - Math.abs(centerB.y - centerA.y);

        System.out.println("Width: " + widthDepth + ", Height: " + heightDepth);

        if (widthDepth <= heightDepth && widthDepth != 0) {
            if (centerA.x < centerB.x) return new Collision(Direction.Right, Math.abs(widthDepth));
            else if (centerA.x > centerB.x) return new Collision(Direction.Left, Math.abs(widthDepth));
        } else if (heightDepth < widthDepth && heightDepth != 0) {
            if (centerA.y < centerB.y) return new Collision(Direction.Down, Math.abs(heightDepth));
            else if (centerA.y > centerB.y) return new Collision(Direction.Up, Math.abs(heightDepth));
        }
        return new Collision(Direction.None, 0);
    }

    public static class Collision {
        public Direction direction;
        public float offset;
        public Collision(Direction direction, float offset) {
            this.direction = direction;
            this.offset = offset;
        }

        @Override
        public String toString() {
            return "Direction: " + direction;
        }
    }

    public enum Direction {
        Up, Down, Left, Right, None
    }

    public CollisionBox copy() {
        return new CollisionBox(P, OFFSET, SIZE);
    }
}
