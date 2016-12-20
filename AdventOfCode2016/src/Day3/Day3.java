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
 * @author hodges-olan
 */
public class Day3 {
    
    public static void main(String[] args) {
        String filePath = "day3.txt";
        ArrayList<Integer[]> triangles = readFile(filePath);
        
        // Part 1
        System.out.println("Part 1: " + partOne(triangles));
        
        // Part 2
        System.out.println("Part 2: " + partTwo(triangles));
    }
    
    private static ArrayList<Integer[]> readFile(String filePath) {
        String read;
        String[] readString;
        ArrayList<Integer[]> triangles = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((read = in.readLine()) != null) {
                readString = read.trim().split("\\s+");
                Integer[]nums = new Integer[readString.length];
                for(int i = 0; i < readString.length; i++) {
                    nums[i] = Integer.parseInt(readString[i]);
                }
                triangles.add(nums);
            }
        } catch (IOException ex) {
            Logger.getLogger(Day3.class.getName()).log(Level.SEVERE, null, ex);
        }
        return triangles;
    }

    private static String partOne(ArrayList<Integer[]> triangles) {
        int answer1 = 0;
        for(Integer[] dimension : triangles) {
            if (dimension[0] + dimension[1] > dimension[2]
             && dimension[1] + dimension[2] > dimension[0]
             && dimension[0] + dimension[2] > dimension[1]) {
                answer1++;
            }
        }
        return Integer.toString(answer1);
    }

    private static String partTwo(ArrayList<Integer[]> triangles) {
        int answer2 = 0;
        for(int i = 0; i < triangles.size()-2; i = i + 3) {
            Integer[] row1 = triangles.get(i);
            Integer[] row2 = triangles.get(i+1);
            Integer[] row3 = triangles.get(i+2);
            for(int j = 0; j < 3; j++) {
                if(row1[j] + row2[j] > row3[j] && row2[j] + row3[j] > row1[j] && row1[j] + row3[j] > row2[j]) { answer2++; }
            }
        }
        return Integer.toString(answer2);
    }
    
}
