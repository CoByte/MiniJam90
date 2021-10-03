package main.sound;

import processing.core.PApplet;
import processing.sound.SoundFile;

import static main.Main.sounds;

public class SoundLoader {

    public static void loadSounds(PApplet p) {
        //ui
        sounds.put("clickIn", new SoundFile(p, "sounds/gui/clickIn.wav"));
        sounds.put("clickOut", new SoundFile(p, "sounds/gui/clickOut.wav"));

        //player
        sounds.put("die", new SoundFile(p, "sounds/die.wav"));
        sounds.put("cast", new SoundFile(p, "sounds/cast.wav"));
        sounds.put("castFail", new SoundFile(p, "sounds/castFail.wav"));
    }
}
