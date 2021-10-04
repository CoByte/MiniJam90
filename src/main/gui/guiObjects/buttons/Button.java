package main.gui.guiObjects.buttons;

import main.misc.InputManager;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import static main.Main.*;
import static main.sound.SoundUtilities.playSound;

public abstract class Button {

    protected PApplet p;

    public PVector position;
    public PVector size;

    private final InputManager inputManager = InputManager.getInstance();

    protected boolean holdable;
    protected PImage sprite;
    protected PImage spriteIdle;
    protected PImage spritePressed;
    protected PImage spriteHover;
    protected String spriteLocation;
    protected SoundFile clickIn;
    protected SoundFile clickOut;

    protected Button(PApplet p, float x, float y) {
        this.p = p;
        position = new PVector(x, y);
        size = new PVector(25, 25);
        holdable = false;
        clickIn = sounds.get("clickIn");
        clickOut = sounds.get("clickOut");
    }

    public void hover() {
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

    public abstract void action();

    public void main() {
        hover();
        display();
    }

    public void display() {
        p.image(sprite,position.x-size.x/2,position.y-size.y/2, size.x, size.y);
    }
}