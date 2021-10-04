package main.gui.guiObjects;

import com.jogamp.newt.event.KeyEvent;
import main.Main;
import main.misc.InputManager;
import processing.core.PApplet;
import processing.core.PImage;

public class EndingScreen {

    final PApplet p;
    final PImage image;

    public EndingScreen(PApplet p) {
        this.p = p;

        image = Main.sprites.get("endingScreen");
    }

    public void main() {
        p.image(image, 0, 0, Main.BOARD_SIZE.x, Main.BOARD_SIZE.y);

        if (InputManager.getInstance().getEvent(KeyEvent.VK_ESCAPE).rising()) {
            Main.scene = Main.Scene.TitleScreen;
        }
    }
}
