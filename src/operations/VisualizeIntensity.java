package operations;

import model.ImageProcessorModel;

/**
 * This class represents a function object that converts an image to greyscale by visualizing its
 * intensity.
 */
public class VisualizeIntensity implements Operation {
  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int [][] copy = new int [model.getImage(name).length][model.getImage(name)[0].length];

    for(int r = 0; r < copy.length; r++) {
      for(int c = 0; c < copy[r].length; c++) {
        copy[r][c] = model.getImage(name)[r][c];
      }
    }

    for (int i = 1; i < copy.length; i++) {
      for (int j = 1; j < copy[i].length; j++) {
        int intensity = (copy[i][0] + copy[i][1] + copy[i][2]) / 3;
        copy[i][j] = intensity;
      }
    }
    return copy;
  }
}