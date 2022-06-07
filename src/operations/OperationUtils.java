package operations;

/**
 * This class represents methods that are useful for Operations but do not fit in its interface.
 */
public class OperationUtils {
  /**
   * Copy the given array into a new array (this is done to avoid passing a reference to an array
   * and mutate the original by mistake).
   * @param arr the array to copy
   * @return a copy of the given array
   */
  public static int[][] copy(int[][] arr) {
    int[][] copy = new int[arr.length][arr[0].length];

    // Copy all contents from the given array to the copy
    for(int r = 0; r < copy.length; r++) {
      for(int c = 0; c < copy[r].length; c++) {
        copy[r][c] = arr[r][c];
      }
    }
    return copy;
  }
}
