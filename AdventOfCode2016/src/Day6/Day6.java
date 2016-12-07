/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day6;

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
public class Day6 {
    
    private static final String FILEPATH = "day6.txt";
    private static final ArrayList<char[]> INPUT = new ArrayList<>();    
    
    public static void main(String[] args) {
        readFile();
        // Part 1
        System.out.println(findMessagePartOne());
        
        // Part2
        System.out.println(findMessagePartTwo());
    }
    
    private static void readFile() {
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(FILEPATH))) {
            while((read = in.readLine()) != null) {
                INPUT.add(read.trim().toCharArray());
            }
        } catch (IOException ex) {
            Logger.getLogger(Day6.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String findMessagePartOne() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 8; i++) {
            int[] alphabet = new int[26];
            int common = 0;
            int max = 0;
            for(char[] charInput : INPUT) {
                alphabet[(((int) charInput[i])-97)]++;
            }
            for(int j = 0; j < 26; j++) {
                if(alphabet[j] > max) {
                    common = j;
                    max = alphabet[j];
                }
            }
            sb.append((char) (common+97));
        }
        return sb.toString();
    }

    private static String findMessagePartTwo() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 8; i++) {
            int[] alphabet = new int[26];
            int common = 0;
            int min = 9000;
            for(char[] charInput : INPUT) {
                alphabet[(((int) charInput[i])-97)]++;
            }
            for(int j = 0; j < 26; j++) {
                if(alphabet[j] < min && alphabet[j] != 0) {
                    common = j;
                    min = alphabet[j];
                }
            }
            sb.append((char) (common+97));
        }
        return sb.toString();
    }
}
