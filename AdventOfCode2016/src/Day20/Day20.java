//--- Day 20: Firewall Rules ---
//
//You'd like to set up a small hidden computer here so you can use it to get back into the network later. However, the corporate firewall only allows communication with certain external IP addresses.
//
//You've retrieved the list of blocked IPs from the firewall, but the list seems to be messy and poorly maintained, and it's not clear which IPs are allowed. Also, rather than being written in dot-decimal notation, they are written as plain 32-bit integers, which can have any value from 0 through 4294967295, inclusive.
//
//For example, suppose only the values 0 through 9 were valid, and that you retrieved the following blacklist:
//
//5-8
//0-2
//4-7
//The blacklist specifies ranges of IPs (inclusive of both the start and end value) that are not allowed. Then, the only IPs that this firewall allows are 3 and 9, since those are the only numbers not in any range.
//
//Given the list of blocked IPs you retrieved from the firewall (your puzzle input), what is the lowest-valued IP that is not blocked?
//
//Your puzzle answer was 22887907.
//
//--- Part Two ---
//
//How many IPs are allowed by the blacklist?
//
//Your puzzle answer was 109.
//
//Both parts of this puzzle are complete! They provide two gold stars: **

package Day20;

import FileIO.FileIO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author hodges-olan
 */
public class Day20 {
    
    public static void main(String[] args) {
        String filePath = "day20.txt";
        ArrayList<String> input = FileIO.readFileToArrayListString(filePath);
        TreeMap<Long, Long> ipAddresses = acquireAddresses(input);
        
        // Part 1
        System.out.println("Part One: " + partOne(ipAddresses));
        
        // Part2
        System.out.println("Part Two: " + partTwo(ipAddresses));
        
    }

    private static String partOne(TreeMap<Long, Long> ipAddresses) {
        Iterator<Long> i = ipAddresses.navigableKeySet().iterator();
        Long first = i.next();
        Long last = 0L;

        while (i.hasNext()) {
            Long current = i.next();
            if ((ipAddresses.get(first) + 1) > last) last = ipAddresses.get(first) + 1;
            if (current > last) {
                return Long.toString(last);
            }
            first = current;
        }

        return "Not found";
    }

    private static String partTwo(TreeMap<Long, Long> ipAddresses) {
        Iterator<Long> i = ipAddresses.navigableKeySet().iterator();
        Long first = i.next();
        Long last = 0L;
        Long count = 0L;

        while (i.hasNext()) {
            Long current = i.next();
            if ((ipAddresses.get(first) + 1) > last) last = ipAddresses.get(first) + 1;
            if (current > last) {
                count += current - last;
            }
            first = current;
        }

        return Long.toString(count);
    }

    private static TreeMap<Long, Long> acquireAddresses(ArrayList<String> input) {
        TreeMap<Long, Long> tm = new TreeMap<>();
        for(String entry : input) {
            String[] temp = entry.split("-");
            tm.put(Long.parseLong(temp[0]), Long.parseLong(temp[1]));
        }
        return tm;
    }
    
}
