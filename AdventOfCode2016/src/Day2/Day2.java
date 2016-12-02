
package Day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 71100096
 */
public class Day2 {
    private static final String FILEPATH = "day2.txt";
    private static final ArrayList<String> INSTRUCTIONS = new ArrayList<>();
    private static final int[][] KEYPAD = new int[3][3];
    private static final char[][] REALKEYPAD = new char[5][5];
    private static final ArrayList<Integer> CODE = new ArrayList<>();
    private static final ArrayList<Character> REALCODE = new ArrayList<>();
    private static int x;
    private static int y;
    
    public static void main(String[] args) {
        readFile();
        
        // Part 1
        initializeKeypad();
        x = 1;
        y = 1;
        INSTRUCTIONS.stream().forEach((instruction) -> { CODE.add(moveKey(instruction)); });
        System.out.println("Part 1");
        CODE.stream().forEach((key) -> { System.out.println(key); });
        
        // Part 2
        initializeRealKeypad();
        x = 0;
        y = 2;
        INSTRUCTIONS.stream().forEach((instruction) -> { REALCODE.add(moveRealKey(instruction)); });
        System.out.println("Part 2");
        REALCODE.stream().forEach((key) -> {System.out.println(key); });
    }
    
    private static void readFile() {
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(FILEPATH))) { while((read = in.readLine()) != null) INSTRUCTIONS.add(read.trim()); } catch (IOException ex) { Logger.getLogger(Day2.class.getName()).log(Level.SEVERE, null, ex); }
    }
    
    private static void initializeKeypad() {
        for(int localY=0; localY < 3; localY++) {
            for(int localX=0; localX < 3; localX++) {
                KEYPAD[localY][localX] = localX+(localY*3)+1;
            }
        }
    }
    
    private static void initializeRealKeypad() {
        REALKEYPAD[0][0] = 'X';
        REALKEYPAD[0][1] = 'X';
        REALKEYPAD[0][2] = '1';
        REALKEYPAD[0][3] = 'X';
        REALKEYPAD[0][4] = 'X';
        REALKEYPAD[1][0] = 'X';
        REALKEYPAD[1][1] = '2';
        REALKEYPAD[1][2] = '3';
        REALKEYPAD[1][3] = '4';
        REALKEYPAD[1][4] = 'X';
        REALKEYPAD[2][0] = '5';
        REALKEYPAD[2][1] = '6';
        REALKEYPAD[2][2] = '7';
        REALKEYPAD[2][3] = '8';
        REALKEYPAD[2][4] = '9';
        REALKEYPAD[3][0] = 'X';
        REALKEYPAD[3][1] = 'A';
        REALKEYPAD[3][2] = 'B';
        REALKEYPAD[3][3] = 'C';
        REALKEYPAD[3][4] = 'X';
        REALKEYPAD[4][0] = 'X';
        REALKEYPAD[4][1] = 'X';
        REALKEYPAD[4][2] = 'D';
        REALKEYPAD[4][3] = 'X';
        REALKEYPAD[4][4] = 'X';
    }
    
    private static Integer moveKey(String instruction) {
        for(int i = 0; i < instruction.length(); i++) {
            switch(instruction.charAt(i)) {
                case 'U':
                    if(y != 0) { y--; }
                    break;
                case 'R':
                    if(x != 2) { x++; }
                    break;
                case 'D':
                    if(y != 2) { y++; }
                    break;
                case 'L':
                    if(x != 0) { x--; }
                    break;
            }
        }
        return KEYPAD[y][x];
    }

    private static Character moveRealKey(String instruction) {
        for(int i = 0; i < instruction.length(); i++) {
            switch(instruction.charAt(i)) {
                case 'U':
                    if(y != 0 && REALKEYPAD[y - 1][x] != 'X') { y--; }
                    break;
                case 'R':
                    if(x != 4 && REALKEYPAD[y][x + 1] != 'X') { x++; }
                    break;
                case 'D':
                    if(y != 4 && REALKEYPAD[y + 1][x] != 'X') { y++; }
                    break;
                case 'L':
                    if(x != 0 && REALKEYPAD[y][x - 1] != 'X') { x--; }
                    break;
            }
        }
        return REALKEYPAD[y][x];
    }
}
