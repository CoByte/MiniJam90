package main.misc;

import processing.core.PApplet;

public class Timer {

    private final PApplet P;
    private final int BETWEEN_COUNTS;

    private int alarmTime;
    private int counter;

    /**
     * Counts up when updated
     * @param alarmTime when the alarm will trigger
     * @param betweenCounts how many frames to wait before incrementing timer
     * @param startTriggered if the alarms starts triggered, or untriggered
     */
    public Timer(PApplet p, int alarmTime, int betweenCounts, boolean startTriggered) {
        P = p;
        BETWEEN_COUNTS = betweenCounts;
        this.alarmTime = alarmTime;
        if (startTriggered) counter = this.alarmTime;
    }

    /**
     * Counts up when updated
     * @param alarmTime when the alarm will trigger
     */
    public Timer(PApplet p, int alarmTime) {
        this(p, alarmTime, 1, false);
    }

    /**
     * Counts up when updated
     * @param alarmTime when the alarm will trigger
     * @param betweenCounts how many frames to wait before incrementing timer
     */
    public Timer(PApplet p, int alarmTime, int betweenCounts) {
        this(p, alarmTime, betweenCounts, false);
    }

    /**
     * Counts up when updated
     * @param alarmTime when the alarm will trigger
     * @param startTriggered if the alarms starts triggered, or untriggered
     */
    public Timer(PApplet p, int alarmTime, boolean startTriggered) {
        this(p, alarmTime, 1, startTriggered);
    }

    /**
     * Counts up when updated
     */
    public Timer(PApplet p) {
        this(p, 0);
    }

    /**
     * Increase counter,
     * between counts will only work if this is run every frame
     */
    public void update() {
        if (P.frameCount % BETWEEN_COUNTS == 0) counter++;
    }

    public int getCurrentTime() {
        return counter;
    }

    /**
     * Checks counter, and resets if true and reset enabled
     * @return if the counter is >= alarm time
     */
    public boolean triggered(boolean reset) {
        if (counter >= alarmTime) {
            if (reset) reset();
            return true;
        } return false;
    }

    public void reset() {
        counter = 0;
    }

    public void setAlarmTime(int alarmTime) {
        this.alarmTime = alarmTime;
    }
}
