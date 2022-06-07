package operations;

import model.ImageProcessorModel;

/**
 * This class represents a function object that converts an image to greyscale based on its blue
 * component.
 */
public class VisualizeGreen implements Operation {

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int [][] copy = OperationUtils.copy(model.getImage(name));

    for (int i = 1; i < copy.length; i++) {
      for (int j = 0; j < copy[i].length; j++) {
        copy[i][j] = copy[i][1];
      }
    }
    return copy;
  }
}
