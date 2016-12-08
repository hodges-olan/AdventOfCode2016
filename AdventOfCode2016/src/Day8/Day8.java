/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private static final String FILEPATH = "day8.txt";
    private static final ArrayList<String[]> INPUT = new ArrayList<>();
    private static final int ROWS = 6;
    private static final int COLUMNS = 50;
    private static final char[][] PIXELS = new char[ROWS][COLUMNS];
    
    public static void main(String[] args) {
        readFile();
        
        // Part 1
        System.out.println("Part One: ");
        partOne();
        
        // Part2
        System.out.println("Part Two: ");
        partTwo();
    }
    
    private static void readFile() {
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(FILEPATH))) {
            while((read = in.readLine()) != null) {
                INPUT.add(read.trim().split(" "));
            }
        } catch (IOException ex) {
            Logger.getLogger(Day8.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void partOne() {
        for(String[] directions : INPUT) {
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
