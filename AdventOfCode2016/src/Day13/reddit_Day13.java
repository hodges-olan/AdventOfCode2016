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

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;

import java.util.AbstractMap;

public class reddit_Day13 {

    private static class Location extends AbstractMap.SimpleImmutableEntry<Long, Long> {
        private static final long SEED = 1364;
        private int bits = -1;

        Location(long x, long y) {
            super(x, y);
        }

        boolean isOpen() {
            if (bits == -1) {
                bits = Long.bitCount(SEED + getKey() * getKey() + 3 * getKey() + 2 * getKey() * getValue() + getValue() + getValue() * getValue());
            }
            return bits % 2 == 0;
        }
    }

    private static int getId(Location loc) {
        return (int) (loc.getKey() * 50 + loc.getValue());
    }

    public static void main(String[] args) {
        Graph G = new Graph(50*50);
        for (long i = 0; i < 50; i++) {
            for (long j = 0; j < 50; j++) {
                Location current = new Location(i, j);
                if (current.isOpen()) {
                    for (Location loc : new Location[]{new Location(i, j + 1), new Location(i + 1, j)}) {
                        if (loc.getKey() < 50 && loc.getValue() < 50 && loc.isOpen())
                            G.addEdge(getId(current), getId(loc));
                    }
                }
            }
        }

        BreadthFirstPaths bfs = new BreadthFirstPaths(G, getId(new Location(1, 1)));
        System.out.println(bfs.distTo(getId(new Location(31, 39))));

        int count = 0;
        for (int i = 0; i < G.V(); i++) {
            if (bfs.hasPathTo(i) && bfs.distTo(i) <= 50) ++count;
        }
        System.out.println(count);
    }
}