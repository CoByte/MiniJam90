package main.world;

import main.Main;
import main.misc.CollisionBox;
import main.misc.IntVector;
import main.misc.Tile;
import main.misc.Utilities;
import main.world.entities.Entity;
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
    private ArrayList<Entity> entities;

    private Player player;

    public World(PApplet p) {
        P = p;

        TILEMAP = new Tile.TileDS();
        for (int y = 0; y <= BOARD_SIZE.y / TILE_SIZE; y++) {
            for (int x = 0; x <= BOARD_SIZE.x / TILE_SIZE; x++) {
                TILEMAP.add(new Tile(p, new PVector(x * TILE_SIZE, y * TILE_SIZE), TILEMAP.size()), x, y);
                //todo: temp
                if (y > (BOARD_SIZE.y - 200) / TILE_SIZE) {
                    TILEMAP.get(x, y).setBase("groundBa_TL");
                }
            }
        }

        entities = new ArrayList<>();

        player = new Player(P, new PVector(300, BOARD_SIZE.y - 200), this);

        entities.add(player);
    }

    public void main() {
        for (int i = 0; i < TILEMAP.size(); i++) {
            Tile tile = TILEMAP.get(i);
            tile.displayBaseAndDecoration();
            if (debug) tile.collider.display(tile.position);
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

        return collided;
    }

    private ArrayList<Entity> getTileCollision(Entity entity) {
        IntVector[] hitBoxCorners = entity.collider.getCornerGridPositions(entity.position, 0);

        return Arrays.stream(hitBoxCorners)
                .map(TILEMAP::get)
                .filter(Objects::nonNull)
                .filter(tile -> tile.baseName != null)
//                .filter(e -> entity.collider.intersects(
//                        entity.position,
//                        e.position,
//                        e.collider
//                ))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void update() {
        entities.forEach(Entity::update);
    }

    private void display() {
        entities.forEach(Entity::draw);
    }
}
