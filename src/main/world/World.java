package main.world;

import main.Main;
import main.misc.DataControl;
import main.misc.Tile;
import main.world.entities.Entity;
import main.world.entities.MovingPlatform;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class World {

    public final Tile.TileDS TILEMAP;

    private final PApplet P;

    private Player player;

    private Entity test;

    public World(PApplet p) {
        P = p;

        TILEMAP = new Tile.TileDS();
        for (int y = 0; y <= BOARD_SIZE.y / TILE_SIZE; y++) {
            for (int x = 0; x <= BOARD_SIZE.x / TILE_SIZE; x++) {
                TILEMAP.add(new Tile(p, new PVector(x * TILE_SIZE, y * TILE_SIZE), TILEMAP.size()), x, y);
//                //todo: temp
//                if (y > (BOARD_SIZE.y - 200) / TILE_SIZE) {
//                    TILEMAP.get(x, y).setObstacle("wall006Ob_TL");
//                }
            }
        }

        DataControl.loadLevel("test", TILEMAP);

        test = new MovingPlatform(
                P,
                new PVector(250, 250),
                new PVector(600, 250),
                3,
                10
        );
        player = new Player(P, new PVector(200, BOARD_SIZE.y - 200), this);
    }

    public void main() {
        for (int i = 0; i < TILEMAP.size(); i++) {
            TILEMAP.get(i).displayObstacle();
        }

        update();
        display();
    }

    private void update() {
        player.update();
        test.update();
    }

    private void display() {
        player.display();
        test.draw();
    }
}
