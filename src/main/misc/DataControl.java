package main.misc;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static main.Main.*;

public class DataControl extends ClassLoader {

    private static String filePath() {
        //run from terminal
        String filePath = new File("").getAbsolutePath();
        //run from intelliJ todo: comment out before release
        filePath = "resources";
        return filePath;
    }

    /**
     * Saves level data to a JSON file.
     */
    public static void saveLevel() {
        JSONArray saveArray = new JSONArray();
        //tiles
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            JSONObject saveObject = new JSONObject();
            saveObject.setString("type", "tile");
            saveObject.setInt("id", i);
            saveObject.setString("base", tile.baseName);
            saveObject.setString("decoration", tile.decorationName);
            saveObject.setString("breakable", tile.breakableName);
            saveObject.setString("obstacle", tile.obstacleName);
            saveArray.setJSONObject(i, saveObject);
        }
        String name = "Save-"+month()+"-"+day()+"-"+year()+"-"+hour() +"-"+minute()+"-"+second();
        new File(filePath() + "/data/saveData/" + name + ".json");
        try {
            FileWriter saveWriter = new FileWriter("resources/data/saveData/" + name + ".json");
            saveWriter.write(saveArray.toString());
            saveWriter.close();
        } catch (IOException ex) {
            System.out.println("failed to save " + "resources/data/saveData/" + name + ".json");

        }
    }

    /**
     * Saves settings to a JSON file
     */
    public static void saveSettings() {
        JSONObject saveObject = new JSONObject();
        saveObject.setFloat("volume", globalVolume);

        String name = "settings";
        new File(filePath() + "/data/saveData/" + name + ".json");
        try {
            FileWriter saveWriter = new FileWriter(filePath() + "/data/saveData/" + name + ".json");
            saveWriter.write(saveObject.toString());
            saveWriter.close();
        } catch (IOException exception) {
            System.out.println("failed to save " + filePath() + "/data/saveData/" + name + ".json");
        }
    }

    public static void savePlayerData() {
        JSONObject saveObject = new JSONObject();
        saveObject.setInt("placeholder", 0);

        String name = "playerData";
        new File(filePath() + "/data/saveData/" + name + ".json");
        try {
            FileWriter saveWriter = new FileWriter(filePath() + "/data/saveData/" + name + ".json");
            saveWriter.write(saveObject.toString());
            saveWriter.close();
        } catch (IOException exception) {
            System.out.println("failed to save " + filePath() + "/data/saveData/" + name + ".json");
        }
    }

    public static void loadPlayerData() {
        File loadFile = new File(filePath() + "/data/saveData/playerData.json");
        JSONObject loadObject = loadJSONObject(loadFile);

        int testValue = loadObject.getInt("placeholder");

        System.out.println(testValue);
    }

    public static void loadSettings() {
        File loadFile = new File(filePath()+"/data/settings.json");
        JSONObject loadObject = loadJSONObject(loadFile);

        globalVolume = loadObject.getFloat("volume");
    }

    /**
     * Loads level data from a JSON file.
     * @param file the filename, sans extension.
     */
    public static void loadLevel(String file) {
        File loadFile = new File(filePath()+"/data/"+file+".json");
        JSONArray loadArray = loadJSONArray(loadFile);

        //tiles
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            JSONObject loadedTile = loadArray.getJSONObject(i);

            String base = loadedTile.getString("base");
            String decoration = loadedTile.getString("decoration");
            String breakable = loadedTile.getString("breakable");
            String obstacle = loadedTile.getString("obstacle");

            tile.setBase(base);
            tile.setDecoration(decoration);
            tile.setBreakable(breakable);
            tile.setObstacle(obstacle);
        }
    }

    /**
     * Loads data about a single tile from JSON
     * @param tile Tile to load data about
     * @param file file to load data from, no extension
     */
    public static void loadTile(Tile tile, String file) {
        File loadFile = new File(filePath()+"/data/"+file+".json");
        JSONArray loadArray = loadJSONArray(loadFile);

        JSONObject loadedTile = loadArray.getJSONObject(tile.id);
        String base = loadedTile.getString("base");
        String decoration = loadedTile.getString("decoration");
        String breakable = loadedTile.getString("breakable");
        String obstacle = loadedTile.getString("obstacle");
        if (base != null) tile.setBase(base);
        tile.setDecoration(decoration);
        tile.setBreakable(breakable);
        tile.setObstacle(obstacle);
    }
}
