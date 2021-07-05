package main.misc;

import processing.core.PApplet;

import static main.Main.keysPressed;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;

public class InputHandler {

    public static final char KEY_DELETE = '*';
    public static final char KEY_TAB = '?';
    public static final char KEY_ESC = '|';
    public static final char KEY_LEFT = '<';
    public static final char KEY_UP = '^';
    public static final char KEY_RIGHT = '>';
    public static final char KEY_DOWN = '&';

    public boolean rightMouseReleasedPulse;
    public boolean leftMouseReleasedPulse;
    public boolean rightMousePressed;
    public boolean leftMousePressed;
    public boolean rightMousePressedPulse;
    public boolean leftMousePressedPulse;

    private final PApplet P;

    /**
     * Handles input from keyboard and mouse.
     * @param p the PApplet
     */
    public InputHandler(PApplet p) {
        this.P = p;
        loadKeyBinds();
    }

    /**
     * Handles input from the mouse.
     * @param b mouse pressed
     */
    public void mouse(boolean b) {
        if (b) {
            if (P.mouseButton == RIGHT) {
                if (!rightMousePressed) rightMousePressedPulse = true;
                rightMousePressed = true;
            }
            if (P.mouseButton == LEFT) {
                if (!leftMousePressed) leftMousePressedPulse = true;
                leftMousePressed = true;
            }
        } else {
            if (rightMousePressed) {
                rightMouseReleasedPulse = true;
                rightMousePressed = false;
            }
            if (leftMousePressed) {
                leftMouseReleasedPulse = true;
                leftMousePressed = false;
            }
        }
    }

    /**
     * Handles input from the keyboard.
     * @param b any key pressed
     */
    public void key(boolean b) {
        for (KeyDS.KeyDSItem item : keysPressed.items) {
            if (item.key == P.key) {
                item.pressed = b;
                item.pressedPulse = b;
                item.releasedPulse = !b;
            }
        }
    }

    public static class KeyDS {

        public KeyDSItem[] items;

        /**
         * Contains all the keys from the keyboard
         */
        public KeyDS() {
            items = new KeyDSItem[0];
        }

        public void add(char key) {
            KeyDSItem[] newItems = new KeyDSItem[items.length + 1];
            System.arraycopy(items, 0, newItems, 0, items.length);
            newItems[items.length] = new KeyDSItem(key);
            items = newItems;
        }

        public boolean getPressed(char key) {
            boolean r = false;
            for (KeyDSItem item : items) if (item.key == key) r = item.pressed;
            return r;
        }

        public boolean getPressedPulse(char key) {
            boolean r = false;
            for (KeyDSItem item : items) if (item.key == key) r = item.pressedPulse;
            return r;
        }

        public boolean getReleasedPulse(char key) {
            boolean r = false;
            for (KeyDSItem item : items) if (item.key == key) r = item.releasedPulse;
            return r;
        }

        static class KeyDSItem {

            char key;
            boolean pressed;
            boolean pressedPulse;
            boolean releasedPulse;

            KeyDSItem(char key) {
                this.key = key;
                pressed = false;
            }
        }
    }

    private void loadKeyBinds() {
        keysPressed.add('`');
        keysPressed.add('1');
        keysPressed.add('2');
        keysPressed.add('3');
        keysPressed.add('4');
        keysPressed.add('5');
        keysPressed.add('6');
        keysPressed.add('7');
        keysPressed.add('8');
        keysPressed.add('9');
        keysPressed.add('0');
        keysPressed.add('-');
        keysPressed.add('=');
        keysPressed.add('~');
        keysPressed.add('!');
        keysPressed.add('@');
        keysPressed.add('#');
        keysPressed.add('$');
        keysPressed.add('%');
        keysPressed.add('^');
        keysPressed.add('&');
        keysPressed.add('*');
        keysPressed.add('(');
        keysPressed.add(')');
        keysPressed.add('_');
        keysPressed.add('+');
        keysPressed.add('q');
        keysPressed.add('w');
        keysPressed.add('e');
        keysPressed.add('r');
        keysPressed.add('t');
        keysPressed.add('y');
        keysPressed.add('u');
        keysPressed.add('i');
        keysPressed.add('o');
        keysPressed.add('p');
        keysPressed.add('[');
        keysPressed.add(']');
        keysPressed.add('Q');
        keysPressed.add('W');
        keysPressed.add('E');
        keysPressed.add('R');
        keysPressed.add('T');
        keysPressed.add('Y');
        keysPressed.add('U');
        keysPressed.add('I');
        keysPressed.add('O');
        keysPressed.add('P');
        keysPressed.add('{');
        keysPressed.add('}');
        keysPressed.add('|');
        keysPressed.add('a');
        keysPressed.add('s');
        keysPressed.add('d');
        keysPressed.add('f');
        keysPressed.add('g');
        keysPressed.add('h');
        keysPressed.add('j');
        keysPressed.add('k');
        keysPressed.add('l');
        keysPressed.add(';');
        keysPressed.add('A');
        keysPressed.add('S');
        keysPressed.add('D');
        keysPressed.add('F');
        keysPressed.add('G');
        keysPressed.add('H');
        keysPressed.add('J');
        keysPressed.add('K');
        keysPressed.add('L');
        keysPressed.add(':');
        keysPressed.add('"');
        keysPressed.add('z');
        keysPressed.add('x');
        keysPressed.add('c');
        keysPressed.add('v');
        keysPressed.add('b');
        keysPressed.add('n');
        keysPressed.add('m');
        keysPressed.add(',');
        keysPressed.add('.');
        keysPressed.add('/');
        keysPressed.add('Z');
        keysPressed.add('X');
        keysPressed.add('C');
        keysPressed.add('V');
        keysPressed.add('B');
        keysPressed.add('N');
        keysPressed.add('M');
        keysPressed.add('<');
        keysPressed.add('>');
        keysPressed.add('?');
        keysPressed.add(' ');
    }
}
