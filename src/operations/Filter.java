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
  public enum Filters { Blur, Sharpen };

  // The filter that we will apply to this image
  private final Filters filter;

  private final Map<Filters, double[][]> filtersMap;

  Filter(Filters filter) {
    this.filter = filter;

    this.filtersMap = new HashMap<Filters, double[][]>();
    this.filtersMap.put(Filters.Blur, new double[][] {
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}});
  }

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int[][] copy = model.getImage(name);
    int numPixels = copy.length;
    int numPixelsPerRow = copy.length / copy[0][0];

    // Iterate through every pixel
    for (int i = 1; i < numPixels; i++) {
      // Iterate through each color channel of the ith pixel
      for (int j = 0; j < copy[i].length; j++) {
        int sum = 0;
        // Iterate through each element of the kernel and apply its transformation
        for (int k = 0; k < filtersMap.get(this.filter).length; k++) {
          // If we are going above the center row of the image
          if (k < filtersMap.get(this.filter).length + 1) {
            int numRowsAboveCenter = filtersMap.get(this.filter).length / 2 - k;
//                      - (filtersMap.get(this.filter).length
//                      - filtersMap.get(this.filter).length / 2 - 1
//                      - (filtersMap.get(this.filter).length / 2 - k));
            // If the number of rows above that we need to go exists
            if (i - numPixelsPerRow * numRowsAboveCenter > 0) {
              for (int l = 0; l < filtersMap.get(this.filter)[k].length; l++) {
                // If we are going to the left of the center
                if (l < filtersMap.get(this.filter)[k].length / 2 + 1) {
                  int numColsLeftOfCenter = filtersMap.get(this.filter)[k].length / 2 - l;
                  // If the column we are looking for exists
                  if (i - numColsLeftOfCenter >= 1) {
                    sum += copy[i - l][j] * filtersMap.get(this.filter)[k][l];
                  }
                }
              }
            }

          }
          // If we are going below the center row of the image
          else if (k > filtersMap.get(this.filter).length + 1) {

          }
          // If we are in the center row of the image
          else {

          }
          copy[i][j] = Math.min(sum, 255);
        }
      }
    }

    return new int[0][];
  }
}
