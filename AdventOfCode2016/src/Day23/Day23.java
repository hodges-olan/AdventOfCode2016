/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day23;

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
public class Day23 {
    
    public static void main(String[] args) {
        String filePath = "day23.txt";
        ArrayList<String[]> inputA = FileIO.readFileToArrayListStringArray(filePath, " ");
        ArrayList<String[]> inputB = FileIO.readFileToArrayListStringArray(filePath, " ");
        Map registersA = initializeRegisters(7, 0, 0, 0);
        Map registersB = initializeRegisters(12, 0, 0, 0);
        
        // Part 1
        System.out.println("Part One: " + partOne(registersA, inputA));
        
        // Part2
        System.out.println("Part Two: " + partOne(registersB, inputB));
    }

    private static String partOne(Map registers, ArrayList<String[]> input) {
        for(int i = 0; i < input.size(); i++) {
            String[] splitInstructions = input.get(i);
            Pattern patternDigits = Pattern.compile("\\d+");
            Pattern patternRegisters = Pattern.compile("[a-d]");
            Matcher m1;
            Matcher m2;
            switch(splitInstructions[0]) {
                case "cpy":
                    m1 = patternDigits.matcher(splitInstructions[1]);
                    m2 = patternRegisters.matcher(splitInstructions[2]);
                    if(m1.find() && m2.find()) {
                        registers.put(splitInstructions[2], Integer.parseInt(splitInstructions[1]));
                    }
                    m1 = patternRegisters.matcher(splitInstructions[1]);
                    m2 = patternRegisters.matcher(splitInstructions[2]);
                    if(m1.find() && m2.find()) {
                        registers.put(splitInstructions[2], Integer.parseInt(registers.get(splitInstructions[1]).toString()));
                    }
                    break;
                case "inc":
                    m1 = patternRegisters.matcher(splitInstructions[1]);
                    if(m1.find()) {
                        registers.put(splitInstructions[1],(Integer.parseInt(registers.get(splitInstructions[1]).toString()) + 1));
                    }
                    break;
                case "dec":
                    m1 = patternRegisters.matcher(splitInstructions[1]);
                    if(m1.find()) {
                        registers.put(splitInstructions[1],(Integer.parseInt(registers.get(splitInstructions[1]).toString()) - 1));
                    }
                    break;
                case "jnz":
                    m1 = patternDigits.matcher(splitInstructions[1]);
                    m2 = patternDigits.matcher(splitInstructions[2]);
                    if(m1.find() && m2.find()) {
                        if(Integer.parseInt(splitInstructions[1]) != 0) {
                            i = ((i + Integer.parseInt(splitInstructions[2]) - 1) <= 0) ? -1 : i + Integer.parseInt(splitInstructions[2]) - 1;
                        }
                    }
                    m1 = patternRegisters.matcher(splitInstructions[1]);
                    m2 = patternDigits.matcher(splitInstructions[2]);
                    if(m1.find() && m2.find()) {
                        if(Integer.parseInt(registers.get(splitInstructions[1]).toString()) != 0) {
                            i = ((i + Integer.parseInt(splitInstructions[2]) - 1) <= 0) ? -1 : i + Integer.parseInt(splitInstructions[2]) - 1;
                        }
                    }
                    m1 = patternDigits.matcher(splitInstructions[1]);
                    m2 = patternRegisters.matcher(splitInstructions[2]);
                    if(m1.find() && m2.find()) {
                        if(Integer.parseInt(splitInstructions[1]) != 0) {
                            i = ((i + Integer.parseInt(registers.get(splitInstructions[2]).toString()) - 1) <= 0) ? -1 : i + Integer.parseInt(registers.get(splitInstructions[2]).toString()) - 1;
                        }
                    }
                    m1 = patternRegisters.matcher(splitInstructions[1]);
                    m2 = patternRegisters.matcher(splitInstructions[2]);
                    if(m1.find() && m2.find()) {
                        if(Integer.parseInt(registers.get(splitInstructions[1]).toString()) != 0) {
                            i = ((i + Integer.parseInt(registers.get(splitInstructions[2]).toString()) - 1) <= 0) ? -1 : i + Integer.parseInt(registers.get(splitInstructions[2]).toString()) - 1;
                        }
                    }
                    break;
                case "tgl":
                    int target = Integer.parseInt(registers.get(splitInstructions[1]).toString()) + i;
                    if(!(target >= input.size())) {
                        String[] tglInstruction = input.get(target);
                        switch(tglInstruction.length - 1) {
                            case 1:
                                if(tglInstruction[0].equals("inc")) tglInstruction[0] = "dec"; else tglInstruction[0] = "inc";
                                break;
                            case 2:
                                if(tglInstruction[0].equals("jnz")) tglInstruction[0] = "cpy"; else tglInstruction[0] = "jnz";
                                break;
                        }
                        input.remove(target);
                        input.add(target, tglInstruction);
                    }
                    break;
            }
        }
        return registers.get("a").toString();
    }

    private static Map initializeRegisters(int a, int b, int c, int d) {
        Map registers = new HashMap();
        registers.put("a", a);
        registers.put("b", b);
        registers.put("c", c);
        registers.put("d", d);
        return registers;
    }
    
}