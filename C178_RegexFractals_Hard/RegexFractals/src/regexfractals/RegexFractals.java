package regexfractals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.regex.*;
import javax.swing.JPanel;


public class RegexFractals extends JPanel {
    
    private String[][] board;
    private String reg;
    private int[][] cells;
    private int largest;
    private int cellSize = 2;
    
    public RegexFractals(Dimension dimension) {
        reg = ".*(?:13|31)(.*)";
        initBoard(dimension.width, dimension.height);
        setPreferredSize(new Dimension(dimension.width * cellSize, dimension.height * cellSize));
        createFractalBoard(true);
        repaint();
    }
    private Matcher compileRegex(String regex, String line) {
        return Pattern.compile(regex).matcher(line);
    }
    
    public void changePattern(String pattern, boolean colored) {
        reg = pattern;
        initBoard(board.length, board.length);
        createFractalBoard(colored);
        repaint();
    }
    private void createFractalBoard(boolean colored) {
        quadrate(0,board.length, 0,board.length);
        if (colored) compileColorBoard();
        else compileBinaryBoard();
    }
    private void initBoard(int width, int height) {
        board = new String[width][height];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] ="";
            }
        }   
    }
    private void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("");
        }
    }
    private void quadrate(int startx, int endx, int starty, int endy) {
        int width = Math.abs(startx - endx);
        int height = Math.abs(starty - endy);
        
        for (int i = startx + (width/2); i < endx; i++) 
            for (int j = starty; j < starty + (height/2); j++) 
                board[i][j] += "3";
        
        for (int i = startx; i < startx + (width/2); i++) 
            for (int j = starty; j < starty  + (height/2); j++) 
                board[i][j] += "2";
        
        for (int i = startx; i < startx + (width/2); i++) 
            for (int j = starty + (height/2); j < endy; j++) 
                board[i][j] += "1";

        for (int i = startx + (width/2); i < endx; i++) 
            for (int j = starty + (height/2); j < endy; j++) 
                board[i][j] += "4";
        
        if (width != 2) {
            quadrate(startx, startx  + (width/2), starty, starty + (height/2));
            quadrate(startx + (width/2), endx, starty + (height/2), endy);
            quadrate(startx, startx + (width/2), starty + (height/2), endy);
            quadrate(startx + (width/2), endx, starty, starty + (height/2));
        }
    }
    private void compileBinaryBoard() {
        cells = new int[board.length][board[0].length];
        largest = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Matcher m = compileRegex(reg, board[i][j]);
                if (m.find()) cells[i][j] = 1;
                else cells[i][j] = 0;
                if (cells[i][j] > largest) largest = cells[i][j];
            }
        }  
    }
    private void compileColorBoard() {
        cells = new int[board.length][board[0].length];
        largest = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Matcher m = compileRegex(reg, board[i][j]);
                int amt = 0;
                if (m.find()) {
                    for (int k = 0; k <= m.groupCount(); k++) {
                        try {
                            if (m.group(k) != null) amt += m.group(k).length();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                cells[i][j] = amt;
                if (cells[i][j] > largest) largest = cells[i][j];
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        g.fillRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j] == 0) g.setColor(Color.WHITE);
                else g.setColor(new Color(0, 0,((255/largest) * cells[i][j])));
                Color c = Color.green;
                g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }
}
