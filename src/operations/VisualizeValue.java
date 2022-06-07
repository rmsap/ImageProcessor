package operations;

import model.ImageProcessorModel;

/**
 * This class represents a function object that converts an image to greyscale by visualizing its
 * value.
 */
public class VisualizeValue implements Operation {

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int [][] copy = OperationUtils.copy(model.getImage(name));

    for (int i = 1; i < copy.length; i++) {
      for (int j = 1; j < copy[i].length; j++) {
          int max = Math.max(copy[i][0], Math.max(copy[i][1], copy[i][2]));
          copy[i][j] = max;
      }
    }
    return copy;
  }
}
