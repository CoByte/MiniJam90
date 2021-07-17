package main.misc;

import processing.core.PImage;

public class Animator {

    private final PImage[] ANIMATION;
    private final Timer ANIMATION_TIMER;

    private int frame;

    /**
     * Handles animating
     * @param animation a looped sequence of images
     * @param betweenFrames how many frames before flipping to next image
     */
    public Animator(PImage[] animation, int betweenFrames) {
        ANIMATION = animation;
        ANIMATION_TIMER = new Timer(animation.length, betweenFrames);
    }

    /**
     * Flip to next image, loop back to start if at end
     */
    public void update() {
        ANIMATION_TIMER.update();
        ANIMATION_TIMER.triggered(true);
    }

    /**
     * Gets the current frame, resets back to the beginning if it's past the end
     * @return the current frame of the animation
     */
    public PImage getCurrentFrame() {
        if (ANIMATION_TIMER.getCurrentTime() > ANIMATION.length) ANIMATION_TIMER.reset();
        return ANIMATION[ANIMATION_TIMER.getCurrentTime()];
    }

    /**
     * Send back to first frame
     */
    public void reset() {
        ANIMATION_TIMER.reset();
    }
}
