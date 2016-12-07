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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hodges-olan
 */
public class Day7 {
    private static final String FILEPATH = "day7.txt";
    private static final ArrayList<String> INPUT = new ArrayList<>();
    
    public static void main(String[] args) {
        readFile();
        
        // Part 1
        System.out.println("Part One: " + partOne());
        
        // Part2
        System.out.println("Part Two: " + partTwo());
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
    
    private static int partOne() {
        int total = 0;
        for(String eachInput : INPUT) {
            String address = compileString("^\\w+|\\w+$|(?<=\\])\\w+", eachInput);
            if(findABBA(address)) {
                String hypernetAddress = compileString("(?<=\\[)\\w+", eachInput);
                if(!findABBA(hypernetAddress)) {
                    total++;
                }
            }
        }
        return total;
    }
    
    private static int partTwo() {
        int total = 0;
        for(String eachInput : INPUT) {
            String address = compileString("^\\w+|\\w+$|(?<=\\])\\w+", eachInput);
            ArrayList<String> matches = findABA(address);
            if(!matches.isEmpty()) {
                String hypernetAddress = compileString("(?<=\\[)\\w+", eachInput);
                if(findBAB(matches, hypernetAddress)) {
                    total++;
                }
            }
        }
        return total;
    }
    
    private static boolean findABBA(String string) {
        for(int i = 0; i < (string.length()-4); i++) {
            if(string.substring(i,i+2).equals(new StringBuilder(string.substring(i+2, i+4)).reverse().toString())
                    && !string.substring(i,i+1).equals(string.substring(i+1,i+2))) {
                return true;
            }
        }
        return false;
    }
    
    private static ArrayList<String> findABA(String string) {
        ArrayList<String> matches = new ArrayList<>();
        for(int i = 0; i < (string.length()-3); i++) {
            if(string.substring(i,i+1).equals(string.substring(i+2,i+3))
                    && !string.substring(i,i+1).equals(string.substring(i+1,i+2))
                    && !string.substring(i,i+1).equals(string.substring(i+1,i+2))) {
                StringBuilder sb = new StringBuilder();
                sb.append(string.substring(i+1,i+2));
                sb.append(string.substring(i,i+1));
                sb.append(string.substring(i+1,i+2));
                matches.add(sb.toString());
            }
        }
        return matches;
    }
    
    private static boolean findBAB(ArrayList<String> matches, String string) {
        for(String match : matches) {
            if(string.contains(match)) {
                return true;
            }
        }
        return false;
    }
    
    private static String compileString(String pattern, String eachInput) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(eachInput);
        StringBuilder sb = new StringBuilder();
        while(m.find()) {
            sb.append(m.group());
            sb.append(' ');
        }
        return sb.toString();
    }
}
