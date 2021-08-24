package main.gui.guiObjects;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;

public class FadeInText {
    enum State {
        Unloaded,
        Loaded,
        Disabling,
        Disabled,
    }

    private final float LOAD_DISTANCE = 200;

    private final PApplet p;
    private final PFont font;

    private String text;
    private PVector position;
    private PVector size;

    private int alpha = 0;
    private State state = State.Unloaded;

    public FadeInText(PApplet p, PFont font, String text, PVector position, PVector size) {
        this.p = p;
        this.font = font;

        this.text = text;
        this.position = position;
        this.size = size;
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
        p.text(text, position.x, position.y, size.x, size.y);
    }
}
