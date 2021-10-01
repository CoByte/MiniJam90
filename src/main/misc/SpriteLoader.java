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
    }

    private static void getAnimation(PApplet p, String name, String type, int length) {
        StringBuilder path = new StringBuilder().append("sprites/");
        switch (type) {
            case "BT":
                path.append("gui/buttons/");
                break;
        }
        path.append(name).append("/");
        String fullName = name+type;
        animations.put(fullName, new PImage[length]);
        for (int i = length-1; i >= 0; i--) {
            animations.get(fullName)[i] = p.loadImage(path + PApplet.nf(i,3) + ".png");
        }
    }

    public static void loadSprites(PApplet p) {
        sprites.put("groundBa_TL", p.loadImage("sprites/tiles/ground.png"));
    }
}
