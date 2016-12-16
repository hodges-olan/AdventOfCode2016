/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day16;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author hodges-olan
 */
public class Day16 {
    
    public static void main(String[] args) {
        String input = "10111011111001111";
        int diskPartOne = 272;
        int diskPartTwo = 35651584;
        
        // Part 1
        System.out.println("Part One: " + partOne(input, diskPartOne));
        
        // Part2
        System.out.println("Part Two: " + partOne(input,diskPartTwo));
        
    }

    private static String partOne(String partA, int diskPartOne) {
        String data = partA;
        String checksum = "";
        while(data.length() < diskPartOne) {
            data = dragonCurve(data);
        }
        data = data.substring(0, diskPartOne);
        checksum = checksum(data);
        while(checksum.length() % 2 == 0) {
            checksum = checksum(checksum);
        }
        return checksum;
    }
    
    private static String dragonCurve(String partA) {
        String partB = StringUtils.reverse(partA).replaceAll("0", "x").replaceAll("1", "0").replaceAll("x", "1");
        return partA + "0" + partB;
    }
    
    private static String checksum(String data) {
        StringBuilder checksum = new StringBuilder();
        for(int i = 0; i < data.length(); i = i + 2) {
            if(data.substring(i, i+1).equals(data.substring(i+1, i+2))) {
                checksum.append("1");
            } else { 
                checksum.append("0");
            }
        }
        return checksum.toString();
    }
}
