package main.misc;

import main.Main;
import main.particles.FloatParticle;
import main.particles.GravityParticle;
import main.sound.FadeSoundLoop;
import main.world.World;
import main.world.entities.Entity;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static processing.core.PConstants.CORNER;

public class Tile extends Entity {
    public int id;
    public PImage base;
    public String baseName;
    public PImage decoration;
    public String decorationName;
    public PImage breakable;
    public String breakableName;
    public PImage obstacle;
    public String obstacleName;

    private final Timer burnTimer;
    private final FadeSoundLoop fireSound;

    public Tile(PApplet p, World world, PVector position, int id) {
        super(p, world, new CollisionBox(p, new PVector(), new PVector(TILE_SIZE, TILE_SIZE)), position);

        this.id = id;
        this.flammable = false;

        burnTimer = new Timer(Utilities.secondsToFrames(5));
        fireSound = fadeSoundLoops.get("fire");
    }

    public void highlight(Color color) {
        P.noFill();
        P.stroke(color.getRGB());
        P.rectMode(CORNER);
        P.rect(position.x, position.y, TILE_SIZE, TILE_SIZE);
        P.rectMode(DEFAULT_MODE);
    }

    public void displayBaseAndDecoration() {
        if (base != null) P.image(base, position.x, position.y, TILE_SIZE, TILE_SIZE);
        if (decoration != null) P.image(decoration, position.x, position.y, TILE_SIZE, TILE_SIZE);
    }

    public void displayObstacle() {
        if (obstacle != null) P.image(obstacle, position.x, position.y, TILE_SIZE, TILE_SIZE);
        if (onFire) {
            fireSound.setTargetVolume(1);
            if (P.random(5) < 1) {
                PVector pos = getRandPos();
                world.inFrontParticles.add(new FloatParticle(P,
                        pos.x, pos.y, new Color(255, 60, 0), world.inFrontParticles));
            }
            if (P.random(5) < 1) {
                PVector pos = getRandPos();
                world.inFrontParticles.add(new FloatParticle(P,
                        pos.x, pos.y, new Color(255, 183, 0), world.inFrontParticles));
            }
            if (P.random(5) < 1) {
                PVector pos = getRandPos();
                world.inFrontParticles.add(new GravityParticle(P,
                        pos.x, pos.y, new Color(255, 136, 0), world.inFrontParticles));
            }
            burnTimer.update();
            if (burnTimer.triggered(false) && !obstacleName.contains("Charred"))
                setObstacle(obstacleName.replace("wood", "woodCharred"));
        }
    }

    /**
     * Sets the base layer of the tile.
     * @param name name of base
     */
    public void setBase(String name) {
        if (name == null) return;
        name = name.replace("Ba_TL", "");
        baseName = name;
        base = sprites.get(name + "Ba_TL");
    }

    public void setDecoration(String name) {
        if (name == null) {
            decoration = null;
            decorationName = null;
        } else {
            decoration = sprites.get(name);
            decorationName = name;
        }
    }

    public void setBreakable(String name) {
        if (name == null) {
            breakable = null;
            breakableName = null;
        } else {
            breakable = sprites.get(name);
            breakableName = name;
        }
    }

    public void setObstacle(String name) {
        if (name == null) {
            obstacle = null;
            obstacleName = null;
        } else {
            if (name.contains("wood")) flammable = true;
            setDecoration(null);
            setBreakable(null);
            obstacle = sprites.get(name);
            obstacleName = name;
        }
    }

    public PVector getGridPosition() {
        int x = (int) (position.x / 50);
        int y = (int) (position.y / 50);
        return new PVector(x, y);
    }

    /**
     * Tiles have multiple layers, so a single draw doesn't make sense.
     * This is just here because entity requires it
     * FUCK YOU OWEN I'LL MAKE THIS WORK!
     */
    @Override
    public void draw() {
        if (onFire) {
            if (P.random(5) < 1) {
                PVector pos = getRandPos();
                world.inFrontParticles.add(new FloatParticle(P,
                        pos.x, pos.y, Color.RED, world.inFrontParticles));
            }
            if (P.random(5) < 1) {
                PVector pos = getRandPos();
                world.inFrontParticles.add(new FloatParticle(P,
                        pos.x, pos.y, Color.YELLOW, world.inFrontParticles));
            }
            if (P.random(5) < 1) {
                PVector pos = getRandPos();
                world.inFrontParticles.add(new GravityParticle(P,
                        pos.x, pos.y, Color.ORANGE, world.inFrontParticles));
            }
        }
        displayObstacle();
    }

    public static class TileDS {

        public TileDSItem[] items;

        public TileDS() {
            items = new TileDSItem[0];
        }

        public Tile get(int id) {
            return items[id].tile;
        }

        public Tile get(int x, int y) {
            Tile r = null;
            for (TileDSItem item : items) if (item.x == x && item.y == y) r = item.tile;
            return r;
        }

        public Tile get(IntVector gridPosition) {
            return get(gridPosition.x, gridPosition.y);
        }

        public void add(Tile tile, int x, int y) {
            TileDSItem[] newItems = new TileDSItem[items.length + 1];
            System.arraycopy(items, 0, newItems, 0, items.length);
            newItems[items.length] = new TileDSItem(tile, x, y);
            items = newItems;
        }

        public int size() {
            return items.length;
        }

        public void remove(int id) {
            TileDSItem removeItem = items[id];
            if (removeItem != null) {
                TileDSItem[] newItems = new TileDSItem[items.length - 1];
                for (int i = 0; i < items.length; i++) if (items[i] != removeItem) newItems[i] = items[i];
                items = newItems;
            }
        }

        public void remove(int x, int y) {
            TileDSItem removeItem = null;
            for (TileDSItem item : items) if (item.x == x && item.y == y) removeItem = item;
            if (removeItem != null) {
                TileDSItem[] newItems = new TileDSItem[items.length - 1];
                for (int i = 0; i < items.length; i++) if (items[i] != removeItem) newItems[i] = items[i];
                items = newItems;
            }
        }

        public void remove(Tile tile) {
            TileDSItem removeItem = null;
            for (TileDSItem item : items) if (item.tile == tile) removeItem = item;
            if (removeItem != null) {
                TileDSItem[] newItems = new TileDSItem[items.length - 1];
                for (int i = 0; i < items.length; i++) if (items[i] != removeItem) newItems[i] = items[i];
                items = newItems;
            }
        }

        public static class TileDSItem {

            public Tile tile;
            public int x;
            public int y;

            public TileDSItem(Tile tile, int x, int y) {
                this.tile = tile;
                this.x = x;
                this.y = y;
            }
        }
    }
}
