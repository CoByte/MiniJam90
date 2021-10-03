package main.gui.guiObjects.buttons;

import main.misc.DataControl;
import main.misc.InputManager;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class TileSelect extends Button {

    private final String TYPE;

    private PImage tileSprite;

    public TileSelect(PApplet p, float x, float y, String type) {
        super(p,x,y);
        position = new PVector(x, y);
        size = new PVector(TILE_SIZE, TILE_SIZE);
        this.TYPE = type;
        //still uses old sprite loading system because it is only created at the beginning of the game
        String folder = "";
        switch (type) {
            case "Na":
                folder = "erase";
                tileSprite = p.loadImage("sprites/gui/buttons/tileSelect/erase/icon.png");
                break;
            case "Sa":
                folder = "save";
                tileSprite = p.loadImage("sprites/gui/buttons/tileSelect/save/icon.png");
                break;
            default:
                tileSprite = sprites.get(type + "_TL");
                break;
        }
        if (type.endsWith("Ba")) folder = "base";
        else if (type.endsWith("De")) folder = "decoration";
        else if (type.endsWith("Br")) folder = "breakable";
        else if (type.endsWith("Ob")) folder = "obstacle";
        else if (type.endsWith("PI")) {
            folder = "piece";
            tileSprite = sprites.get(type);
        } else if (type.endsWith("TO")) {
            folder = "piece";
            tileSprite = animations.get(type)[0];
        }
        spriteLocation = "sprites/gui/buttons/tileSelect/" + folder + "/";
        spriteIdle = p.loadImage(spriteLocation + "000.png");
        spritePressed = p.loadImage(spriteLocation + "001.png");
        sprite = spriteIdle;
    }

    @Override
    public void main() {
        hover();
        display();
    }

    @Override
    public void display() {
        p.imageMode(CENTER);
        p.image(tileSprite, position.x, position.y, 50, 50);
        p.image(sprite, position.x, position.y, 50, 50);
        p.imageMode(DEFAULT_MODE);
    }

    /**
     * If hovered or depressed.
     */
    @Override
    public void hover() {
        if (matrixMousePosition.x < position.x+size.x/2 && matrixMousePosition.x > position.x-size.x/2 &&
          matrixMousePosition.y < position.y+size.y/2 && matrixMousePosition.y > position.y-size.y/2) {
            sprite = spritePressed;
            if (InputManager.getInstance().leftMouse.rising()) action();
        }
        else sprite = spriteIdle;
    }

    @Override
    public void action() {
        if (TYPE.equals("Sa")) {
            playSoundRandomSpeed(p, sounds.get("camera"), 1);
            DataControl.saveLevel(worlds[currentWorld].TILEMAP);
            hand.setHeld("null");
            return;
        }
        if (hand.held.equals(TYPE)) hand.setHeld("null");
        if (TYPE.contains("Ob")) hand.setHeld(TYPE + "_TL");
        else if (TYPE.contains("TO")) hand.setHeld(TYPE);
        else hand.setHeld(TYPE + "_TL");
    }
}