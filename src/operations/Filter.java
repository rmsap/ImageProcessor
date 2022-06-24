package operations;


import java.util.HashMap;
import java.util.Map;

import model.ImageProcessorModel;

/**
 * This class represents an Operation that can apply a filter to an image, such as blurring or
 * sharpening.
 */
public class Filter implements Operation {
  /**
   * This enum represents potential filters that can be applied to an image.
   */
  public enum Filters { Blur, Sharpen }

  ;

  // The filter that we will apply to this image
  private final Filters filter;

  // A map from filters to the matrix that applies them
  private final Map<Filters, double[][]> filtersMap;

  /**
   * Represents a storage of various filter operations.
   * @param filter representing the collection of Filter enumerations
   */
  public Filter(Filters filter) {
    this.filter = filter;

    this.filtersMap = new HashMap<Filters, double[][]>();
    this.filtersMap.put(Filters.Blur, new double[][]{
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}});
    this.filtersMap.put(Filters.Sharpen, new double[][]{
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}});
  }

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int[][] copy = model.getImage(name);
    int numPixels = copy.length;
    int numPixelsPerRow = copy.length / copy[0][1];
    int kernelRows = this.filtersMap.get(this.filter).length;

    // Iterate through every pixel
    for (int i = 1; i < numPixels; i++) {
      // Iterate through each color channel of the ith pixel
      for (int j = 0; j < 3; j++) {
        double newColor = 0;
        // Iterate through each row of the kernel
        for (int k = 0; k < kernelRows; k++) {
          int multiplicativeFactor = k - kernelRows / 2;
          int centerPixelInRowToChange = i + (multiplicativeFactor * numPixelsPerRow);
          // Iterate through each column of the given row in the kernel
          for (int l = 0; l < this.filtersMap.get(this.filter)[k].length; l++) {
            int numLeftOrRight = l - this.filtersMap.get(this.filter)[k].length / 2;
            int lastPixelInRow;

            if (i % numPixelsPerRow != 0) {
              lastPixelInRow = i + (numPixelsPerRow - (i % numPixelsPerRow));
            } else {
              lastPixelInRow = i;
            }

            if ((i + numLeftOrRight <= lastPixelInRow)
                    && !(i % numPixelsPerRow <= Math.abs(numLeftOrRight) && i % numPixelsPerRow != 0
                    && numLeftOrRight < 0)
                    && centerPixelInRowToChange + numLeftOrRight > 0
                    && centerPixelInRowToChange + numLeftOrRight < copy.length
                    && centerPixelInRowToChange > 0
                    && centerPixelInRowToChange < copy.length) {
              newColor += this.filtersMap.get(this.filter)[k][l]
                      * copy[centerPixelInRowToChange + numLeftOrRight][j];
            }
          }
        }
        if (newColor > 255) {
          newColor = 255;
        } else if (newColor < 0) {
          newColor = 0;
        }

        copy[i][j] = (int) newColor;
      }
    }
    return copy;
  }
}
