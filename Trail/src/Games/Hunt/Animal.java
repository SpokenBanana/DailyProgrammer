package Games.Hunt;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Animal {
    public int health, vel;
    public Rectangle pos;
    public boolean alerted;
    Hunting.Direction direction;
    Random random;
    BufferedImage sprite;

    public Animal(int _health, Rectangle _pos) {
        health = _health;
        alerted = false;
        pos = _pos;
        direction = Hunting.Direction.STANDING;
        // the amount of health determines the kind of animal
        // switch case on health | 1=raccoon 2=deer, 3=ox
    }
    public void update() {
        if (alerted) {
            if (direction == Hunting.Direction.STANDING) {
                Hunting.Direction[] dirs = {Hunting.Direction.DOWN, Hunting.Direction.UP,
                        Hunting.Direction.LEFT, Hunting.Direction.RIGHT};
                direction = dirs[random.nextInt(dirs.length)];
            }
            switch (direction) {
                case DOWN:
                    pos.y -= vel;
                    break;
                case UP:
                    pos.y += vel;
                    break;
                case LEFT:
                    pos.x -= vel;
                    break;
                case RIGHT:
                    pos.y += vel;
            }
            return; // don't want too many nested brackets
        }
        if (direction == Hunting.Direction.STANDING) {
            if (random.nextInt(100) < 2){
                Hunting.Direction[] dirs = {Hunting.Direction.DOWN, Hunting.Direction.UP,
                                            Hunting.Direction.LEFT, Hunting.Direction.RIGHT};
                direction = dirs[random.nextInt(dirs.length)];
            }
        }
        else {
            switch (direction) {
                case DOWN:
                    pos.y -= vel;
                    break;
                case UP:
                    pos.y += vel;
                    break;
                case LEFT:
                    pos.x -= vel;
                    break;
                case RIGHT:
                    pos.y += vel;
            }
            if (pos.y < 0) pos.y = 0;
            else if (pos.x < 0) pos.x = 0;
            if (random.nextInt(100) < 20)
                direction = Hunting.Direction.STANDING;
        }
    }
    public void draw(Graphics g) {
        g.drawImage(sprite, pos.x, pos.y, pos.width, pos.height, null);
    }
}
