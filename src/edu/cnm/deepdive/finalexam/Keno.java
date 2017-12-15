package edu.cnm.deepdive.finalexam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

  public static int[] userSelection = new int[15];
  public static String[] strs;
  public static List<Integer> computerSelection = new ArrayList<>();
  public static int indexWin = 0;
  public static int indexLoss = 0;

  private static PayoutTable payoutTable;

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
    try {
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);
      String line = br.readLine();
      strs = line.trim().split(" ");

      if (line.equals("-?") || line.equals("-help")) {
        System.out.println(
            "1. Choose a number from 1 to 80. \n" +
                "2. Pick no more 15 numbers. \n" +
                "3. You start with $100 in your account. \n");
      }

      if (line.equals("")){
        System.out.println("Please enter a number.");
      } else {
        if (strs.length > 15) {
          System.out.println("Please choose less than 15 numbers.");
        } else {
          for (int i = 0; i < strs.length; i++) {
            userSelection[i] = Integer.parseInt(strs[i]);

            if (userSelection[i] > 80 || userSelection[i] < 1) {
              System.out.println("Invalid Number: " + userSelection[i]);
            }
          }
        }
      }

      if (isInvalid()) {
        System.out.println("invalid number!");
      } else {
        generateAndRandomize();
      }


    } catch (IOException e) {
      throw new RuntimeException(e);
    }


  }

  /**
   * Checks if numbers are duplicated
   * @return returns true or false if a number is valid
   */
  public static boolean isInvalid() {
    for (int i = 0; i < strs.length; i++) {
      for (int j = i + 1; j < strs.length; j++) {
        if (strs[i].equals(strs[j])) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Generate, Shuffles, and compares userSelection with computerSelection and track
   * how many times userSelection and computerSelection matches.
   *
   */
  public static void generateAndRandomize() {
    List<Integer> randomEighty = new ArrayList<>();
    for (int i = 1; i <= 80; i++) {
      randomEighty.add(i);
    }

    Collections.shuffle(randomEighty);
    for (int i = 0; i < 20; i++) {
      computerSelection.add(randomEighty.get(i));
    }

    for (int i = 0; i < userSelection.length; i++) {
      if (computerSelection.contains(userSelection[i]))
        indexWin++;
//      System.out.println("Matches : " + userSelection[i]);
    }
    System.out.println("Number of wins : " + indexWin);
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

//    FileInputStream inStream = new FileInputStream(payoutTable);
//    InputStreamReader reader = new InputStreamReader(inStream);
//    BufferedReader buffer = new BufferedReader(reader);
//    System.out.println(buffer.readLine().trim().split(" ").length);

//    payoutTable.get(strs.length, indexWin);
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
  protected static boolean picksAreValid(int[] sortedPicks) {
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
