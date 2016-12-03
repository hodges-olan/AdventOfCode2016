//--- Day 2: Bathroom Security ---
//
//You arrive at Easter Bunny Headquarters under cover of darkness. However, you left in such a rush that you forgot to use the bathroom! Fancy office buildings like this one usually have keypad locks on their bathrooms, so you search the front desk for the code.
//
//"In order to improve security," the document you find says, "bathroom codes will no longer be written down. Instead, please memorize and follow the procedure below to access the bathrooms."
//
//The document goes on to explain that each button to be pressed can be found by starting on the previous button and moving to adjacent buttons on the keypad: U moves up, D moves down, L moves left, and R moves right. Each line of instructions corresponds to one button, starting at the previous button (or, for the first line, the "5" button); press whatever button you're on at the end of each line. If a move doesn't lead to a button, ignore it.
//
//You can't hold it much longer, so you decide to figure out the code as you walk to the bathroom. You picture a keypad like this:
//
//1 2 3
//4 5 6
//7 8 9
//Suppose your instructions are:
//
//ULL
//RRDDD
//LURDL
//UUUUD
//You start at "5" and move up (to "2"), left (to "1"), and left (you can't, and stay on "1"), so the first button is 1.
//Starting from the previous button ("1"), you move right twice (to "3") and then down three times (stopping at "9" after two moves and ignoring the third), ending up with 9.
//Continuing from "9", you move left, up, right, down, and left, ending with 8.
//Finally, you move up four times (stopping at "2"), then down once, ending with 5.
//So, in this example, the bathroom code is 1985.
//
//Your puzzle input is the instructions from the document you found at the front desk. What is the bathroom code?
//
//Your puzzle answer was 74921.
//
//--- Part Two ---
//
//You finally arrive at the bathroom (it's a several minute walk from the lobby so visitors can behold the many fancy conference rooms and water coolers on this floor) and go to punch in the code. Much to your bladder's dismay, the keypad is not at all like you imagined it. Instead, you are confronted with the result of hundreds of man-hours of bathroom-keypad-design meetings:
//
//    1
//  2 3 4
//5 6 7 8 9
//  A B C
//    D
//You still start at "5" and stop when you're at an edge, but given the same instructions as above, the outcome is very different:
//
//You start at "5" and don't move at all (up and left are both edges), ending at 5.
//Continuing from "5", you move right twice and down three times (through "6", "7", "B", "D", "D"), ending at D.
//Then, from "D", you move five more times (through "D", "B", "C", "C", "B"), ending at B.
//Finally, after five more moves, you end at 3.
//So, given the actual keypad layout, the code would be 5DB3.
//
//Using the same instructions in your puzzle input, what is the correct bathroom code?
//
//Your puzzle answer was A6B35.

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
