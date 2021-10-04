package main.world;

import main.misc.Timer;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class DialogueEngine {
    private final PApplet p;
    private final Player player;

    public final ArrayList<Dialogue> dialogue;

    private final Timer textTimer = new Timer(60 * 5);

    private int currentLine = 0;

    public DialogueEngine(PApplet p, Player player, ArrayList<Dialogue> dialogue) {
        this.p = p;
        this.player = player;
        this.dialogue = dialogue;
    }

    public boolean finished() {
        return (currentLine >= dialogue.size());
    }

    public void play() {
        if (finished()) return;

        dialogue.get(currentLine).display(p, player);
        textTimer.update();
        if (textTimer.triggered(true)) {
            currentLine += 1;
        }
    }

    public void nextLine() {
        currentLine += 1;
        textTimer.reset();
    }
}
