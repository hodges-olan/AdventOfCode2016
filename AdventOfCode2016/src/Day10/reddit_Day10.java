//--- Day 10: Balance Bots ---
//
//You come upon a factory in which many robots are zooming around handing small microchips to each other.
//
//Upon closer examination, you notice that each bot only proceeds when it has two microchips, and once it does, it gives each one to a different bot or puts it in a marked "output" bin. Sometimes, bots take microchips from "input" bins, too.
//
//Inspecting one of the microchips, it seems like they each contain a single number; the bots must use some logic to decide what to do with each chip. You access the local control computer and download the bots' instructions (your puzzle input).
//
//Some of the instructions specify that a specific-valued microchip should be given to a specific bot; the rest of the instructions indicate what a given bot should do with its lower-value or higher-value chip.
//
//For example, consider the following instructions:
//
//value 5 goes to bot 2
//bot 2 gives low to bot 1 and high to bot 0
//value 3 goes to bot 1
//bot 1 gives low to output 1 and high to bot 0
//bot 0 gives low to output 2 and high to output 0
//value 2 goes to bot 2
//Initially, bot 1 starts with a value-3 chip, and bot 2 starts with a value-2 chip and a value-5 chip.
//Because bot 2 has two microchips, it gives its lower one (2) to bot 1 and its higher one (5) to bot 0.
//Then, bot 1 has two microchips; it puts the value-2 chip in output 1 and gives the value-3 chip to bot 0.
//Finally, bot 0 has two microchips; it puts the 3 in output 2 and the 5 in output 0.
//In the end, output bin 0 contains a value-5 microchip, output bin 1 contains a value-2 microchip, and output bin 2 contains a value-3 microchip. In this configuration, bot number 2 is responsible for comparing value-5 microchips with value-2 microchips.
//
//Based on your instructions, what is the number of the bot that is responsible for comparing value-61 microchips with value-17 microchips?
//
//Your puzzle answer was 118.
//
//--- Part Two ---
//
//What do you get if you multiply together the values of one chip in each of outputs 0, 1, and 2?
//
//Your puzzle answer was 143153.

package Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class reddit_Day10 {

  private static HashMap<Integer, ArrayList<Integer>> bots = new HashMap<>();
  private static HashMap<Integer, ArrayList<Integer>> outputs = new HashMap<>();
  private static HashMap<Integer, String> transferStorage = new HashMap<>();

  private static final int TARGET_CHIP_1 = 61;
  private static final int TARGET_CHIP_2 = 17;

  // Format: {low chip recipient} - {high chip recipient}
  private static final String TransferStorageFormat = "%s%d - %s%d";
  private static final String TRANSFER_STORAGE =
    "^([a-z]+)(\\d+) - ([a-z]+)(\\d+)$";
  private static final Pattern transferStoragePattern =
    Pattern.compile( TRANSFER_STORAGE );

  private static final String ASSIGNMENT =
    "^value (\\d+) .* (\\d+)$";
  private static final String TRANSFER =
    "^[a-z]+ (\\d+) .* ([a-z]+) (\\d+) .* ([a-z]+) (\\d+)$";
  private static final Pattern assignmentPattern =
    Pattern.compile( ASSIGNMENT );
  private static final Pattern transferPattern =
    Pattern.compile( TRANSFER );

  public static void main( String[] args ) {
    String fileName = "day10.txt";

    try {
      FileReader reader = new FileReader( fileName );
      BufferedReader bufferedReader = new BufferedReader( reader );

      String line;

      while ( ( line = bufferedReader.readLine() ) != null ) {
        Matcher assignmentMatcher = assignmentPattern.matcher( line );
        Matcher transferMatcher = transferPattern.matcher( line );

        // If we directly assign a chip id to a bot, do that
        if ( assignmentMatcher.matches() ) {
          int chipId = Integer.parseInt( assignmentMatcher.group( 1 ) );
          int botId = Integer.parseInt( assignmentMatcher.group( 2 ) );

          addChipId( bots, botId, chipId );
        } else if ( transferMatcher.matches() ) {
          // else store an encoding of a bot transfer
          int botId = Integer.parseInt( transferMatcher.group( 1 ) );
          String transfer = String.format(
            TransferStorageFormat,
            transferMatcher.group( 2 ),                       // low chip recipient type
            Integer.parseInt( transferMatcher.group( 3 ) ),   // low chip recipient id
            transferMatcher.group( 4 ),                       // high chip recipient type
            Integer.parseInt( transferMatcher.group( 5 ) ) ); // high chip recipient id

          transferStorage.put( botId, transfer );
        } else {
          System.out.format( "Unknown input instruction: \n%s\n", line );
          System.exit( 1 );
        }
      }

      reader.close();
    } catch ( IOException e ) {
      e.printStackTrace();
    }

    // Now we go through all the bots ...
    while ( bots.size() > 0 ) {
      HashSet<Map.Entry<Integer, ArrayList<Integer>>> botSet =
        new HashSet( bots.entrySet() );

      for ( Map.Entry<Integer, ArrayList<Integer>> bot : botSet ) {
        int botId = bot.getKey();
        ArrayList<Integer> chipIds = bot.getValue();
        // ... and if a bot is holding 2 chips ...
        if ( chipIds.size() == 2 ) {
          // ... first check if we have the chips we want to look for ...
          if ( chipIds.contains( TARGET_CHIP_1 ) &&
              chipIds.contains( TARGET_CHIP_2 ) ) {
            System.out.println( botId ); // (part1) print the bot that holds these
          }

          // ... then execute the transfer instruction
          String transfer = transferStorage.get( botId );
          Matcher transferStorageMatcher =
            transferStoragePattern.matcher( transfer );

          // just for safety
          if ( !transferStorageMatcher.matches() ) {
            System.out.format( "Transfer Storage format not recognized: \n%s\n",
              transfer );
            System.exit( 1 );
          }

          String lowRecip = transferStorageMatcher.group( 1 );
          int lowRecipId = Integer.parseInt( transferStorageMatcher.group( 2 ) );
          String highRecip = transferStorageMatcher.group( 3 );
          int highRecipId = Integer.parseInt( transferStorageMatcher.group( 4 ) );

          // sort the chip ids so that we know which one is low and high
          Collections.sort( chipIds );

          switch ( lowRecip ) {
            case "bot": addChipId( bots, lowRecipId, chipIds.get( 0 ) ); break;
            case "output": addChipId( outputs, lowRecipId, chipIds.get( 0 ) ); break;
          }

          switch( highRecip ) {
            case "bot": addChipId( bots, highRecipId, chipIds.get( 1 ) ); break;
            case "output": addChipId( outputs, highRecipId, chipIds.get( 1 ) ); break;
          }

          // finally remove the bot from the map, since the bot is done with work
          bots.remove( botId );
        }
      }
    }

    // (part2) multiply together the values of one cheap in each of the outputs 0, 1, 2
    long result = 1;
    for ( int outputId = 0; outputId <= 2; outputId++ ) {
      result *= outputs.get( outputId ).get( 0 );
    }
    System.out.println( result );
  }

  private static void addChipId(
    HashMap<Integer, ArrayList<Integer>> map, int id, int chipId ) {

    if ( !map.containsKey( id ) ) {
      map.put( id, new ArrayList<>() );
    }

    map.get( id ).add( chipId );
  }
}