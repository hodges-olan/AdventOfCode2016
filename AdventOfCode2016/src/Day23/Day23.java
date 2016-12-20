/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day23;

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
public class Day23 {
    
    public static void main(String[] args) {
        
        String filePath = "day23.txt";
        ArrayList<String> input = readFile(filePath);
        
        // Part 1
        System.out.println("Part One: " + partOne(input));
        
        // Part2
        System.out.println("Part Two: " + partTwo(input));
        
    }
 
    private static ArrayList<String> readFile(String filePath) {
        String read;
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((read = in.readLine()) != null) input.add(read.trim());
        } catch (IOException ex) {
            Logger.getLogger(Day23.class.getName()).log(Level.SEVERE, null, ex);
        }
        return input;
    }

    private static String partOne(ArrayList<String> input) {
        return "Not yet";
    }

    private static String partTwo(ArrayList<String> input) {
        return "Not yet";
    }
    
}
