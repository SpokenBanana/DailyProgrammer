package Games.Hunt;

import Main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player {
    public ArrayList<Bullet> bullets;
    public Rectangle pos;
    protected Hunting.Direction direction;
    protected Hunting.Direction oldDirection;

    public Player() {
        oldDirection = direction = Hunting.Direction.STANDING;
        bullets = new ArrayList<Bullet>();
        pos = new Rectangle(0,0, 30,60);
    }

    public void filterBullets() {
        ArrayList<Bullet> toDel = new ArrayList<Bullet>();
        for (Bullet bullet : bullets) {
            if (bullet.pos.x <= 0 || bullet.pos.x >= Game.WIDTH || bullet.pos.y < 0 || bullet.pos.y > Game.HEIGHT)
                toDel.add(bullet);
        }
        for (Bullet bullet : toDel) bullets.remove(bullet);
    }

    public void shoot() {
        bullets.add(new Bullet(oldDirection));
    }

    public void update() {
        if (Game.keys.isDown(KeyEvent.VK_RIGHT))
            pos.x += 5;
        else if (Game.keys.isDown(KeyEvent.VK_LEFT))
            pos.x -= 5;
        else if (Game.keys.isDown(KeyEvent.VK_DOWN))
            pos.y += 5;
        else if (Game.keys.isDown(KeyEvent.VK_UP))
            pos.y -= 5;
        if (pos.x < 0 || pos.x > Game.WIDTH || pos.y < 0 || pos.y > Game.HEIGHT) {

        }

    }
    public void draw(Graphics g) {

    }
}
