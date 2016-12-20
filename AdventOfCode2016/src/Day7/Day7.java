//--- Day 7: Internet Protocol Version 7 ---
//
//While snooping around the local network of EBHQ, you compile a list of IP addresses (they're IPv7, of course; IPv6 is much too limited). You'd like to figure out which IPs support TLS (transport-layer snooping).
//
//An IP supports TLS if it has an Autonomous Bridge Bypass Annotation, or ABBA. An ABBA is any four-character sequence which consists of a pair of two different characters followed by the reverse of that pair, such as xyyx or abba. However, the IP also must not have an ABBA within any hypernet sequences, which are contained by square brackets.
//
//For example:
//
//abba[mnop]qrst supports TLS (abba outside square brackets).
//abcd[bddb]xyyx does not support TLS (bddb is within square brackets, even though xyyx is outside square brackets).
//aaaa[qwer]tyui does not support TLS (aaaa is invalid; the interior characters must be different).
//ioxxoj[asdfgh]zxcvbn supports TLS (oxxo is outside square brackets, even though it's within a larger string).
//How many IPs in your puzzle input support TLS?
//
//Your puzzle answer was 118.
//
//--- Part Two ---
//
//You would also like to know which IPs support SSL (super-secret listening).
//
//An IP supports SSL if it has an Area-Broadcast Accessor, or ABA, anywhere in the supernet sequences (outside any square bracketed sections), and a corresponding Byte Allocation Block, or BAB, anywhere in the hypernet sequences. An ABA is any three-character sequence which consists of the same character twice with a different character between them, such as xyx or aba. A corresponding BAB is the same characters but in reversed positions: yxy and bab, respectively.
//
//For example:
//
//aba[bab]xyz supports SSL (aba outside square brackets with corresponding bab within square brackets).
//xyx[xyx]xyx does not support SSL (xyx, but no corresponding yxy).
//aaa[kek]eke supports SSL (eke in supernet with corresponding kek in hypernet; the aaa sequence is not related, because the interior character must be different).
//zazbz[bzb]cdb supports SSL (zaz has no corresponding aza, but zbz has a corresponding bzb, even though zaz and zbz overlap).
//How many IPs in your puzzle input support SSL?
//
//Your puzzle answer was 260.

package Day7;

import FileIO.FileIO;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hodges-olan
 */
public class Day7 {

    public static void main(String[] args) {
        String filePath = "day7.txt";
        ArrayList<String> input = FileIO.readFileToArrayListString(filePath);
        
        // Part 1
        System.out.println("Part One: " + partOne(input));
        
        // Part2
        System.out.println("Part Two: " + partTwo(input));
    }
    
    private static int partOne(ArrayList<String> input) {
        int total = 0;
        for(String eachInput : input) {
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
    
    private static int partTwo(ArrayList<String> input) {
        int total = 0;
        for(String eachInput : input) {
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
