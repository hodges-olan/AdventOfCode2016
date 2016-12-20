/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day24;

import FileIO.FileIO;
import java.util.ArrayList;

/**
 *
 * @author hodges-olan
 */
public class Day24 {
    
    public static void main(String[] args) {
        
        String filePath = "day24.txt";
        ArrayList<String> input = FileIO.readFileToArrayListString(filePath);
        
        // Part 1
        System.out.println("Part One: " + partOne(input));
        
        // Part2
        System.out.println("Part Two: " + partTwo(input));
        
    }

    private static String partOne(ArrayList<String> input) {
        return "Not yet";
    }

    private static String partTwo(ArrayList<String> input) {
        return "Not yet";
    }
    
}
