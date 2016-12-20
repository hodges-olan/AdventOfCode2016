//--- Day 8: Two-Factor Authentication ---
//
//You come across a door implementing what you can only assume is an implementation of two-factor authentication after a long game of requirements telephone.
//
//To get past the door, you first swipe a keycard (no problem; there was one on a nearby desk). Then, it displays a code on a little screen, and you type that code on a keypad. Then, presumably, the door unlocks.
//
//Unfortunately, the screen has been smashed. After a few minutes, you've taken everything apart and figured out how it works. Now you just have to work out what the screen would have displayed.
//
//The magnetic strip on the card you swiped encodes a series of instructions for the screen; these instructions are your puzzle input. The screen is 50 pixels wide and 6 pixels tall, all of which start off, and is capable of three somewhat peculiar operations:
//
//rect AxB turns on all of the pixels in a rectangle at the top-left of the screen which is A wide and B tall.
//rotate row y=A by B shifts all of the pixels in row A (0 is the top row) right by B pixels. Pixels that would fall off the right end appear at the left end of the row.
//rotate column x=A by B shifts all of the pixels in column A (0 is the left column) down by B pixels. Pixels that would fall off the bottom appear at the top of the column.
//For example, here is a simple sequence on a smaller screen:
//
//rect 3x2 creates a small rectangle in the top-left corner:
//
//###....
//###....
//.......
//rotate column x=1 by 1 rotates the second column down by one pixel:
//
//#.#....
//###....
//.#.....
//rotate row y=0 by 4 rotates the top row right by four pixels:
//
//....#.#
//###....
//.#.....
//rotate column x=1 by 1 again rotates the second column down by one pixel, causing the bottom pixel to wrap back to the top:
//
//.#..#.#
//#.#....
//.#.....
//As you can see, this display technology is extremely powerful, and will soon dominate the tiny-code-displaying-screen market. That's what the advertisement on the back of the display tries to convince you, anyway.
//
//There seems to be an intermediate check of the voltage used by the display: after you swipe your card, if the screen did work, how many pixels should be lit?
//
//Your puzzle answer was 110.
//
//--- Part Two ---
//
//You notice that the screen is only capable of displaying capital letters; in the font it uses, each letter is 5 pixels wide and 6 tall.
//
//After you swipe your card, what code is the screen trying to display?
//
//Your puzzle answer was ZJHRKCPLYJ.

package Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hodges-olan
 */
public class Day8 {
    private static final int ROWS = 6;
    private static final int COLUMNS = 50;
    private static final char[][] PIXELS = new char[ROWS][COLUMNS];
    
    public static void main(String[] args) {
        String filePath = "day8.txt";
        ArrayList<String[]> input = readFile(filePath);
        
        // Part 1
        System.out.println("Part One: ");
        partOne(input);
        
        // Part2
        System.out.println("Part Two: ");
        partTwo();
        
    }
    
    private static ArrayList<String[]> readFile(String filePath) {
        ArrayList<String[]> input = new ArrayList<>();
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((read = in.readLine()) != null) {
                input.add(read.trim().split(" "));
            }
        } catch (IOException ex) {
            Logger.getLogger(Day8.class.getName()).log(Level.SEVERE, null, ex);
        }
        return input;
    }
    
    private static void partOne(ArrayList<String[]> input) {
        for(String[] directions : input) {
            performAction(directions);
        }
        displayPanel();
    }
    
    private static void partTwo() {
        for(int i = 0; i < (COLUMNS/5); i++) {
            for(int y = 0; y < ROWS; y++) {
                for(int x = i*5; x < ((i+1)*5); x++) {
                    System.out.print(PIXELS[y][x]);
                }
                System.out.print("\n");
            }
            System.out.println("");
        }
    }
    
    private static void performAction(String[] directions) {
        switch (directions[0]) {
            case "rect":
                rect(directions[1]);
                break;
            case "rotate":
                rotate(directions);
                break;
        }
    }

    private static void rect(String direction) {
        String[] block = direction.split("x");
        for(int y = 0; y < Integer.parseInt(block[1]); y++) {
            for(int x = 0; x < Integer.parseInt(block[0]); x++) {
                PIXELS[y][x] = '#';
            }
        }
    }

    private static void rotate(String[] directions) {
        switch (directions[1]) {
            case "row":
                rotateRow(directions);
                break;
            case "column":
                rotateColumn(directions);
                break;
        }
    }

    private static void rotateRow(String[] directions) {
        String[] rowString = directions[2].split("=");
        char[] newRow = new char[COLUMNS];
        int row = Integer.parseInt(rowString[1]);
        int distance = Integer.parseInt(directions[4]);
        for(int x = 0; x < COLUMNS; x++) {
            if(x+distance < COLUMNS) {
                newRow[x+distance] = PIXELS[row][x];
            } else {
                newRow[x+distance-COLUMNS] = PIXELS[row][x];
            }
        }
        System.arraycopy(newRow, 0, PIXELS[row], 0, COLUMNS);
    }

    private static void rotateColumn(String[] directions) {
        String[] columnString = directions[2].split("=");
        char[] newColumn = new char[ROWS];
        int column = Integer.parseInt(columnString[1]);
        int distance = Integer.parseInt(directions[4]);
        for(int y = 0; y < ROWS; y++) {
            if(y+distance < ROWS) {
                newColumn[y+distance] = PIXELS[y][column];
            } else {
                newColumn[y+distance-ROWS] = PIXELS[y][column];
            }
        }
        for(int y = 0; y < ROWS; y++) {
            PIXELS[y][column] = newColumn[y];
        }
    }
    
    private static void displayPanel() {
        int total = 0;
        for(int y = 0; y < ROWS; y++) {
            for(int x = 0; x < COLUMNS; x++) {
                System.out.print(PIXELS[y][x]);
                if(PIXELS[y][x] == '#') {
                    total++;
                }
            }
            System.out.print("\n");
        }
        System.out.println("Total Pixels Lit: " + total);
    }
}
