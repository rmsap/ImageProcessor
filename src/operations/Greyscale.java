package operations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import model.ImageProcessorModel;

/**
 * This class represents a function object that converts an image to greyscale based on its one of
 * its color components.
 */
public class Greyscale implements Operation {

  /**
   * This enum represents possible colors that we could use to greyscale an image. An enum is used
   * here to ensure that it is impossible to enter an invalid key into the Map created below.
   */
  public enum GreyscaleFactor { Red, Green, Blue, Value, Intensity, Luma }

  // A map of Colors to Integers so that we can get the index of red, green, or blue
  // based on the Color that we are visualizing
  private final Map<GreyscaleFactor, Function<int[], Integer>> greyscaleFactors;

  // The factor that we will be using to greyscale this image.
  private final GreyscaleFactor gf;

  /**
   * Construct a new VisualizeRGB object with the given color to visualize.
   *
   * @param gf the factor by which we will be converting to greyscale
   */
  public Greyscale(GreyscaleFactor gf) {
    this.gf = gf;

    greyscaleFactors = new HashMap<GreyscaleFactor, Function<int[], Integer>>();
    // Put all greyscale factors in the map and point them to
    // the function that will return the correct color of a pixel.
    this.greyscaleFactors.put(GreyscaleFactor.Red, pixel -> pixel[0]);
    this.greyscaleFactors.put(GreyscaleFactor.Green, pixel -> pixel[1]);
    this.greyscaleFactors.put(GreyscaleFactor.Blue, pixel -> pixel[2]);
    this.greyscaleFactors.put(GreyscaleFactor.Value,
        pixel -> Math.max(pixel[0], Math.max(pixel[1], pixel[2])));
    this.greyscaleFactors.put(GreyscaleFactor.Intensity,
        pixel -> (pixel[0] + pixel[1] + pixel[2]) / 3);
    this.greyscaleFactors.put(GreyscaleFactor.Luma,
        pixel -> (int) (0.2126 * pixel[0] + 0.7152 * pixel[1] + 0.0722 * pixel[2]));
  }

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int[][] copy = model.getImage(name);

    for (int i = 1; i < copy.length; i++) {
      Arrays.fill(copy[i], this.greyscaleFactors.get(this.gf).apply(copy[i]));
    }
    return copy;
  }
}
