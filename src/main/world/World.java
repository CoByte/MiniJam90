package main.world;

import main.misc.DataControl;
import main.misc.IntVector;
import main.misc.Tile;
import main.world.entities.Entity;
import main.world.entities.Fire;
import main.world.entities.Illusion;
import main.world.entities.MovingPlatform;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static main.Main.*;

public class World {

    public final Tile.TileDS TILEMAP;

    private final PApplet P;

    // List of all entities in the scene
    public ArrayList<Entity> entities;
    public Illusion illusion;

    private Entity test;
    private Player player;

    public World(PApplet p) {
        P = p;

        TILEMAP = new Tile.TileDS();
        for (int y = 0; y <= BOARD_SIZE.y / TILE_SIZE; y++) {
            for (int x = 0; x <= BOARD_SIZE.x / TILE_SIZE; x++) {
                TILEMAP.add(new Tile(p, this, new PVector(x * TILE_SIZE, y * TILE_SIZE), TILEMAP.size()), x, y);
            }
        }

        entities = new ArrayList<>();

        DataControl.loadLevel("test", TILEMAP);

//        test = new MovingPlatform(
//                P,
//                new PVector(250, 250),
//                new PVector(600, 250),
//                3,
//                10
//        );
        test = new Fire(P, new PVector(400, 800), this);
        player = new Player(P, new PVector(200, BOARD_SIZE.y - 200), this);

        entities.add(player);
        entities.add(test);
    }

    public void main() {
        for (int i = 0; i < TILEMAP.size(); i++) {
            Tile tile = TILEMAP.get(i);
            tile.displayBaseAndDecoration();
            if (debug) tile.collider.display(tile.position);
        }
        for (int i = 0; i < TILEMAP.size(); i++) {
            TILEMAP.get(i).displayObstacle();
        }

        update();
        display();
    }

    public ArrayList<Entity> getCollidingEntities(Entity entity) {
        ArrayList<Entity> collided = new ArrayList<>();

        collided.addAll(getTileCollision(entity));
        collided.addAll(entities.stream()
                .filter(e -> e != entity)
                .filter(e -> entity.collider.intersects(
                        entity.position,
                        e.position,
                        e.collider
                ))
                .collect(Collectors.toList()));
        if (illusion != null &&
                entity.collider.intersects(entity.position, illusion.getPosition(), illusion.getCollider()))
            collided.add(illusion);

        return collided;
    }

    private ArrayList<Entity> getTileCollision(Entity entity) {
        IntVector[] hitBoxCorners = entity.collider.getCornerGridPositions(entity.position, 0);

        return Arrays.stream(hitBoxCorners)
                .map(TILEMAP::get)
                .filter(Objects::nonNull)
                .filter(tile -> tile.obstacleName != null)
//                .filter(e -> entity.collider.intersects(
//                        entity.position,
//                        e.position,
//                        e.collider
//                ))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void update() {
        for (int i = 0; i < TILEMAP.size(); i++) {
            TILEMAP.get(i).update();
        }
        entities.forEach(Entity::update);
        if (illusion != null) illusion.update();
    }

    private void display() {
        entities.forEach(Entity::draw);
        if (illusion != null) illusion.draw();
    }
}
