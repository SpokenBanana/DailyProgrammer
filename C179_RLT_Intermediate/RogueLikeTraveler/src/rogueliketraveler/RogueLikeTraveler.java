package rogueliketraveler;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

public class RogueLikeTraveler extends JPanel implements KeyListener{
    public static void main(String[] args) {
        JFrame f = new JFrame();
        RogueLikeTraveler r = new RogueLikeTraveler();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setFocusable(true);
        f.add(r);
        f.addKeyListener(r);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        
    }
    char[][] board;
    final char PLAYER, WALL, COIN;
    int moves, points;
    BufferedImage imgPlayer, imgWall, imgCoin, map;
    Point playerPos;

    public RogueLikeTraveler() {
        PLAYER = '@';
        WALL = '#';
        COIN = '%';
        moves = 100;
        try {
            imgPlayer = ImageIO.read(this.getClass().getResource("graphics/player.png"));
            imgWall = ImageIO.read(this.getClass().getResource("graphics/wall.png"));
            imgCoin = ImageIO.read(this.getClass().getResource("graphics/coin.png"));
            map = ImageIO.read(this.getClass().getResource("graphics/Levels/first.png"));
            parsePicToBoard(map);
        }catch(IOException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(600, 700));
    }
    public void parsePicToBoard(BufferedImage pic) {
        board = new char[20][20];
        for (int i = 0; i < 20;i++) {
            for (int j = 0; j < 20; j++) {
                if (new Color(pic.getRGB(i, j)).equals(Color.GREEN)) 
                    board[i][j] = COIN;
                else if (new Color(pic.getRGB(i, j)).equals(Color.WHITE)){
                    playerPos = new Point(i, j);
                    board[i][j] = PLAYER;
                }
                else if (new Color(pic.getRGB(i, j)).equals( Color.RED))
                    board[i][j] = WALL;
                else 
                    board[i][j] = ' ';
            }
        }
    }
    public void step(int move) {
        Point toMoveTo = null;
        if (move == KeyEvent.VK_RIGHT) 
            toMoveTo = new Point(playerPos.x + 1, playerPos.y);
        else if (move == KeyEvent.VK_LEFT) 
            toMoveTo = new Point(playerPos.x - 1, playerPos.y);
        else if (move == KeyEvent.VK_DOWN) 
            toMoveTo = new Point(playerPos.x, playerPos.y + 1);
        else if (move == KeyEvent.VK_UP) 
            toMoveTo = new Point(playerPos.x, playerPos.y - 1);
        
        if (toMoveTo == null || board[toMoveTo.x][toMoveTo.y] == WALL) return;
        if (new Random().nextInt(100) < 5) spawnRandomCoin();
        if (board[toMoveTo.x][toMoveTo.y] == COIN) {
            points += 100;
            spawnRandomCoin();
        }
        movePlayer(toMoveTo);
    }
    public void movePlayer(Point toMoveTo) {
        board[playerPos.x][playerPos.y] = ' ';
        playerPos = toMoveTo;
        board[playerPos.x][playerPos.y] = PLAYER;
        moves--;
    }
    public void spawnRandomCoin() {
        Random r = new Random();
        int x = r.nextInt(20), y = r.nextInt(20);
        while (board[x][y] != ' ') {
            x = r.nextInt(20);
            y = r.nextInt(20);
        }
        board[x][y] = COIN;
    }
    public void reset() {
        moves = 100;
        points = 0;
        try {
            parsePicToBoard(map);
        } catch (Exception e) {}
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, board.length * 30, getHeight());
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == PLAYER) g.drawImage(imgPlayer, i * 30, j * 30, null);
                else if (board[i][j] == WALL) g.drawImage(imgWall, i * 30, j * 30, null);
                else if (board[i][j] == COIN) g.drawImage(imgCoin, i * 30, j * 30, null);
            }
        }
        g.setColor(Color.WHITE);
        g.drawString("Moves: " + moves, 20, 650);
        g.drawString("Points: " + points, 100, 650);
        g.setColor(Color.WHITE);
        g.drawString("Press I to import your own level! Make sure the image file is 20x20", 20, 670);
        g.drawString("Each pixel represents something: Red = Wall, Green = Coin, White = Player", 20, 685);
        g.setColor(Color.RED);
        if (moves == 0)
            g.drawString("DONE! press R to play again!", 200, 650);
    }

    public void keyPressed(KeyEvent e) {
        if (moves == 0) {
            if (e.getKeyCode() == KeyEvent.VK_R)
                reset();
        }
        else if (e.getKeyCode() == KeyEvent.VK_I) {
            try{
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif", "png"));
                int returnVal = chooser.showOpenDialog(this);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    String path = chooser.getSelectedFile().getPath();
                    map = ImageIO.read(new File(path));
                    reset();
            }
        } catch (Exception ex) { System.exit(0); }
        }
        else {
            step(e.getKeyCode());
        }
        repaint();
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}