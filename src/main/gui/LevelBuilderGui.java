package main.gui;

import main.gui.guiObjects.buttons.TileSelect;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

import static main.Main.*;

public class LevelBuilderGui {

    public static boolean right = false;

    private final PApplet P;

    private final ArrayList<TileSelect> TILE_SELECT_BUTTONS;

    public LevelBuilderGui(PApplet p) {
        this.P = p;
        TILE_SELECT_BUTTONS = new ArrayList<>();
        build();
    }

    public void main() {
        P.fill(255); //big white bg
        P.rectMode(PConstants.CORNER);
        if (right) P.rect(BOARD_SIZE.x - (TILE_SIZE * 4), 0, TILE_SIZE * 4, BOARD_SIZE.y);
        else P.rect(0, 0, TILE_SIZE * 4, BOARD_SIZE.y);
        P.rectMode(DEFAULT_MODE);
        for (TileSelect tileSelectButton : TILE_SELECT_BUTTONS) tileSelectButton.main();
    }

    private void build() {
        int bottom = (int) (BOARD_SIZE.y / TILE_SIZE) - 1;
        int middle = (int) ((BOARD_SIZE.y / TILE_SIZE) / 2) - 1;

        placeButton(0, 17, "Na");
        placeButton(3, bottom, "Sa");
    }

    private void placeButton(int x, int y, String type) {
        if (right) TILE_SELECT_BUTTONS.add(new TileSelect(P, 925 + (x * 50), 25 + (y * 50), type));
        else TILE_SELECT_BUTTONS.add(new TileSelect(P, (TILE_SIZE / 2f) + (x * TILE_SIZE), (TILE_SIZE / 2f) + (y * TILE_SIZE), type));
    }
}
