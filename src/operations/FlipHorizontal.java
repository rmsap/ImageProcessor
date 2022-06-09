package operations;

import model.ImageProcessorModel;
import operations.Operation;

/**
 * This class represents an Operation that flips the given ImageProcessorModel.
 */
public class FlipHorizontal implements Operation {
  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int [][] copy = model.getImage(name);
    int totalCols = copy.length / copy[0][1];

    for (int i = 1; i < copy.length; i++) {
      int row = i / copy[0][0] + 1;
      int col = i % totalCols;
      this.swap(copy, i, (copy[0][0] * row) - (col - 1));

      // If we are halfway through this row, skip to the beginning of the next row so that we don't
      // "double swap" the first half of the row and the second half.
      if (i % (copy[0][0] / 2) == 0) {
        i += copy[0][0] / 2;
      }
    }

    return copy;
  }

  /**
   * Swap the two items at the given indices of the given array.
   * @param arr the array whose items we want to swap
   * @param indexOne the first index whose values we are swapping
   * @param indexTwo the second index whose value we are swapping
   */
  private void swap(int[][] arr, int indexOne, int indexTwo) {
    int[] temp = arr[indexOne];
    arr[indexOne] = arr[indexTwo];
    arr[indexTwo] = temp;
  }
}
