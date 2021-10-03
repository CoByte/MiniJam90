package main.world;

import com.jogamp.newt.event.KeyEvent;
import main.misc.DataControl;
import main.misc.InputManager;
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

    private enum State {
        Playing, Resetting, Advancing, Exiting
    }

    public final Tile.TileDS TILEMAP;

    private final PApplet P;

    // List of all entities in the scene
    public ArrayList<Entity> entities;
    public Illusion illusion;

    public ArrayList<Particle> behindParticles;
    public ArrayList<Particle> inFrontParticles;

    private State currentState = State.Playing;

    private float black_x;

    private Player player;

    public World(PApplet p, String levelFile) {
        P = p;
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
        entities.add(player);

        black_x = BOARD_SIZE.x;
    }

    public void main() {
        update();
        display();
        exitControl();

        if (InputManager.getInstance().getEvent(KeyEvent.VK_ESCAPE).rising()) exit();

        //todo: temp
        fadeSoundLoops.get("music").setTargetVolume(1);
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

        entities.forEach(Entity::draw);
        if (illusion != null) illusion.draw();

        for (int i = 0; i < TILEMAP.size(); i++) {
            TILEMAP.get(i).displayObstacle();
        }

        for (int i = inFrontParticles.size() - 1; i >= 0; i--) {
            Particle particle = inFrontParticles.get(i);
            particle.main();
        }
    }

    private void exitControl() {
        black_x += 50;

        if (currentState != State.Playing) {
            if (black_x > BOARD_SIZE.x) {
                switch (currentState) {
                    case Resetting:
                        worlds.set(currentWorld, WorldBuilder.buildWorld(P, currentWorld));
                        break;
                    case Advancing:
                        currentWorld++;
                        break;
                    case Exiting:
                        scene = Scene.TitleScreen;
                        break;
                }
            }
        }

        P.fill(0);
        P.rect(black_x, 0, BOARD_SIZE.x * -1.5f, BOARD_SIZE.y);
    }

    private void setBlack() {
        if (currentState.equals(State.Playing)) {
            black_x = 0;
        }
    }

    public void reset() {
        setBlack();
        currentState = State.Resetting;
    }

    public void advance() {
        setBlack();
        currentState = State.Advancing;
    }

    public void exit() {
        setBlack();
        currentState = State.Exiting;
    }
}
