package Games.Hunt;

import java.awt.*;

public class Bullet {
    public Rectangle pos;
    Hunting.Direction direction;
    public Bullet(Hunting.Direction _direction) {
        direction = _direction;
    }
    public void update() {
        switch (direction) {
            case DOWN:
                pos.y += 7;
                break;
            case UP:
                pos.y -= 7;
                break;
            case LEFT:
                pos.x -= 7;
                break;
            case RIGHT:
                pos.x += 7;
        }
    }
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(pos.x, pos.y, pos.width,pos.height);
    }
}
