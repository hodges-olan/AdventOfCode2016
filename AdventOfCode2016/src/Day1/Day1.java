//--- Day 1: No Time for a Taxicab ---
//
//Santa's sleigh uses a very high-precision clock to guide its movements, and the clock's oscillator is regulated by stars. Unfortunately, the stars have been stolen... by the Easter Bunny. To save Christmas, Santa needs you to retrieve all fifty stars by December 25th.
//
//Collect stars by solving puzzles. Two puzzles will be made available on each day in the advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!
//
//You're airdropped near Easter Bunny Headquarters in a city somewhere. "Near", unfortunately, is as close as you can get - the instructions on the Easter Bunny Recruiting Document the Elves intercepted start here, and nobody had time to work them out further.
//
//The Document indicates that you should start at the given coordinates (where you just landed) and face North. Then, follow the provided sequence: either turn left (L) or right (R) 90 degrees, then walk forward the given number of blocks, ending at a new intersection.
//
//There's no time to follow such ridiculous instructions on foot, though, so you take a moment and work out the destination. Given that you can only walk on the street grid of the city, how far is the shortest path to the destination?
//
//For example:
//
//Following R2, L3 leaves you 2 blocks East and 3 blocks North, or 5 blocks away.
//R2, R2, R2 leaves you 2 blocks due South of your starting position, which is 2 blocks away.
//R5, L5, R5, R3 leaves you 12 blocks away.
//How many blocks away is Easter Bunny HQ?
//
//Your puzzle answer was 273.
//
//--- Part Two ---
//
//Then, you notice the instructions continue on the back of the Recruiting Document. Easter Bunny HQ is actually at the first location you visit twice.
//
//For example, if your instructions are R8, R4, R4, R8, the first location you visit twice is 4 blocks away, due East.
//
//How many blocks away is the first location you visit twice?
//
//Your puzzle answer was 115.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day1;


import FileIO.FileIO;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hodges-olan
 */
public class Day1 {
    
    public static void main(String[] args) {
        String filePath = "day1.txt";
        ArrayList<String> directions = FileIO.readFileToArrayListStringSplit(filePath, ", ");
        
        // Part 1
        System.out.println("Part 1 Answer: " + partOne(directions, false));
        
        // Part 2
        System.out.println("Part 2 Answer: " + partOne(directions, true));
    }

    private static String partOne(ArrayList<String> directions, boolean partTwo) {
        int[][] blocks = new int[10000][10000];
        int direction = 0;
        int x = 0;
        int y = 0;
        Pattern p = Pattern.compile("\\d+");
        
        for (String splitDirection: directions) {
            char turn = splitDirection.charAt(0);
            Matcher m = p.matcher(splitDirection);
            int distance = 0;
            while(m.find()) distance = Integer.parseInt(m.group());
            switch(turn) {
                case 'R': if(direction == 3) direction = 0; else direction++; break;
                case 'L': if(direction == 0) direction = 3; else direction--; break;
            }
            for (int i = 0; i < distance; i++) {
                switch(direction) {
                    case 0: y++; break;
                    case 1: x++; break;
                    case 2: y--; break;
                    case 3: x--; break;
                }
                blocks[x+5000][y+5000]++;
                if (partTwo && blocks[x+5000][y+5000] > 1) return getResult(x, y);
            }
        }
        return getResult(x, y);
    }
    
    private static String getResult(int x, int y) {
        return Integer.toString(Math.abs(x) + Math.abs(y));
    }
}