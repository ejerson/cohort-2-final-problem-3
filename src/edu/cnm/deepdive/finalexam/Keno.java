package edu.cnm.deepdive.finalexam;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Partial implementation of a simple Keno game, with a payout table specified in
 * resources/payouts.txt
 */
public class Keno {

  /** Classpath-relative location of payouts file. */
  public static final String PAYOUTS_RESOURCE = "resources/payouts.txt";
  /** Minimum value (inclusive) in number pool (and minimum valid pick). */
  public static final int MIN_VALUE = 1;
  /** Maximum value (inclusive) in number pool (and maximum valid pick). */
  public static final int MAX_VALUE = 80;

  private PayoutTable payoutTable;

  /**
   * Entry point for Keno application. 
   *  
   * @param args
   * @throws IOException
   * @throws URISyntaxException
   */
  public static void main(String[] args) throws IOException, URISyntaxException {
    ClassLoader loader = Keno.class.getClassLoader();
    new Keno(Paths.get(loader.getResource(PAYOUTS_RESOURCE).toURI()));
    // TODO - Deal with command line args, or pass them along to Keno instance.
  }

  /**
   * Initializes Keno instance by creating the payout table and (eventually)
   * creating a number pool, which will be shuffled and drawn from in each play.
   *  
   * @param payoutsPath     location of payout file.
   * @throws IOException    if payout file cannot be found or read.
   */
  public Keno(Path payoutsPath) throws IOException {
    payoutTable = new PayoutTable(payoutsPath);
    // TODO - Generate number pool. 
  }

  /**
   * Checks <code>int</code> array of picks (which <strong>must</strong> already
   * be sorted) for validity. If any picks are less that {@link #MIN_VALUE} or
   * greater than {@link #MAX_VALUE|, or if any picks are duplicated, then the 
   * picks are considered invalid.
   *  
   * @param sortedPicks   values picked in Keno play.
   * @return              <code>true</code> if picks are valid; false otherwise.
   */
  protected boolean picksAreValid(int[] sortedPicks) {
    int previousPick = Integer.MIN_VALUE;
    boolean valid = true;
    if (sortedPicks.length > payoutTable.maxPickSize()) {
      valid = false;
    } else {
      for (int pick : sortedPicks) {
        if (pick == previousPick || pick < MIN_VALUE || pick > MAX_VALUE) {
          valid = false;
          break;
        }
        previousPick = pick;
      }
    }
    return valid;
  }

  

}
