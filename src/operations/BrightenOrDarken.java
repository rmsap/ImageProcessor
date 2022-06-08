package operations;

import model.ImageProcessorModel;
import operations.Operation;

/**
 * This class represents an Operation that brightens or darkens an image by its scale.
 */
public class BrightenOrDarken implements Operation {
  int scale;

  /**
   * Construct a new BrightenOrDarken Operation with the given scale.
   * If the integer is negative, then the image should darken.
   * If the integer is positive, then the image should brigthen.
   * @param scale the scale of this BrightenOrDarken
   */
  public BrightenOrDarken(int scale) {
    this.scale = scale;
  }

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int [][] deepCopy = model.getImage(name);

    // starting at index 1 since the 0th index contains the header information
    for(int i = 1; i < deepCopy.length; i++) {
      for(int j = 0; j < deepCopy[i].length; j++) {
        deepCopy[i][j] += scale;
        if (deepCopy[i][j] < 0) {
          deepCopy[i][j] = 0;
        }
        else if (deepCopy[i][j] > deepCopy[0][2]) {
          deepCopy[i][j] = deepCopy[0][2];
        }
      }
    }
    return deepCopy;
  }
}
