//--- Day 25: Clock Signal ---
//
//You open the door and find yourself on the roof. The city sprawls away from you for miles and miles.
//
//There's not much time now - it's already Christmas, but you're nowhere near the North Pole, much too far to deliver these stars to the sleigh in time.
//
//However, maybe the huge antenna up here can offer a solution. After all, the sleigh doesn't need the stars, exactly; it needs the timing data they provide, and you happen to have a massive signal generator right here.
//
//You connect the stars you have to your prototype computer, connect that to the antenna, and begin the transmission.
//
//Nothing happens.
//
//You call the service number printed on the side of the antenna and quickly explain the situation. "I'm not sure what kind of equipment you have connected over there," he says, "but you need a clock signal." You try to explain that this is a signal for a clock.
//
//"No, no, a clock signal - timing information so the antenna computer knows how to read the data you're sending it. An endless, alternating pattern of 0, 1, 0, 1, 0, 1, 0, 1, 0, 1...." He trails off.
//
//You ask if the antenna can handle a clock signal at the frequency you would need to use for the data from the stars. "There's no way it can! The only antenna we've installed capable of that is on top of a top-secret Easter Bunny installation, and you're definitely not-" You hang up the phone.
//
//You've extracted the antenna's clock signal generation assembunny code (your puzzle input); it looks mostly compatible with code you worked on just recently.
//
//This antenna code, being a signal generator, uses one extra instruction:
//
//out x transmits x (either an integer or the value of a register) as the next value for the clock signal.
//The code takes a value (via register a) that describes the signal to generate, but you're not sure how it's used. You'll have to find the input to produce the right signal through experimentation.
//
//What is the lowest positive integer that can be used to initialize register a and cause the code to output a clock signal of 0, 1, 0, 1... repeating forever?
//
//Your puzzle answer was 182.
//
//--- Part Two ---
//
//The antenna is ready. Now, all you need is the fifty stars required to generate the signal for the sleigh, but you don't have enough.
//
//You look toward the sky in desperation... suddenly noticing that a lone star has been installed at the top of the antenna! Only 49 more to go.
//
//If you like, you can [Retransmit the Signal].
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, all that is left is for you to admire your advent calendar.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day25;

import FileIO.FileIO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hodges-olan
 */
public class Day25 {
    
    public static void main(String[] args) {
        String filePath = "day25.txt";
        ArrayList<String> input = FileIO.readFileToArrayListString(filePath);
        Map registers;
        
        // Part 1
        System.out.print("Part One: ");
        boolean valid = false;
        int i = 0;
        while(!valid) {
            registers = initializeRegisters(i);
            if(partOne(registers, input).equals("01010101")) valid = true;
            i++;
        }
        System.out.print(Integer.toString(i - 1) + "\n");
    }

    private static String partOne(Map registers, ArrayList<String> input) {
        int j = 0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < input.size() && j < 8; i++) {
            String[] splitInstructions = input.get(i).split(" ");
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(splitInstructions[1]);
            switch (splitInstructions[0]) {
                case "cpy":
                    if(m.find()) {
                        registers.put(splitInstructions[2], Integer.parseInt(splitInstructions[1]));
                    } else {
                        registers.put(splitInstructions[2], Integer.parseInt(registers.get(splitInstructions[1]).toString()));
                    }
                    break;
                case "inc":
                    registers.put(splitInstructions[1],(Integer.parseInt(registers.get(splitInstructions[1]).toString()) + 1));
                    break;
                case "dec":
                    registers.put(splitInstructions[1],(Integer.parseInt(registers.get(splitInstructions[1]).toString()) - 1));
                    break;
                case "jnz":
                    if(m.find()) {
                        if(Integer.parseInt(splitInstructions[1]) != 0) {
                            i = ((i + Integer.parseInt(splitInstructions[2]) - 1) <= 0) ? -1 : i + Integer.parseInt(splitInstructions[2]) - 1;
                        }
                    } else {
                        if(Integer.parseInt(registers.get(splitInstructions[1]).toString()) != 0) {
                            i = ((i + Integer.parseInt(splitInstructions[2]) - 1) <= 0) ? -1 : i + Integer.parseInt(splitInstructions[2]) - 1;
                        }
                    }
                    break;
                case "out":
                    sb.append(registers.get(splitInstructions[1]).toString());
                    j++;
                    break;
            }
        }
        return sb.toString();
    }

    private static Map initializeRegisters(int i) {
        Map registers = new HashMap();
        registers.put("a", i);
        registers.put("b", 0);
        registers.put("c", 0);
        registers.put("d", 0);
        return registers;
    }
    
}
