package main.world;

import main.misc.DataControl;
import main.misc.IntVector;
import main.misc.Tile;
import main.particles.Particle;
import main.world.entities.Entity;
import main.world.entities.Illusion;
import main.world.entities.Lever;
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

    private final boolean hasBeenLoaded;

    public DialogueEngine dialogue;

    // List of all entities in the scene
    public ArrayList<Entity> entities;
    public Illusion illusion;

    public ArrayList<Particle> behindParticles;
    public ArrayList<Particle> inFrontParticles;

    private Player player;

    public World(PApplet p, String levelFile, boolean hasBeenLoaded) {
        P = p;
        this.hasBeenLoaded = hasBeenLoaded;

        TILEMAP = new Tile.TileDS();
        for (int y = 0; y <= BOARD_SIZE.y / TILE_SIZE; y++) {
            for (int x = 0; x <= BOARD_SIZE.x / TILE_SIZE; x++) {
                TILEMAP.add(new Tile(p, this, new PVector(x * TILE_SIZE, y * TILE_SIZE), TILEMAP.size()), x, y);
            }
        }
        DataControl.loadLevel(levelFile, TILEMAP);

        entities = new ArrayList<>();
        behindParticles = new ArrayList<>();
        inFrontParticles = new ArrayList<>();

        player = new Player(P, new PVector(200, BOARD_SIZE.y - 200), this);

        dialogue = new DialogueEngine(p, player, new ArrayList<>());
    }

    public void addDialogue(String... text) {
        dialogue.dialogue.addAll(Arrays.asList(text));
    }

    public void main() {
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
        if (entity.collider.intersects(entity.position, player.position, player.collider))
            collided.add(player);

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

        if (dialogue.finished()) player.update();

        entities.forEach(Entity::update);
        if (illusion != null) illusion.update();
    }

    private void display() {
        for (int i = 0; i < TILEMAP.size(); i++) {
            Tile tile = TILEMAP.get(i);
            tile.displayBaseAndDecoration();
            if (debug) tile.collider.display(tile.position);
        }

        for (int i = behindParticles.size() - 1; i >= 0; i--) {
            Particle particle = behindParticles.get(i);
            particle.main();
        }

        if (illusion != null && illusion.trueEntity instanceof Lever) {
            illusion.drawLeverBack();
        }

        for (Entity entity : entities) {
            if (entity instanceof Lever) {
                ((Lever) entity).drawBack();
            }
        }

        player.draw();

        entities.forEach(Entity::draw);
        if (illusion != null) illusion.draw();

        for (int i = 0; i < TILEMAP.size(); i++) {
            TILEMAP.get(i).displayObstacle();
        }

        for (int i = inFrontParticles.size() - 1; i >= 0; i--) {
            Particle particle = inFrontParticles.get(i);
            particle.main();
        }

        if (!dialogue.finished()) {
            dialogue.play();
        }
    }

    public void reset() {
        worlds.set(currentWorld, WorldBuilder.buildWorld(P, currentWorld));
    }
}
