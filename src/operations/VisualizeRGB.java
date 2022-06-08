package operations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import model.ImageProcessorModel;

/**
 * This class represents a function object that converts an image to greyscale based on its one of
 * its color components.
 */
public class VisualizeRGB implements Operation {

  /**
   * This enum represents possible colors that we use to greyscale an image.
   */
  public enum Color {Red, Green, Blue};

  // A map of Colors to Integers so that we can get the index of red, green, or blue
  // based on the Color that we are visualizing
  private Map<Color, Integer> colorToNumber = new HashMap<Color, Integer>();
  private final Color color;

  /**
   * Construct a new VisualizeRGB object with the given color to visualize.
   * @param color the color that we want to visualize
   */
  public VisualizeRGB(Color color) {
    this.color = color;

    // Put all colors in the map and point them to the indices of the array in which that color
    // will be stored.
    this.colorToNumber.put(Color.Red, 0);
    this.colorToNumber.put(Color.Green, 1);
    this.colorToNumber.put(Color.Blue, 2);
  }

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int [][] copy = model.getImage(name);

    for (int i = 1; i < copy.length; i++) {
      Arrays.fill(copy[i], copy[i][this.colorToNumber.get(this.color)]);
    }
    return copy;
  }
}
