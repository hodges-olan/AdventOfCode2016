/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day9;

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
public class Day9 {
    private static final String FILEPATH = "day9.txt";
    private static ArrayList<Character> INPUT = new ArrayList<>();
    private static final ArrayList<Character> FINALSTRING = new ArrayList<>();
    
    public static void main(String[] args) {
        readFile();
        
        // Part 1
        partOne();
        System.out.println("Part One: " + FINALSTRING.size());
        
        // Part2
        partTwo();
        System.out.println("Part Two: " + INPUT.size());
    }
    
    private static void readFile() {
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(FILEPATH))) {
            while((read = in.readLine()) != null) {
                for (char c : read.toCharArray()) {
                    INPUT.add(c);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Day9.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void partOne() {
        boolean sequenceFound = false;
        boolean postSequence = false;
        StringBuilder sequence = new StringBuilder();
        for(int i = 0; i < INPUT.size(); i++) {
            if(INPUT.get(i) == '(' && !postSequence) {
                sequence = new StringBuilder();
                sequenceFound = true;
            } else if(INPUT.get(i) == ')') {
                sequenceFound = false;
                postSequence = true;
            } else if(sequenceFound) {
                sequence.append(INPUT.get(i));
            } else if(postSequence) {
                String[] temp = (sequence.toString()).split("x");
                int repeat = Integer.parseInt(temp[1]);
                int size = Integer.parseInt(temp[0]);
                for(int a = 0; a < repeat; a++) {
                    for(int b = 0; b < size; b++) {
                        FINALSTRING.add(INPUT.get(i+b));
                    }
                }
                if(size > 1) {
                    i += size-1;
                }
                postSequence = false;
            } else if(INPUT.get(i) == ' ') {
                FINALSTRING.add(INPUT.get(i));
            }
        }
    }

    private static void partTwo() {
        boolean patternFound = true;
        while(patternFound) {
            INPUT = new ArrayList<>(FINALSTRING);
            FINALSTRING.clear();
            StringBuilder sb = new StringBuilder();
            for(char letter : INPUT) {
                sb.append(letter);
            }
            Pattern p = Pattern.compile("\\(\\w+\\)");
            Matcher m = p.matcher(sb.toString());
            if(m.find()) {
                partOne();
            } else {
                patternFound = false;
            }
        }
    }
}
