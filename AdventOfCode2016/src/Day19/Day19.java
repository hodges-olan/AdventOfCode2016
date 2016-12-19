/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day19;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author hodges-olan
 */
public class Day19 {
    
    public static void main(String[] args) {
        
        int input = 3001330;
        
        // Part 1
        System.out.println("Part One: " + partOne(input));
        
        // Part2
        System.out.println("Part Two: " + partTwo(input));
        
    }

    private static String partOne(int input) {
        LinkedList<Integer> elves = new LinkedList<>();
        for(int i = 1; i <= input; i++) elves.add(i);
        boolean take = false;
        while(elves.size() != 1) {
            Iterator<Integer> iterator = elves.iterator();
            while(iterator.hasNext()) {
                iterator.next();
                if(take) iterator.remove();
                take = !take;
            }
        }
        return elves.pollFirst().toString();
    }

    private static String partTwo(int input) {
        LinkedList<Integer> que1 = new LinkedList<>();
        LinkedList<Integer> que2 = new LinkedList<>();
        for(int i = 1; i<=input; i++) {
            if(i<=input/2) que1.addLast(i);
            else que2.addLast(i);
        }

        while(que1.size() + que2.size() != 1) {
            int x = que1.pollFirst();
            if(que1.size() == que2.size()) {
                que1.pollLast();
            }else {
                que2.pollFirst();
            }
            que2.addLast(x);
            int a = que2.pollFirst();
            que1.addLast(a);
        }
        return que1.pollFirst().toString();
    }
    
}
