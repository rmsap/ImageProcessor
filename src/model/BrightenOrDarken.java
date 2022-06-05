package model;

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
    int [][] deepCopy = new int [model.getImage(name).length][model.getImage(name)[0].length];
    // starting at index 1 since the 0th index contains the header information
    for(int r = 1; r < deepCopy.length; r++) {
      for(int c = 0; c < deepCopy[0].length; c++) {
        deepCopy[r][c] = model.getImage(name)[r][c];
      }
    }
    // starting at index 1 since the 0th index contains the header information
    for(int i = 1; i < deepCopy.length; i++) {
      for(int j = 0; j < deepCopy.length; j++) {
        if(scale < 0) { // if its negative we darken
          if(deepCopy[i][j] + scale < 0) {
            deepCopy[i][j] = 0;
          }
          else {
            deepCopy[i][j] = deepCopy[i][j] + scale;
          }
        }
        else if(scale >= 0) { // if its positive then we brighten
          if(deepCopy[i][j] + scale > 255) {
            deepCopy[i][j] = 255;
          }
          else {
            deepCopy[i][j] = deepCopy[i][j] + scale;
          }
        }
      }
    }
    return deepCopy;


  }
}
