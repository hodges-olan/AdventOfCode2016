/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hodges-olan
 */
public class Day12 {
    
    public static void main(String[] args) {
        String filePath = "day12.txt";
        ArrayList<String> input;
        Map registers = new HashMap();
        
        input = readFile(filePath);
        registers = initializeRegisters(registers);
        
        // Part 1
        System.out.println("Part One: " + partOne(registers, input));
        
        // Part2
        registers = partTwo(registers);
        System.out.println("Part Two: " + partOne(registers, input));
    }
    
    private static ArrayList<String> readFile(String filePath) {
        String read;
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((read = in.readLine()) != null) {
                input.add(read.trim());
            }
        } catch (IOException ex) {
            Logger.getLogger(Day12.class.getName()).log(Level.SEVERE, null, ex);
        }
        return input;
    }

    private static String partOne(Map registers, ArrayList<String> input) {
        for(int i = 0; i < input.size(); i++) {
            String[] splitInstructions = input.get(i).split(" ");
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(splitInstructions[1]);
            switch (splitInstructions[0]) {
                case "cpy":
                    if(m.find()) {
                        registers.put(splitInstructions[2], Integer.parseInt(splitInstructions[1]));
                    } else {
                        registers.put(splitInstructions[2], Integer.parseInt(registers.get(splitInstructions[1]).toString()));
                    }
                    break;
                case "inc":
                    registers.put(splitInstructions[1],(Integer.parseInt(registers.get(splitInstructions[1]).toString()) + 1));
                    break;
                case "dec":
                    registers.put(splitInstructions[1],(Integer.parseInt(registers.get(splitInstructions[1]).toString()) - 1));
                    break;
                case "jnz":
                    if(m.find()) {
                        if(Integer.parseInt(splitInstructions[1]) != 0) {
                            i = ((i + Integer.parseInt(splitInstructions[1]) - 1) < 0) ? -1 : i + Integer.parseInt(splitInstructions[2]) - 1;
                        }
                    } else {
                        if(Integer.parseInt(registers.get(splitInstructions[1]).toString()) != 0) {
                            i = ((i + Integer.parseInt(registers.get(splitInstructions[1]).toString()) - 1) < 0) ? -1 : i + Integer.parseInt(splitInstructions[2]) - 1;
                        }
                    }
                    break;
            }
        }
        return registers.get("a").toString();
    }

    private static Map partTwo(Map registers) {
        registers.put("a", 0);
        registers.put("b", 0);
        registers.put("c", 1);
        registers.put("d", 0);
        return registers;
    }

    private static Map initializeRegisters(Map registers) {
        registers.put("a", 0);
        registers.put("b", 0);
        registers.put("c", 0);
        registers.put("d", 0);
        return registers;
    }

}
