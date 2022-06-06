package operations;

import model.ImageProcessorModel;
import operations.Operation;

public class VisualizeBrightness implements Operation {
  private final String channelToVisualize;

  public VisualizeBrightness(String channelToVisualize) {
    this.channelToVisualize = channelToVisualize;
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
        if (this.channelToVisualize.equalsIgnoreCase("v")) {
          int max = Math.max(copy[i][0], Math.max(copy[i][1], copy[i][2]));
          copy[i][j] = max;
        }
        else if (this.channelToVisualize.equalsIgnoreCase("i")) {
          int intensity = (copy[i][0] + copy[i][1] + copy[i][2]) / 3;
          copy[i][j] = intensity;
        }
        else if (this.channelToVisualize.equalsIgnoreCase("l")) {
          double luma = 0.2126 * copy[i][0] + 0.7152 * copy[i][1] + 0.0722 * copy[i][2];
          copy[i][j] = (int) luma;
        }
      }
    }

    return copy;
  }
}
