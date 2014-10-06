package Games.Hunt;

import Components.Wagon;
import Handlers.GameState;
import Main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Hunting extends GameState {
    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        STANDING
    }
    protected ArrayList<Animal> animals;

    public Hunting(Wagon wagon) {

    }
    private void filterAnimals() {

    }
    private void shoot() {

    }
    @Override
    public void update() {
/*        if (Game.keys.isDown(KeyEvent.VK_RIGHT))
            player.x += 5;
        else if (Game.keys.isPressed(KeyEvent.VK_LEFT))
            player.x -= 5;
        else if (Game.keys.isPressed(KeyEvent.VK_DOWN))
            player.y += 5;
        else if (Game.keys.isPressed(KeyEvent.VK_UP))
            player.y -= 5;*/
        if (Game.keys.isPressed(KeyEvent.VK_SPACE))
            shoot();
    }

    @Override
    public void draw(Graphics g) {

    }
}
