package main.world.entities;

import main.Main;
import main.misc.CollisionBox;
import main.world.World;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public abstract class Door extends Entity {
    public static final float WIDTH = Main.TILE_SIZE;
    public static final float HEIGHT = Main.TILE_SIZE * 2;

    public boolean closed = true;

    protected PImage sprite;

    protected final float startY;
    protected final float speed;

    public Door(PApplet p, World world, PVector position, float speed) {
        super(p, world, new CollisionBox(p, new PVector(WIDTH, HEIGHT)), position);
        startY = position.y;
        this.speed = speed;
    }

    @Override
    public void update() {
        super.update();
        if (closed) position.y = Math.max(startY, position.y - speed);
        else position.y += speed;
    }

    @Override
    public void draw() {
        P.image(sprite, position.x, position.y, WIDTH, HEIGHT);
    }
}
