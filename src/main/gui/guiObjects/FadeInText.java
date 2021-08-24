package main.gui.guiObjects;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;

public class FadeInText {

    enum State {
        Unloaded(0),
        Loaded(1),
        Disabling(2),
        Disabled(3);

        final int ID;

        State(int id) {
            ID = id;
        }
    }

    private final PApplet p;
    private final PFont font;

    private final String TEXT;
    private final PVector POSITION;
    private final PVector SIZE;

    private int alpha = 0;
    private State state = State.Unloaded;

    /**
     * A textbox that can fade in and out.
     * @param font font to display text in
     * @param text what to display
     * @param position where in world to display
     * @param size size of text box
     */
    public FadeInText(PApplet p, PFont font, String text, PVector position, PVector size) {
        this.p = p;
        this.font = font;

        this.TEXT = text;
        this.POSITION = position;
        this.SIZE = size;
    }

    public void update() {
        switch (state) {
            case Unloaded:
                break;
            case Loaded:
                alpha += 3;
                alpha = Math.min(alpha, 255);
                break;
            case Disabling:
                alpha -= 3;
                if (alpha < 0) state = State.Disabled;
                break;
        }
    }

    public void draw() {
        p.fill(240, alpha);
        p.textFont(font);
        p.textAlign(PConstants.CENTER);
        p.rectMode(PConstants.CENTER);
        p.text(TEXT, POSITION.x, POSITION.y, SIZE.x, SIZE.y);
    }
}
