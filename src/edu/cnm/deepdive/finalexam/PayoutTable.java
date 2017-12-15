package edu.cnm.deepdive.finalexam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Encapsulates a simple Keno payout table, driven by values found in a 
 * specified file.
 *  
 * @author Nicholas Bennett
 *
 */
public class PayoutTable {

  private static final String SPLIT_REGEX = "\\s+";
  
  private int[][] payouts;
  
  /**
   * Reads a payout table from a specified path. Line <i>N</i> (zero-based) in 
   * the table is assumed to contain the (<i>N</i> + 1) payouts for 0 through 
   * <i>N</i> values matched (aka the "catch"). Within a line, values are 
   * assumed to be whitespace-separated integers.
   *  
   * @param payoutsPath  location of payout file. 
   * @throws IOException if payout file cannot be found or read.
   */
  public PayoutTable(Path payoutsPath) throws IOException {
    payouts = Files.lines(payoutsPath)
                   .map((line) -> Arrays.stream((line.trim().split(SPLIT_REGEX)))
                                        .mapToInt(Integer::parseInt)
                                        .toArray())
                   .toArray(int[][]::new);
//      System.out.print(Arrays.toString(payouts));
      // TODO map payout to matching result

  }
  
  /**
   * Returns payout (against a $1 bet) for specified number of picks and number
   * of matches (aka the "catch").
   * 
   * @param numPicked   number of values picked.
   * @param numMatched  number of picks matching the numbers drawn.
   * @return            payout
   */
  public int get(int numPicked, int numMatched) {
    return payouts[numPicked][numMatched];
  }
  
  public int maxPickSize() {
    return payouts.length - 1;
  }
  
}
