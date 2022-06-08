package operations;

import model.ImageProcessorModel;

/**
 * This class represents a function object that converts an image to greyscale based on its blue
 * component.
 */
public class VisualizeRed implements Operation {
  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int [][] copy = model.getImage(name);

    for (int i = 1; i < copy.length; i++) {
      for (int j = 1; j < copy[i].length; j++) {
        copy[i][j] = copy[i][0];
      }
    }
    return copy;
  }
}
