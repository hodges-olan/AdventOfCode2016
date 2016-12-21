//--- Day 17: Two Steps Forward ---
//
//You're trying to access a secure vault protected by a 4x4 grid of small rooms connected by doors. You start in the top-left room (marked S), and you can access the vault (marked V) once you reach the bottom-right room:
//
//#########
//#S| | | #
//#-#-#-#-#
//# | | | #
//#-#-#-#-#
//# | | | #
//#-#-#-#-#
//# | | |  
//####### V
//Fixed walls are marked with #, and doors are marked with - or |.
//
//The doors in your current room are either open or closed (and locked) based on the hexadecimal MD5 hash of a passcode (your puzzle input) followed by a sequence of uppercase characters representing the path you have taken so far (U for up, D for down, L for left, and R for right).
//
//Only the first four characters of the hash are used; they represent, respectively, the doors up, down, left, and right from your current position. Any b, c, d, e, or f means that the corresponding door is open; any other character (any number or a) means that the corresponding door is closed and locked.
//
//To access the vault, all you need to do is reach the bottom-right room; reaching this room opens the vault and all doors in the maze.
//
//For example, suppose the passcode is hijkl. Initially, you have taken no steps, and so your path is empty: you simply find the MD5 hash of hijkl alone. The first four characters of this hash are ced9, which indicate that up is open (c), down is open (e), left is open (d), and right is closed and locked (9). Because you start in the top-left corner, there are no "up" or "left" doors to be open, so your only choice is down.
//
//Next, having gone only one step (down, or D), you find the hash of hijklD. This produces f2bc, which indicates that you can go back up, left (but that's a wall), or right. Going right means hashing hijklDR to get 5745 - all doors closed and locked. However, going up instead is worthwhile: even though it returns you to the room you started in, your path would then be DU, opening a different set of doors.
//
//After going DU (and then hashing hijklDU to get 528e), only the right door is open; after going DUR, all doors lock. (Fortunately, your actual passcode is not hijkl).
//
//Passcodes actually used by Easter Bunny Vault Security do allow access to the vault if you know the right path. For example:
//
//If your passcode were ihgpwlah, the shortest path would be DDRRRD.
//With kglvqrro, the shortest path would be DDUDRLRRUDRD.
//With ulqzkmiv, the shortest would be DRURDRUDDLLDLUURRDULRLDUUDDDRR.
//Given your vault's passcode, what is the shortest path (the actual path, not just the length) to reach the vault?
//
//Your puzzle answer was DUDDRLRRRD.
//
//--- Part Two ---
//
//You're curious how robust this security solution really is, and so you decide to find longer and longer paths which still provide access to the vault. You remember that paths always end the first time they reach the bottom-right room (that is, they can never pass through it, only end in it).
//
//For example:
//
//If your passcode were ihgpwlah, the longest path would take 370 steps.
//With kglvqrro, the longest path would be 492 steps long.
//With ulqzkmiv, the longest path would be 830 steps long.
//What is the length of the longest path that reaches the vault?
//
//Your puzzle answer was 578.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//Your puzzle input was gdjjyniy.
//
//You can also [Share] this puzzle.

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
