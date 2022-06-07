package operations;

import model.ImageProcessorModel;

/**
 * This class represents a function object that converts an image to greyscale by visualizing its
 * luma.
 */
public class VisualizeLuma implements Operation {
  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int [][] copy = OperationUtils.copy(model.getImage(name));

    for (int i = 1; i < copy.length; i++) {
      for (int j = 1; j < copy[i].length; j++) {
        double luma = 0.2126 * copy[i][0] + 0.7152 * copy[i][1] + 0.0722 * copy[i][2];
        copy[i][j] = (int) luma;
      }
    }
    return copy;
  }
}
