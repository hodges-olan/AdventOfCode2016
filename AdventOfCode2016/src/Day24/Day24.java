//--- Day 24: Air Duct Spelunking ---
//
//You've finally met your match; the doors that provide access to the roof are locked tight, and all of the controls and related electronics are inaccessible. You simply can't reach them.
//
//The robot that cleans the air ducts, however, can.
//
//It's not a very fast little robot, but you reconfigure it to be able to interface with some of the exposed wires that have been routed through the HVAC system. If you can direct it to each of those locations, you should be able to bypass the security controls.
//
//You extract the duct layout for this area from some blueprints you acquired and create a map with the relevant locations marked (your puzzle input). 0 is your current location, from which the cleaning robot embarks; the other numbers are (in no particular order) the locations the robot needs to visit at least once each. Walls are marked as #, and open passages are marked as .. Numbers behave like open passages.
//
//For example, suppose you have a map like the following:
//
//###########
//#0.1.....2#
//#.#######.#
//#4.......3#
//###########
//To reach all of the points of interest as quickly as possible, you would have the robot take the following path:
//
//0 to 4 (2 steps)
//4 to 1 (4 steps; it can't move diagonally)
//1 to 2 (6 steps)
//2 to 3 (2 steps)
//Since the robot isn't very fast, you need to find it the shortest route. This path is the fewest steps (in the above example, a total of 14) required to start at 0 and then visit every other location at least once.
//
//Given your actual map, and starting from location 0, what is the fewest number of steps required to visit every non-0 number marked on the map at least once?
//
//Your puzzle answer was 474.
//
//--- Part Two ---
//
//Of course, if you leave the cleaning robot somewhere weird, someone is bound to notice.
//
//What is the fewest number of steps required to start at 0, visit every non-0 number marked on the map at least once, and then return to 0?
//
//Your puzzle answer was 696.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day24;

import FileIO.FileIO;
import com.google.common.collect.Collections2;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author hodges-olan
 */
public class Day24 {
    
    public static void main(String[] args) {
        
        String filePath = "day24.txt";
        ArrayList<char[]> input = FileIO.readFileToArrayListCharArray(filePath);
        int width = input.size();
        int height = (input.get(0)).length;
        Graph map = createGraph(input, width, height);
        Location start = findStart(input, width, height);
        List<Location> controls = findControls(input, width, height);
        
        // Part 1
        System.out.println("Part One: " + partOne(map, start, controls, height, false));
        
        // Part2
        System.out.println("Part Two: " + partOne(map, start, controls, height, true));
        
    }

    private static String partOne(Graph map, Location start, List<Location> controls, int height, boolean partTwo) {
        int lowest = 999999999;
        Collection<List<Location>> controlsPermutations = Collections2.permutations(controls);
        for(List<Location> permutation : controlsPermutations) { 
            int total = 0;
            BreadthFirstPaths bfs = new BreadthFirstPaths(map, getId(start, height));
            for(Location location : permutation) {
                total += bfs.distTo(getId(location, height));
                bfs = new BreadthFirstPaths(map, getId(location, height));
            }
            if(partTwo) {
                total += bfs.distTo(getId(start, height));
            }
            if(total < lowest) {
                lowest = total;
            }
        }
        return Integer.toString(lowest);
    }

    private static Graph createGraph(ArrayList<char[]> input, int width, int height) {
        Graph G = new Graph(width*height);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                char locationLetter = (input.get(x))[y];
                Location current = new Location(x, y, locationLetter);
                if(current.isOpen()) {
                    for(Location loc : new Location[]{new Location(x + 1, y, (input.get(x + 1))[y]), new Location(x, y + 1, (input.get(x))[y + 1])}) {
                        if(loc.isOpen()) G.addEdge(getId(current, height), getId(loc, height));
                    }
                }
            }
        }
        return G;
    }
    
    private static int getId(Location loc, int height) {
        return (int) (loc.getKey() * height + loc.getValue());
    }

    private static Location findStart(ArrayList<char[]> input, int width, int height) {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if((input.get(x))[y] == '0') {
                    return new Location(x, y, (input.get(x))[y]);
                }
            }
        }
        return null;
    }
    
    private static ArrayList<Location> findControls(ArrayList<char[]> input, int width, int height) {
        ArrayList<Location> controls = new ArrayList<>();
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                char locationLetter = (input.get(x))[y];
                Location current = new Location(x, y, locationLetter);
                if(current.isControl()) {
                    controls.add(current);
                }
            }
        }
        return controls;
    }
    
    private static class Location extends AbstractMap.SimpleImmutableEntry<Integer, Integer> {
        char state;
        
        Location(int x, int y, char state) {
            super(x, y);
            this.state = state;
        }
        
        boolean isOpen() {
            return state != '#';
        }

        public char getState() {
            return state;
        }
        
        public boolean isControl() {
            return this.state == '1' || this.state == '2' || this.state == '3' || this.state == '4' || this.state == '5' || this.state == '6' || this.state == '7';
        }
        
        public boolean isStart() {
            return this.state == '0';
        }
        
    }
    
}
