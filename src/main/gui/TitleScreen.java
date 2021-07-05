package main.gui;

import main.gui.guiObjects.buttons.MenuButton;
import main.sound.FadeSoundLoop;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.misc.Utilities.getCenter;
import static main.misc.Utilities.shadowedText;
import static processing.core.PConstants.CORNER;

public class TitleScreen {

    private static final int INCREASE_DARK = 3;

    private final PApplet P;
    private final MenuButton QUIT_BUTTON;
    private final MenuButton PLAY_BUTTON;

    /**
     * nothing, exiting, playing
     */
    private int exitState;
    private boolean gettingDark;
    private int darkAmount;

    public TitleScreen(PApplet p) {
        P = p;

        QUIT_BUTTON = new MenuButton(p, BOARD_SIZE.x / 2, (BOARD_SIZE.y / 2) + 50);
        PLAY_BUTTON = new MenuButton(p, BOARD_SIZE.x / 2, (BOARD_SIZE.y / 2) + 25);
        darkAmount = 255;
    }

    public void main() {
        update();
        display();
    }

    private void update() {
        QUIT_BUTTON.hover();
        if (QUIT_BUTTON.isPressed()) {
            for (FadeSoundLoop fadeSoundLoop : fadeSoundLoops.values()) fadeSoundLoop.setTargetVolume(0.001f);
            gettingDark = true;
            exitState = 1;
        }
        PLAY_BUTTON.hover();
        if (PLAY_BUTTON.isPressed()) {
            gettingDark = true;
            exitState = 2;
        }

        if (darkAmount >= 255) {
            switch (exitState) {
                case 1:
                    P.exit();
                    break;
                case 2:
                    System.out.println("play!");
                    break;
            }
        }
    }

    private void display() {
        P.rectMode(CORNER);
        P.imageMode(CORNER);
        P.fill(0);
        P.noStroke();
        P.rect(0, 0, BOARD_SIZE.x, BOARD_SIZE.y);
        P.rectMode(DEFAULT_MODE);

        shadowedText(P, TITLE, new PVector(getCenter().x, getCenter().y - 50), new Color(255, 255, 255), new Color(50, 50, 50),
        25, CENTER);

        P.fill(20);
        P.textSize(10);
        P.textAlign(CENTER);
        PLAY_BUTTON.main();
        P.text("Start", BOARD_SIZE.x / 2, (BOARD_SIZE.y / 2) + 28);
        QUIT_BUTTON.main();
        P.text("Quit", BOARD_SIZE.x / 2, (BOARD_SIZE.y / 2) + 53);

        if (gettingDark) {
            darkAmount += INCREASE_DARK;
            if (darkAmount >= 255) gettingDark = false;
        } else if (darkAmount > 0) darkAmount -= INCREASE_DARK;
        P.rectMode(CORNER);
        P.fill(0, darkAmount);
        P.noStroke();
        P.rect(0, 0, BOARD_SIZE.x + 1, BOARD_SIZE.y + 1);
        P.rectMode(CENTER);
        P.imageMode(DEFAULT_MODE);
    }
}
