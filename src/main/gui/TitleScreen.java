package main.gui;

import main.Main;
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

    private enum ExitState {
        DoNothing(0),
        Exit(1),
        Play(2);

        final int ID;

        ExitState(int id) {
            ID = id;
        }
    }

    private static final int INCREASE_DARK = 3;

    private final PApplet P;
    private final MenuButton QUIT_BUTTON;
    private final MenuButton PLAY_BUTTON;

    private ExitState exitState = ExitState.DoNothing;
    private boolean gettingDark;
    private int darkAmount;

    public TitleScreen(PApplet p) {
        P = p;

        QUIT_BUTTON = new MenuButton(p, BOARD_SIZE.x / 2, (BOARD_SIZE.y / 2 + 100));
        PLAY_BUTTON = new MenuButton(p, BOARD_SIZE.x / 2, (BOARD_SIZE.y / 2));
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
            exitState = ExitState.Exit;
        }
        PLAY_BUTTON.hover();
        if (PLAY_BUTTON.isPressed()) {
            gettingDark = true;
            exitState = ExitState.Play;
        }

        if (darkAmount >= 255) {
            switch (exitState) {
                case Exit:
                    P.exit();
                    break;
                case Play:
                    scene = Scene.World;
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

        shadowedText(P, TITLE, new PVector(getCenter().x, getCenter().y - 150), new Color(0, 200, 255),
                new Color(0, 50, 150),
        75, CENTER);

        P.fill(new Color(0, 50, 150).getRGB());
        P.textSize(40);
        P.textAlign(CENTER);
        PLAY_BUTTON.main();
        P.text("Start", BOARD_SIZE.x / 2, PLAY_BUTTON.position.y + 12);
        QUIT_BUTTON.main();
        P.text("Quit", BOARD_SIZE.x / 2, QUIT_BUTTON.position.y + 12);

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
