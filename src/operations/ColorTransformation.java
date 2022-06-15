package operations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import model.ImageProcessorModel;

/**
 * This class represents a function object that transforms the colors of an image (e.g. give an
 * image a sepia tint).
 */
public class ColorTransformation implements Operation {

  /**
   * This enum represents possible transformations that we could use to change an image's color.
   */
  public enum Transformation { Greyscale, Sepia }

  // A map of Transformations to a Function that, given a pixel's three color channels,
  // determines what the channels should be for a given transformation.
  private final Map<Transformation, Function<int[], int[]>> transformations;

  // The transformation that we will be using on this image.
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
    this.transformations.put(Transformation.Greyscale,
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
