/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day15;

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
public class Day15 {
    
    public static void main(String[] args) {
        String filePath = "day15.txt";
        ArrayList<String> input;
        ArrayList<int[]> discs;
        
        input = readFile(filePath);
        discs = readInput(input);
        
        // Part 1
        System.out.println("Part One: " + partOne(discs));
        
        // Part2
        int[] disc7 = {11, 0};
        discs.add(disc7);
        System.out.println("Part Two: " + partOne(discs));
    }

    private static String partOne(ArrayList<int[]> discs) {
        boolean found = false;
        int count = 0;
        while(!found) {
            if(!pathFound(discs, count)) {
                count++;
            } else {
                found = true;
            }
        }
        return Integer.toString(count);
    }
    
    private static ArrayList<String> readFile(String filePath) {
        String read;
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((read = in.readLine()) != null) {
                input.add(read.trim());
            }
        } catch (IOException ex) {
            Logger.getLogger(Day15.class.getName()).log(Level.SEVERE, null, ex);
        }
        return input;
    }

    private static ArrayList readInput(ArrayList<String> input) {
        ArrayList<int[]> discs = new ArrayList<>();
        for(String entry : input) {
            String[] decompose = entry.split(" ");
            int[] disc = {Integer.parseInt(decompose[3]), Integer.parseInt(decompose[11])};
            discs.add(disc);
        }
        return discs;
    }
    
    private static boolean pathFound(ArrayList<int[]> discs, int count) {
        int increment = 1;
        for(int[] disc : discs) {
            int positions = disc[0];
            int start = disc[1];
            if((start + count + increment) % positions != 0) {
                return false;
            }
            increment++;
        }
        return true;
    }
    
}
