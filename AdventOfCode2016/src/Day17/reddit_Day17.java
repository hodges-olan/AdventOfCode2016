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

import gnu.trove.list.TCharList;
import gnu.trove.list.array.TCharArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class reddit_Day17 {

    public static final TIntObjectMap<char[]> SUCCESS = new TIntObjectHashMap<>();
    public static final HashFunction MD5 = Hashing.md5();
    public static final String INPUT = "gdjjyniy";
    public static final char[] DIRS = { 'U', 'D', 'L', 'R' };
    public static final int[] DX = { 0, 0, -1, 1 };
    public static final int[] DY = { -1, 1, 0, 0 };
    
    public static int doors(HashCode hash) {
        final String str = hash.toString();
        int doors = 0;
        
        for (int i = 0; i < reddit_Day17.DIRS.length; i++) {
            final char c = str.charAt(i);
            
            if (c >= 'b' && c <= 'f') {
                doors |= 1 << i;
            }
        }
        
        return doors;
    }
    
    public static void delve(TCharList stack, int x, int y) {
        if (x == 3 && y == 3) {
            // WOW, it wanted the PATH, not just the length
//            Day17.success.add(stack.size());
            reddit_Day17.SUCCESS.put(stack.size(), stack.toArray());
            return;
        }
        
        final int doors = reddit_Day17.doors(reddit_Day17.MD5.hashString(new StringBuilder(reddit_Day17.INPUT).append(stack.toArray()), Charsets.US_ASCII));
        
        for (int i = 0, nx, ny; i < reddit_Day17.DIRS.length; i++) {
            // Check that the door is open and we won't exit the grid by moving that way
            if ((1 << i & doors) != 0 && (Math.floorDiv(nx = x +  reddit_Day17.DX[i], 4) | Math.floorDiv(ny = y + reddit_Day17.DY[i], 4)) == 0) {
                stack.add(reddit_Day17.DIRS[i]);
                reddit_Day17.delve(stack, nx, ny);
                stack.removeAt(stack.size() - 1);
            }
        }
    }
    
    public static void main(String[] args) {
        reddit_Day17.delve(new TCharArrayList(), 0, 0);
        
        final IntSummaryStatistics stats;
        
        System.out.println(stats = IntStream.of(reddit_Day17.SUCCESS.keys()).summaryStatistics());
        System.out.println("Min path: " + String.valueOf(reddit_Day17.SUCCESS.get(stats.getMin())));
    }
}