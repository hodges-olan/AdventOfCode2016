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