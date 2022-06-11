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
public class ColorTransformation implements Operation {

  /**
   * This enum represents possible colors that we could use to greyscale an image. An enum is used
   * here to ensure that it is impossible to enter an invalid key into the Map created below.
   */
  public enum Transformation { Red, Green, Blue, Value, Intensity, Luma, Sepia }

  // A map of Colors to int arrays so that we can get the new values for each of the color channel
  // of the pixel
  private final Map<Transformation, Function<int[], int[]>> transformations;

  // The factor that we will be using to greyscale this image.
  private final Transformation transform;

  /**
   * Construct a new ColorTransformation object with the given transformation.
   *
   * @param transform the function by which we will be transforming the colors of this image
   */
  public ColorTransformation(Transformation transform) {
    this.transform = transform;

    transformations = new HashMap<Transformation, Function<int[], int[]>>();

    // Put all transformations in the map and point them to
    // the function that will return the correct color of a pixel.
    this.transformations.put(Transformation.Red, pixel -> new int[] {pixel[0], pixel[0], pixel[0]});
    this.transformations.put(Transformation.Green, pixel ->
            new int[] {pixel[1], pixel[1], pixel[1]});
    this.transformations.put(Transformation.Blue, pixel ->
            new int[] {pixel[2], pixel[2], pixel[2]});
    this.transformations.put(Transformation.Value,
            pixel -> {
      int max = Math.max(pixel[0], Math.max(pixel[1], pixel[2]));
      return new int[] {max, max, max};
    });
    this.transformations.put(Transformation.Intensity,
        pixel -> {
      int intensity = (pixel[0] + pixel[1] + pixel[2]) / 3;
      return new int[] {intensity, intensity, intensity};
    });
    this.transformations.put(Transformation.Luma,
        pixel -> {
      int luma = (int) (0.2126 * pixel[0] + 0.7152 * pixel[1] + 0.0722 * pixel[2]);
      return new int[] {luma, luma, luma};
    });
    this.transformations.put(Transformation.Sepia,
            pixel -> new int[] {(int) (0.393 * pixel[0] + 0.769 * pixel[1] + 0.189 * pixel[2]),
                    (int) (0.349 * pixel[0] + 0.686 * pixel[1] + 0.168 * pixel[2]),
                    (int) (0.272 * pixel[0] + 0.534 * pixel[1] + 0.131 * pixel[2])});
  }

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int[][] copy = model.getImage(name);

    for (int i = 1; i < copy.length; i++) {
      copy[i] = this.transformations.get(this.transform).apply(copy[i]).clone();
    }
    return copy;
  }
}
