package Main;

import Handlers.GameStateManager;
import Handlers.KeyHandler;

import javax.swing.*;

public class Game extends JPanel {
    GameStateManager g;
    public static KeyHandler keys;
    public static final int WIDTH = 500, HEIGHT = 500;

    public Game() {
        addKeyListener(keys);
    }

}
