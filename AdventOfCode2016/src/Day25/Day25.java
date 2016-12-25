/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day25;

import FileIO.FileIO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hodges-olan
 */
public class Day25 {
    
    public static void main(String[] args) {
        String filePath = "day25.txt";
        ArrayList<String> input = FileIO.readFileToArrayListString(filePath);
        Map registers;
        
        // Part 1
        System.out.print("Part One: ");
        boolean valid = false;
        int i = 0;
        while(!valid) {
            registers = initializeRegisters(i);
            if(partOne(registers, input).equals("01010101")) valid = true;
            i++;
        }
        System.out.print(Integer.toString(i - 1) + "\n");
    }

    private static String partOne(Map registers, ArrayList<String> input) {
        int j = 0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < input.size() && j < 8; i++) {
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
                            i = ((i + Integer.parseInt(splitInstructions[2]) - 1) <= 0) ? -1 : i + Integer.parseInt(splitInstructions[2]) - 1;
                        }
                    } else {
                        if(Integer.parseInt(registers.get(splitInstructions[1]).toString()) != 0) {
                            i = ((i + Integer.parseInt(splitInstructions[2]) - 1) <= 0) ? -1 : i + Integer.parseInt(splitInstructions[2]) - 1;
                        }
                    }
                    break;
                case "out":
                    sb.append(registers.get(splitInstructions[1]).toString());
                    j++;
                    break;
            }
        }
        return sb.toString();
    }

    private static Map initializeRegisters(int i) {
        Map registers = new HashMap();
        registers.put("a", i);
        registers.put("b", 0);
        registers.put("c", 0);
        registers.put("d", 0);
        return registers;
    }
    
}
