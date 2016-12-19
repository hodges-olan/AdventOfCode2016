/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day17;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.math.*;

/**
 *
 * @author hodges-olan
 */
public class Day17 {
    
    public static void main(String[] args) {
        String input = "gdjjyniy";
        
        // Part 1
        System.out.println("Part One: " + partOne(input));
        
        // Part2
        System.out.println("Part Two: " + partTwo());
        
    }

    private static String partOne(String input) {
        int x = 0, y = 0;
        while(x != 3 && y != 3) {
            String md5sum = md5sum(input).substring(0,4);
            String move = moveRooms(md5sum, x, y);
            switch(move) {
                case "U":
                    y--;
                    break;
                case "D":
                    y++;
                    break;
                case "L":
                    x--;
                    break;
                case "R":
                    x++;
                    break;
                case "F":
                    x = 3;
                    y = 3;
                    break;
            }
            input = input + move;
            System.out.println(input);
        }
        return input;
    }

    private static String partTwo() {
        return "Not yet";
    }
    
    private static String md5sum(String input) {
        String md5sum = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes(),0,input.length());
            md5sum = new BigInteger(1,md.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Day17.class.getName()).log(Level.SEVERE, null, ex);
        }
        return md5sum;
    }
    
    private static String moveRooms(String md5sum, int x, int y) {
        String move = "F";
        boolean up = (y != 0) && Integer.parseInt(md5sum.substring(0,1),16) > 10;
        boolean down = (y != 3) && Integer.parseInt(md5sum.substring(1,2),16) > 10;
        boolean left = (x != 0) && Integer.parseInt(md5sum.substring(2,3),16) > 10;
        boolean right = (x != 3) && Integer.parseInt(md5sum.substring(3,4),16) > 10;
        if(down) move = "D";
        else if(right) move = "R";
        else if(left) move = "L";
        else if(up) move = "U";
        System.out.println(Integer.parseInt(md5sum.substring(0,1),16));
        System.out.println(Integer.parseInt(md5sum.substring(1,2),16));
        System.out.println(Integer.parseInt(md5sum.substring(2,3),16));
        System.out.println(Integer.parseInt(md5sum.substring(3,4),16));
        return move;
    }
    
}
