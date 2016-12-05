//--- Day 3: Squares With Three Sides ---
//
//Now that you can think clearly, you move deeper into the labyrinth of hallways and office furniture that makes up this part of Easter Bunny HQ. This must be a graphic design department; the walls are covered in specifications for triangles.
//
//Or are they?
//
//The design document gives the side lengths of each triangle it describes, but... 5 10 25? Some of these aren't triangles. You can't help but mark the impossible ones.
//
//In a valid triangle, the sum of any two sides must be larger than the remaining side. For example, the "triangle" given above is impossible, because 5 + 10 is not larger than 25.
//
//In your puzzle input, how many of the listed triangles are possible?
//
//Your puzzle answer was 1050.
//
//--- Part Two ---
//
//Now that you've helpfully marked up their design documents, it occurs to you that triangles are specified in groups of three vertically. Each set of three numbers in a column specifies a triangle. Rows are unrelated.
//
//For example, given the following specification, numbers with the same hundreds digit would be part of the same triangle:
//
//101 301 501
//102 302 502
//103 303 503
//201 401 601
//202 402 602
//203 403 603
//In your puzzle input, and instead reading by columns, how many of the listed triangles are possible?
//
//Your puzzle answer was 1921.

package Day3;

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
public class Day3 {
    private static final String FILEPATH = "day3.txt";
    private static final ArrayList<Integer[]> TRIANGLES = new ArrayList<>();
    
    public static void main(String[] args) {
        readFile();
        
        // Part 1
        int answer1 = 0;
        for(Integer[] dimension : TRIANGLES) {
            if (dimension[0] + dimension[1] > dimension[2]
             && dimension[1] + dimension[2] > dimension[0]
             && dimension[0] + dimension[2] > dimension[1]) {
                answer1++;
            }
        }
        System.out.println("Part 1: " + answer1);
        
        // Part 2
        int answer2 = 0;
        for(int i = 0; i < TRIANGLES.size()-2; i = i + 3) {
            Integer[] row1 = TRIANGLES.get(i);
            Integer[] row2 = TRIANGLES.get(i+1);
            Integer[] row3 = TRIANGLES.get(i+2);
            for(int j = 0; j < 3; j++) {
                if(row1[j] + row2[j] > row3[j] && row2[j] + row3[j] > row1[j] && row1[j] + row3[j] > row2[j]) { answer2++; }
            }
        }
        System.out.println("Part 2: " + answer2);
    }
    
    private static void readFile() {
        String read;
        String[] readString;
        try (BufferedReader in = new BufferedReader(new FileReader(FILEPATH))) {
            while((read = in.readLine()) != null) {
                readString = read.trim().split("\\s+");
                Integer[]nums = new Integer[readString.length];
                for(int i = 0; i < readString.length; i++) {
                    nums[i] = Integer.parseInt(readString[i]);
                }
                TRIANGLES.add(nums);
            }
        } catch (IOException ex) {
            Logger.getLogger(Day3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
