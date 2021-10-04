package main.world;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Dialogue {
    public enum LocationType {
        Player,
        Coords
    }

    public final LocationType type;
    private final String dialogue;

    private PVector coords = new PVector();

    public Dialogue(String dialogue) {
        type = LocationType.Player;
        this.dialogue = dialogue;
    }

    public Dialogue(String dialogue, float x, float y) {
        type = LocationType.Coords;
        this.dialogue = dialogue;
        coords.set(x, y);
    }

    public void display(PApplet p, Player player) {
        p.fill(255);
        p.textSize(20);

        switch (type) {
            case Player:
                p.textAlign(PConstants.CENTER, PConstants.BOTTOM);
                p.text(dialogue, player.position.x - 100, player.position.y - 1000, 200, 1000);
                break;
            case Coords:
                p.textAlign(PConstants.LEFT, PConstants.TOP);
                p.text(dialogue, coords.x, coords.y, 400, 1000);
        }
    }
}
