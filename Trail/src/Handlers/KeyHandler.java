package Handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean[] oldKeys;
    private boolean[] keys;

    public KeyHandler() {
        keys = new boolean[140];
        oldKeys = new boolean[keys.length];

        for (int i = 0; i < keys.length; i++)
            oldKeys[i] = keys[i] = false;
    }
    public boolean isDown(int key) { return keys[key];}
    public boolean isPressed(int key) { return keys[key] && oldKeys[key]; }

    public void update() {
        System.arraycopy(keys, 0, oldKeys, 0, keys.length);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
