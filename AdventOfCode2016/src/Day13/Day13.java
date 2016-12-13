/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day13;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author hodges-olan
 */
public class Day13 {
    
    public static void main(String[] args) {
        int input = 10;
        int startX = 1;
        int startY = 1;
        
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (wall(x, y, input)) {
                    System.out.print('#');
                } else {
                    System.out.print('.');
                }
            }
            System.out.print('\n');
        }
        
    }

    private static boolean wall(int x, int y, int input) {
        int total = (x * x) + (3 * x) + (2 * x * y) + y + (y * y) + input;
        String binary = Integer.toBinaryString(total);
        int totalOnes = StringUtils.countMatches(binary, '1');
        boolean valid = (totalOnes%2 == 1) ? true : false;
        return valid;
    }
    
}
