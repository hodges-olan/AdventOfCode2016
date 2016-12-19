//--- Day 19: An Elephant Named Joseph ---
//
//The Elves contact you over a highly secure emergency channel. Back at the North Pole, the Elves are busy misunderstanding White Elephant parties.
//
//Each Elf brings a present. They all sit in a circle, numbered starting with position 1. Then, starting with the first Elf, they take turns stealing all the presents from the Elf to their left. An Elf with no presents is removed from the circle and does not take turns.
//
//For example, with five Elves (numbered 1 to 5):
//
//  1
//5   2
// 4 3
//Elf 1 takes Elf 2's present.
//Elf 2 has no presents and is skipped.
//Elf 3 takes Elf 4's present.
//Elf 4 has no presents and is also skipped.
//Elf 5 takes Elf 1's two presents.
//Neither Elf 1 nor Elf 2 have any presents, so both are skipped.
//Elf 3 takes Elf 5's three presents.
//So, with five Elves, the Elf that sits starting in position 3 gets all the presents.
//
//With the number of Elves given in your puzzle input, which Elf gets all the presents?
//
//Your puzzle answer was 1808357.
//
//--- Part Two ---
//
//Realizing the folly of their present-exchange rules, the Elves agree to instead steal presents from the Elf directly across the circle. If two Elves are across the circle, the one on the left (from the perspective of the stealer) is stolen from. The other rules remain unchanged: Elves with no presents are removed from the circle entirely, and the other elves move in slightly to keep the circle evenly spaced.
//
//For example, with five Elves (again numbered 1 to 5):
//
//The Elves sit in a circle; Elf 1 goes first:
//  1
//5   2
// 4 3
//Elves 3 and 4 are across the circle; Elf 3's present is stolen, being the one to the left. Elf 3 leaves the circle, and the rest of the Elves move in:
//  1           1
//5   2  -->  5   2
// 4 -          4
//Elf 2 steals from the Elf directly across the circle, Elf 5:
//  1         1 
//-   2  -->     2
//  4         4 
//Next is Elf 4 who, choosing between Elves 1 and 2, steals from Elf 1:
// -          2  
//    2  -->
// 4          4
//Finally, Elf 2 steals from Elf 4:
// 2
//    -->  2  
// -
//So, with five Elves, the Elf that sits starting in position 2 gets all the presents.
//
//With the number of Elves given in your puzzle input, which Elf now gets all the presents?
//
//Your puzzle answer was 1407007.
//
//Both parts of this puzzle are complete! They provide two gold stars: **

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
