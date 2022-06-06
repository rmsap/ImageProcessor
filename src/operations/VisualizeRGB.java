package operations;

import model.ImageProcessorModel;
import operations.Operation;

public class VisualizeRGB implements Operation {
  private final String colorToVisualize;

  public VisualizeRGB(String colorToVisualize) {
    this.colorToVisualize = colorToVisualize;
  }

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
        if (this.colorToVisualize.equalsIgnoreCase("r")) {
          copy[i][j] = copy[i][0];
        }
        else if (this.colorToVisualize.equalsIgnoreCase("g")) {
          copy[i][j] = copy[i][1];
        }
        else if (this.colorToVisualize.equalsIgnoreCase("b")) {
          copy[i][j] = copy[i][2];
        }
      }
    }
    return copy;
  }
}
