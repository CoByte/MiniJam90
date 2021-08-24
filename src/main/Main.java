package main;

import main.gui.Hand;
import main.gui.LevelBuilderGui;
import main.misc.InputHandler;
import main.misc.Tile;
import main.sound.FadeSoundLoop;
import main.sound.SoundWithAlts;
import main.sound.StartStopSoundLoop;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.Sound;
import processing.sound.SoundFile;

import java.awt.*;
import java.util.HashMap;

import static java.lang.Character.toLowerCase;
import static main.misc.InputHandler.*;
import static main.misc.SpriteLoader.loadAnimations;
import static main.misc.SpriteLoader.loadSprites;
import static main.sound.SoundLoader.loadSounds;

public class Main extends PApplet {

    public static final int TILE_SIZE = 50;
    public static final int FRAMERATE = 60;
    public static final int DEFAULT_MODE = CORNER;
    public static final PVector BOARD_SIZE = new PVector(1100, 900);
    public static final String TITLE = "template";

    public static float globalVolume = 0.25f;
    public static boolean debug = false;

    private static final Color BACKGROUND_COLOR = new Color(0, 15, 45);
    private static final boolean FULLSCREEN = true;

    private static float matrixScale;
    private static float matrixOffset;

    public static HashMap<String, PImage> sprites;
    public static HashMap<String, PImage[]> animations;

    public static Sound sound;
    public static HashMap<String, SoundFile> sounds;
    public static HashMap<String, StartStopSoundLoop> startStopSoundLoops;
    public static HashMap<String, FadeSoundLoop> fadeSoundLoops;
    public static HashMap<String, SoundWithAlts> soundsWithAlts;

    public static Tile.TileDS tiles;
    public static InputHandler inputHandler;
    public static InputHandler.KeyDS keysPressed;
    public static PVector matrixMousePosition;
    public static Hand hand;
    public static LevelBuilderGui levelBuilderGui;

    public static void main(String[] args) {
        PApplet.main("main.Main", args);
    }

    @Override
    public void settings() {
        if (FULLSCREEN) {
            fullScreen(P2D);
            noSmooth();
        } else size((int) BOARD_SIZE.x, (int) BOARD_SIZE.y, P2D);
    }

    @Override
    public void setup() {
        frameRate(FRAMERATE);
        surface.setTitle(TITLE);
        setupSound();
        setupSprites();
        setupMisc();
        setupTiles();
        setupFullscreen();
    }

    private void setupTiles() {
        tiles = new Tile.TileDS();
        for (int y = 0; y <= BOARD_SIZE.y / TILE_SIZE; y++) {
            for (int x = 0; x <= BOARD_SIZE.x / TILE_SIZE; x++) {
                tiles.add(new Tile(this, new PVector(x * TILE_SIZE, y * TILE_SIZE), tiles.size()), x, y);
            }
        }
    }

    private void setupMisc() {
        keysPressed = new InputHandler.KeyDS();
        inputHandler = new InputHandler(this);
        keysPressed = new InputHandler.KeyDS();
        hand = new Hand(this);
        levelBuilderGui = new LevelBuilderGui(this);
    }

    private void setupFullscreen() {
        if (hasVerticalBars()) {
            matrixScale = height / BOARD_SIZE.y;
            matrixOffset = (width - (BOARD_SIZE.x * matrixScale)) / 2;
        } else {
            matrixScale = width / BOARD_SIZE.x;
            matrixOffset = (height - (BOARD_SIZE.y * matrixScale)) / 2;
        }
    }

    private void setupSound() {
        sound = new Sound(this);
        sounds = new HashMap<>();
        startStopSoundLoops = new HashMap<>();
        fadeSoundLoops = new HashMap<>();
        soundsWithAlts = new HashMap<>();
        loadSounds(this);
    }

    private void setupSprites() {
        sprites = new HashMap<>();
        animations = new HashMap<>();
        loadSprites(this);
        loadAnimations(this);
    }

    @Override
    public void draw() {
        background(BACKGROUND_COLOR.getRGB());
        drawSound();

        pushFullscreen();

        levelBuilderGui.main();
        hand.main();

        popFullscreen();

        if (inputHandler != null) inputHandler.reset();
    }

    private void drawSound() {
        sound.volume(globalVolume);
        for (StartStopSoundLoop startStopSoundLoop : startStopSoundLoops.values()) startStopSoundLoop.continueLoop();
        for (FadeSoundLoop fadeSoundLoop : fadeSoundLoops.values()) fadeSoundLoop.main();
    }

    private void pushFullscreen() {
        pushMatrix();
        if (hasVerticalBars()) translate(matrixOffset, 0);
        else translate(0, matrixOffset);
        scale(matrixScale);
        if (hasVerticalBars()) {
            matrixMousePosition = new PVector((mouseX - matrixOffset) / matrixScale, mouseY / matrixScale);
        } else {
            matrixMousePosition = new PVector(mouseX / matrixScale, (mouseY - matrixOffset) / matrixScale);
        }
    }

    private void popFullscreen() {
        popMatrix();
        drawBlackBars();
    }

    private boolean hasVerticalBars() {
        float screenRatio = width / (float) height;
        float boardRatio = BOARD_SIZE.x / BOARD_SIZE.y;
        return boardRatio < screenRatio;
    }

    private void drawBlackBars() {
        fill(0);
        rectMode(CORNER);
        noStroke();
        if (hasVerticalBars()) {
            rect(0, 0, matrixOffset, height);
            rect(width - matrixOffset, 0, matrixOffset, height);
        } else {
            rect(0, 0, width, matrixOffset);
            rect(0, height - matrixOffset, width, matrixOffset);
        }
        rectMode(DEFAULT_MODE);
    }

    /**
     * Checks if a key is pressed.
     * Also includes overrides.
     */
    @Override
    public void keyPressed() {
        key = toLowerCase(key);
        if (keyCode == 8 || keyCode == 46) key = KEY_DELETE;
        if (keyCode == 9) key = KEY_TAB;
        if (keyCode == 27) key = KEY_ESC;
        if (keyCode == 37) key = KEY_LEFT;
        if (keyCode == 38) key = KEY_UP;
        if (keyCode == 39) key = KEY_RIGHT;
        if (keyCode == 40) key = KEY_DOWN;
        inputHandler.key(true);
    }

    @Override
    public void keyReleased() {
        inputHandler.key(false);
    }

    @Override
    public void mousePressed() {
        inputHandler.mouse(true);
    }

    @Override
    public void mouseReleased() {
        inputHandler.mouse(false);
    }
}
