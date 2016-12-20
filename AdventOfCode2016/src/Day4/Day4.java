//--- Day 4: Security Through Obscurity ---
//
//Finally, you come across an information kiosk with a list of rooms. Of course, the list is encrypted and full of decoy data, but the instructions to decode the list are barely hidden nearby. Better remove the decoy data first.
//
//Each room consists of an encrypted name (lowercase letters separated by dashes) followed by a dash, a sector ID, and a checksum in square brackets.
//
//A room is real (not a decoy) if the checksum is the five most common letters in the encrypted name, in order, with ties broken by alphabetization. For example:
//
//aaaaa-bbb-z-y-x-123[abxyz] is a real room because the most common letters are a (5), b (3), and then a tie between x, y, and z, which are listed alphabetically.
//a-b-c-d-e-f-g-h-987[abcde] is a real room because although the letters are all tied (1 of each), the first five are listed alphabetically.
//not-a-real-room-404[oarel] is a real room.
//totally-real-room-200[decoy] is not.
//Of the real rooms from the list above, the sum of their sector IDs is 1514.
//
//What is the sum of the sector IDs of the real rooms?
//
//Your puzzle answer was 185371.
//
//--- Part Two ---
//
//With all the decoy data out of the way, it's time to decrypt this list and get moving.
//
//The room names are encrypted by a state-of-the-art shift cipher, which is nearly unbreakable without the right software. However, the information kiosk designers at Easter Bunny HQ were not expecting to deal with a master cryptographer like yourself.
//
//To decrypt a room name, rotate each letter forward through the alphabet a number of times equal to the room's sector ID. A becomes B, B becomes C, Z becomes A, and so on. Dashes become spaces.
//
//For example, the real name for qzmt-zixmtkozy-ivhz-343 is very encrypted name.
//
//What is the sector ID of the room where North Pole objects are stored?
//
//Your puzzle answer was 984.

package Day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hodges-olan
 */
public class Day4 {
    private static final ArrayList<String> ROOMS = new ArrayList<>();
    private static final ArrayList<String> CHECKSUMS = new ArrayList<>();
    private static final ArrayList<Integer> SECTORIDS = new ArrayList<>();
    private static final ArrayList<Integer> INVALIDROOMS = new ArrayList<>();
    
    public static void main(String[] args) {
        String filePath = "day4.txt";
        ArrayList<String> input = readFile(filePath);
        splitInput(input);
        
        // Part 1
        System.out.println("Part 1: " + partOne());
        
        // Part 2
        System.out.println("Part 2: " + partTwo());
    }
    
    private static ArrayList<String> readFile(String filePath) {
        ArrayList<String> input = new ArrayList<>();
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((read = in.readLine()) != null) {
                input.add(read.trim());
            }
        } catch (IOException ex) {
            Logger.getLogger(Day4.class.getName()).log(Level.SEVERE, null, ex);
        }
        return input;
    }
    
    private static void splitInput(ArrayList<String> input) {
        Pattern pRoom = Pattern.compile("^[a-zA-Z-]+");
        Pattern pNumber = Pattern.compile("\\d+");
        Pattern pChecksum = Pattern.compile("\\[\\w+\\]$");
        Matcher m;
        for(String splitInput : input) {
            m = pRoom.matcher(splitInput);
            while(m.find()) {
                ROOMS.add(StringUtils.removeEnd(m.group(), "-"));
            }
            m = pNumber.matcher(splitInput);
            while(m.find()) {
                SECTORIDS.add(Integer.parseInt(m.group()));
            }
            m = pChecksum.matcher(splitInput);
            while(m.find()) {
                CHECKSUMS.add(StringUtils.removeAll(m.group(), "[\\[\\]]"));
            }
        }
    }
    
    private static String partOne() {
        int total1 = 0;
        for(int i = 0; i < ROOMS.size(); i++) {
            int[] alphabet = countCharacters(i);
            int[][] topFive = topFiveChars(alphabet);
            if(!validRoom(topFive, i)) INVALIDROOMS.add(i);
        }
        removeInvalidRooms();
        for(int i = 0; i < ROOMS.size(); i++) {
            total1 += SECTORIDS.get(i);
        }
        return Integer.toString(total1);
    }
    
    private static String partTwo() {
        String roomName;
        for(int i = 0; i < ROOMS.size(); i++) {
            roomName = decryptRoom(i);
            if(roomName.equalsIgnoreCase("northpole object storage")) {
                return Integer.toString(SECTORIDS.get(i));
            }
        }
        return "null";
    }
    
    private static int[] countCharacters(int room) {
        int[] alphabet = new int[26];
        for(int i = 97; i < 123; i++) {
            alphabet[i-97] = StringUtils.countMatches(ROOMS.get(room), (char) i);
        }
        return alphabet;
    }
    private static int[][] topFiveChars(int[] alphabet) {
        int[][] topFiveArray = new int[5][2];
        for(int i = 0; i < 26; i++) {
            boolean change = false;
            for(int j = 0; (j < 5 && !change && alphabet[i] != 0); j++) {
                if(alphabet[i] > topFiveArray[j][1] || (i < topFiveArray[j][0] && alphabet[i] >= topFiveArray[j][1])) {
                    for(int x = 4; x >= j; x--) {
                        if(x != 4) {
                            topFiveArray[x+1][0] = topFiveArray[x][0];
                            topFiveArray[x+1][1] = topFiveArray[x][1];
                        }
                        topFiveArray[x][0] = i;
                        topFiveArray[x][1] = alphabet[i];
                        change = true;
                    }
                }
            }
        }
        return topFiveArray;
    }

    private static boolean validRoom(int[][] topFive, int checksum) {
        boolean valid = true;
        char[] checksumCharArray = CHECKSUMS.get(checksum).toCharArray();
        for(int i = 0; i < checksumCharArray.length; i++) {
            if(checksumCharArray[i] != (char) (topFive[i][0]+97)) {
                valid = false;
            }
        }
        return valid;
    }

    private static void removeInvalidRooms() {
        for(int i = INVALIDROOMS.size()-1; i >= 0; i--) {
            ROOMS.remove((int) INVALIDROOMS.get(i));
            SECTORIDS.remove((int) INVALIDROOMS.get(i));
            CHECKSUMS.remove((int) INVALIDROOMS.get(i));
        }
    }
    
    private static String decryptRoom(int i) {
        String roomName;
        StringBuilder sb = new StringBuilder();
        int increment = SECTORIDS.get(i)%26;
        String room = StringUtils.replaceAll(ROOMS.get(i), "-", " ");
        for(int j = 0; j < room.length(); j++) {
            if(room.charAt(j) != ' ') {
                char newChar = (char) (((int) room.charAt(j)) + increment);
                if((int) newChar > 122) {
                    newChar = ((char) (((int) newChar) - 26)); 
                }
                sb.append(newChar);
            } else {
                sb.append(room.charAt(j));
            }
        }
        roomName = sb.toString();
        return roomName;
    }
}