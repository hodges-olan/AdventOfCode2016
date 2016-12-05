/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 71100096
 */
public class Day5 {
    public static void main(String[] args) {
        partOne();
        partTwo();
    }
    
    private static void partOne() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String puzzleInput = "wtnhxymk", decimalString, hashCombo, md5sum;
            StringBuilder sb = new StringBuilder();
            StringBuilder psb = new StringBuilder();
            int decimal = 0;
            int valid = 0;
            byte byteData[];
            char pChar;
            
            do {
                decimalString = String.valueOf(decimal);
                hashCombo = puzzleInput + decimalString;
                md.update(hashCombo.getBytes());
                byteData = md.digest();
                for (int i = 0; i < byteData.length; i++) {
                    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                }
                md5sum = sb.toString();
                if (md5sum.substring(0,5).equals("00000")) {
                    valid++;
                    pChar = md5sum.substring(5,6).charAt(0);
                    psb.append(pChar);
                }
                decimal = ++decimal;
                sb.setLength(0);
            } while (valid < 8);
            String password = psb.toString();
            System.out.println("Part 1 Password: " + password);
        } catch (NoSuchAlgorithmException ex) { Logger.getLogger(Day5.class.getName()).log(Level.SEVERE, null, ex);}
    }
    
    private static void partTwo() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String puzzleInput = "wtnhxymk", decimalString, hashCombo, md5sum;
            StringBuilder sb = new StringBuilder();
            int decimal = 0;
            int valid = 0;
            byte byteData[];
            int pLocation;
            String pLocationChar;
            char pChar;
            char[] password = new char[8];
            Pattern pLocationPattern = Pattern.compile("[0-7]");
            Matcher mLocationMatcher;
            
            do {
                decimalString = String.valueOf(decimal);
                hashCombo = puzzleInput + decimalString;
                md.update(hashCombo.getBytes());
                byteData = md.digest();
                for (int i = 0; i < byteData.length; i++) {
                    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                }
                md5sum = sb.toString();
                if (md5sum.substring(0,5).equals("00000")) {
                    pLocationChar = md5sum.substring(5,6);
                    mLocationMatcher = pLocationPattern.matcher(pLocationChar);
                    if(mLocationMatcher.find()) {
                        pLocation = Integer.parseInt(mLocationMatcher.group());
                        pChar = md5sum.substring(6,7).charAt(0);
                        if((int) password[pLocation] == 0) {
                            password[pLocation] = pChar;
                            valid++;
                        }
                    }
                }
                decimal = ++decimal;
                sb.setLength(0);
            } while (valid < 8);
            System.out.print("Part 2 Password: ");
            for(char pass : password) {
                System.out.print(pass);
            }
            System.out.println("");
        } catch (NoSuchAlgorithmException ex) { Logger.getLogger(Day5.class.getName()).log(Level.SEVERE, null, ex);}
    }
}
