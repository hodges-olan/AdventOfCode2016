/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
