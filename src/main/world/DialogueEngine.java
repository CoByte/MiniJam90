package main.world;

import main.misc.Timer;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class DialogueEngine {
    private final PApplet p;
    private final Player player;

    public final ArrayList<String> dialogue;

    private final Timer textTimer = new Timer(60 * 5);

    private int currentLine = 0;

    public DialogueEngine(PApplet p, Player player, ArrayList<String> dialogue) {
        this.p = p;
        this.player = player;
        this.dialogue = dialogue;
    }

    public boolean finished() {
        return (currentLine >= dialogue.size());
    }

    public void play() {
        if (finished()) return;

        p.fill(255);
        p.textSize(15);
        p.textAlign(PConstants.CENTER, PConstants.BOTTOM);
        p.text(dialogue.get(currentLine), player.position.x - 100, player.position.y - 1000, 200, 1000);
        textTimer.update();
        if (textTimer.triggered(true)) {
            currentLine += 1;
        }
    }
}
