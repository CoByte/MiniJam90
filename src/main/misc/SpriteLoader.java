package main.misc;

import processing.core.PApplet;
import processing.core.PImage;

import static main.Main.*;

public class SpriteLoader {
    
    /**
     * Loads animations.
     * @param p the PApplet
     */
    public static void loadAnimations(PApplet p) {
        getAnimation(p, "genericButton", "BT", 3);

        getAnimation(p, "walk", "Player", 8);
    }

    private static void getAnimation(PApplet p, String name, String type, int length) {
        StringBuilder path = new StringBuilder().append("sprites/");
        switch (type) {
            case "BT":
                path.append("gui/buttons/");
                break;
            case "Player":
                path.append("player/");
                break;
        }
        path.append(name).append("/");
        String fullName = name+type;
        animations.put(fullName, new PImage[length]);
        for (int i = length-1; i >= 0; i--) {
            animations.get(fullName)[i] = p.loadImage(path + nf(i,3) + ".png");
        }
    }

    public static void loadSprites(PApplet p) {
        sprites.put("backBa_TL", p.loadImage("sprites/tiles/back.png"));
        for (int i = 0; i < 21; i++) {
            sprites.put("wall" + nf(i, 3) + "Ob_TL", p.loadImage("sprites/tiles/wall/" + nf(i, 3) + ".png"));
        }
        for (int i = 0; i < 3; i++) {
            sprites.put("wood" + nf(i, 3) + "Ob_TL", p.loadImage("sprites/tiles/wood/" + nf(i, 3) + ".png"));
        }

        sprites.put("movingPlatform", p.loadImage("sprites/movingPlatform.png"));
        sprites.put("wax", p.loadImage("sprites/wax.png"));
        sprites.put("door", p.loadImage("sprites/door.png"));
        sprites.put("leverBack", p.loadImage("sprites/leverBack.png"));
        sprites.put("leverHandle", p.loadImage("sprites/leverHandle.png"));
    }
}
