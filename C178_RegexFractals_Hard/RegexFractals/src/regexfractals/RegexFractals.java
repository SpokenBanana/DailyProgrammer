package regexfractals;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    This class is the main logic that is able to output the patterns!
    Given a regex, we split a 2-dimensional array of Strings into quadrants, then split their quadrants into quadrants
    until we have split it as far as we can go. As we split the quadrants, we add the quadrant number the cells are in
    to the String it contains.
    Doing this can give a sort of "fractal" look to it, and seeing it visualized this way is stunning.
 */
public class RegexFractals extends JPanel {
    
    private String[][] board;
    private String reg;
    private int[][] cells;
    private int largest;
    private int cellSize = 2;
    
    public RegexFractals(Dimension dimension) {
        reg = ".*(?:13|31)(.*)"; // starting regex for the user to see in all its glory
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
        splitIntoQuadrants(0,board.length, 0,board.length);
        if (colored) compileColorBoard();
        else compileBinaryBoard();
    }
    private void initBoard(int width, int height) {
        board = new String[width][height];
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                board[i][j] ="";
    }
    /*
        The start(x,y) and end(x,y) specify boundaries of a square (start being the upper left, end being the bottom right)
        that we must split into 4 quadrants.
        After that is done, we then send each quadrant to be split up again until we reach a point where we have
        a square with each quadrant only 1 cell long.
     */
    private void splitIntoQuadrants(int startx, int endx, int starty, int endy) {
        int halfLength = Math.abs(starty - endy) / 2;

        for (int i = startx + halfLength; i < endx; i++)
            for (int j = starty + halfLength; j < endy; j++)
                board[i][j] += "4";

        for (int i = startx + halfLength; i < endx; i++)
            for (int j = starty; j < starty + halfLength; j++)
                board[i][j] += "3";
        
        for (int i = startx; i < startx + halfLength; i++)
            for (int j = starty; j < starty  + halfLength; j++)
                board[i][j] += "2";
        
        for (int i = startx; i < startx + halfLength; i++)
            for (int j = starty + halfLength; j < endy; j++)
                board[i][j] += "1";
        
        if (halfLength != 2) { // base case
            splitIntoQuadrants(startx, startx  + halfLength, starty, starty + halfLength);
            splitIntoQuadrants(startx + halfLength, endx, starty + halfLength, endy);
            splitIntoQuadrants(startx, startx + halfLength, starty + halfLength, endy);
            splitIntoQuadrants(startx + halfLength, endx, starty, starty + halfLength);
        }
    }
    /*
        Board contains all the data from splitting up the multidimensional array into 4 quadrants repeatedly
        as required for the challenge. When then interpret that data and figure out which cell will be colored
        depending on whether or not it matched the regex.
     */
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
    /*
        Board contains all the data from splitting up the multidimensional array into 4 quadrants repeatedly
        as required for the challenge. When then interpret that data and figure out what color each cell is
        depending on the group length of each regex match.
     */
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
                            // I still get some sort null pointer exception for some patterns
                            // so I had to surround it with a try-catch
                            if (m.group(k) != null) amt += m.group(k).length();
                        } catch (NullPointerException e) {
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
                g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }
}
