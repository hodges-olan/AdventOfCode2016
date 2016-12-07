/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day7;

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
public class Day7 {
    private static final String FILEPATH = "day7.txt";
    private static final ArrayList<String> INPUT = new ArrayList<>();
    
    
    public static void main(String[] args) {
        readFile();
        
        // Part 1
        System.out.println(partOne());
        
        // Part2
        System.out.println(partTwo());
    }
    
    private static void readFile() {
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(FILEPATH))) {
            while((read = in.readLine()) != null) {
                INPUT.add(read.trim());
            }
        } catch (IOException ex) {
            Logger.getLogger(Day7.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static String partOne() {
        for(String eachInput : INPUT) {
            findABBA(eachInput);
        }
        return "NOT YET";
    }
    
    private static String partTwo() {
        return "NOT YET";
    }
    
    private static void findABBA(String string) {
        for(int i = 0; i < (string.length()-4); i++) {
            if(string.substring(i,i+2).equals(new StringBuilder(string.substring(i+2, i+4)).reverse().toString())
                    && (!string.substring(i,i+4).contains("[") || !string.substring(i,i+4).contains("]"))) {
                System.out.println("ABBA Found: " + string.substring(i,i+4));
            }
        }
    }
}
