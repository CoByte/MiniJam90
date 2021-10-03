package main.gui;

import main.Main;
import main.misc.Utilities;
import main.world.World;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

import static main.Main.BOARD_SIZE;
import static main.Main.setupWorlds;

public class LoadingScreen {

    public static final int MAX_PROGRESS = 5;

    public int progress;

    private final PApplet P;
    private final PFont FONT;

    public LoadingScreen(PApplet p, PFont font) {
        P = p;
        FONT = font;
    }

    public void main() {
        update();
        display();
    }

    private void update() {
        switch (progress) {
            case 0:
                Main.setupSprites(P);
                break;
            case 1:
                Main.setupMisc(P);
                break;
            case 2:
                setupWorlds(P);
                Main.titleScreen = new TitleScreen(P);
                break;
            case 3:
//                DataControl.loadLevel("world");
                break;
            case 4:
                Main.titleScreen = new TitleScreen(P);
                break;
        }

        progress++;
        if (progress == MAX_PROGRESS) Main.scene = Main.Scene.World;
    }

    private void display() {
        P.background(0);
        P.fill(240);
        P.textFont(FONT);
        P.textAlign(PConstants.CENTER);
        P.text("Loading...", Utilities.getCenter().x, Utilities.getCenter().y);

        P.strokeWeight(10);
        P.stroke(255);
        P.line(100, Utilities.getCenter().y + 200, ((BOARD_SIZE.x - 200) * (progress / (float) MAX_PROGRESS)) + 100, Utilities.getCenter().y + 200);
        P.strokeWeight(1);
    }
}
