package main.world;

import main.Main;
import main.misc.Tile;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class World {

    public final Tile.TileDS TILEMAP;

    private final PApplet P;

    private Player player;

    public World(PApplet p) {
        P = p;

        TILEMAP = new Tile.TileDS();
        for (int y = 0; y <= BOARD_SIZE.y / TILE_SIZE; y++) {
            for (int x = 0; x <= BOARD_SIZE.x / TILE_SIZE; x++) {
                TILEMAP.add(new Tile(p, new PVector(x * TILE_SIZE, y * TILE_SIZE), TILEMAP.size()), x, y);
            }
        }

        player = new Player(P, new PVector(200, BOARD_SIZE.y - 200));
    }

    public void main() {
        for (int i = 0; i < TILEMAP.size(); i++) {
            TILEMAP.get(i).displayBaseAndDecoration();
        }

        player.display();
    }
}
