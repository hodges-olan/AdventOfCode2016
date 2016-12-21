//--- Day 13: A Maze of Twisty Little Cubicles ---
//
//You arrive at the first floor of this new building to discover a much less welcoming environment than the shiny atrium of the last one. Instead, you are in a maze of twisty little cubicles, all alike.
//
//Every location in this area is addressed by a pair of non-negative integers (x,y). Each such coordinate is either a wall or an open space. You can't move diagonally. The cube maze starts at 0,0 and seems to extend infinitely toward positive x and y; negative values are invalid, as they represent a location outside the building. You are in a small waiting area at 1,1.
//
//While it seems chaotic, a nearby morale-boosting poster explains, the layout is actually quite logical. You can determine whether a given x,y coordinate will be a wall or an open space using a simple system:
//
//Find x*x + 3*x + 2*x*y + y + y*y.
//Add the office designer's favorite number (your puzzle input).
//Find the binary representation of that sum; count the number of bits that are 1.
//If the number of bits that are 1 is even, it's an open space.
//If the number of bits that are 1 is odd, it's a wall.
//For example, if the office designer's favorite number were 10, drawing walls as # and open spaces as ., the corner of the building containing 0,0 would look like this:
//
//  0123456789
//0 .#.####.##
//1 ..#..#...#
//2 #....##...
//3 ###.#.###.
//4 .##..#..#.
//5 ..##....#.
//6 #...##.###
//Now, suppose you wanted to reach 7,4. The shortest route you could take is marked as O:
//
//  0123456789
//0 .#.####.##
//1 .O#..#...#
//2 #OOO.##...
//3 ###O#.###.
//4 .##OO#OO#.
//5 ..##OOO.#.
//6 #...##.###
//Thus, reaching 7,4 would take a minimum of 11 steps (starting from your current location, 1,1).
//
//What is the fewest number of steps required for you to reach 31,39?
//
//Your puzzle answer was 86.
//
//--- Part Two ---
//
//How many locations (distinct x,y coordinates, including your starting location) can you reach in at most 50 steps?
//
//Your puzzle answer was 127.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//Your puzzle input was 1364.
//
//You can also [Share] this puzzle.

//  Notes to review later
//  http://imgur.com/a/N7ZPO
//  http://algs4.cs.princeton.edu/code/

package Day13;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author hodges-olan
 */
public class Day13 {
    
    public static void main(String[] args) {
        int input = 10;
        int startX = 1;
        int startY = 1;
        
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (wall(x, y, input)) {
                    System.out.print('#');
                } else {
                    System.out.print('.');
                }
            }
            System.out.print('\n');
        }
        
    }

    private static boolean wall(int x, int y, int input) {
        int total = (x * x) + (3 * x) + (2 * x * y) + y + (y * y) + input;
        String binary = Integer.toBinaryString(total);
        int totalOnes = StringUtils.countMatches(binary, '1');
        boolean valid = (totalOnes%2 == 1);
        return valid;
    }
    
}
