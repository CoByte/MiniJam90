package main.gui.guiObjects.buttons;

import main.misc.InputManager;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.sound.SoundUtilities.playSound;
import static processing.core.PConstants.LEFT;

public class MenuButton extends Button {

    private final InputManager inputManager = InputManager.getInstance();

    private boolean pressed;

    public MenuButton(PApplet p, float x, float y) {
        super(p, x, y);
        position = new PVector(x, y);
        size = new PVector(320, 64);
        spriteIdle = animations.get("genericButtonBT")[0];
        spritePressed = animations.get("genericButtonBT")[1];
        spriteHover = animations.get("genericButtonBT")[2];
        sprite = spriteIdle;
    }

    /**
     * If mouse over, push in.
     * Works if paused or dead.
     */
    @Override
    public void hover(){
        if (matrixMousePosition.x < position.x+size.x/2 && matrixMousePosition.x > position.x-size.x/2 &&
          matrixMousePosition.y < position.y+size.y/2 && matrixMousePosition.y > position.y-size.y/2) {
            sprite = spriteHover;
            if (inputManager.leftMouse.rising()) playSound(clickIn, 1, 1);
            if (p.mousePressed && p.mouseButton == LEFT) sprite = spritePressed;
            if (holdable && p.mousePressed && p.mouseButton == LEFT) action();
            else if (inputManager.leftMouse.falling()) {
                playSound(clickOut, 1, 1);
                action();
                sprite = spritePressed;
            }
        } else sprite = spriteIdle;
    }

    /**
     * This makes sense for some reason, but I forgot why
     */
    @Override
    public void action() {
        pressed = true;
    }

    /**
     * @return whether the mouse was recently pressed, resets pressed
     */
    public boolean isPressed() {
        boolean wasPressed = pressed;
        pressed = false;
        return wasPressed;
    }
}
