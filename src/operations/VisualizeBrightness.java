package operations;

import java.util.Arrays;

import model.ImageProcessorModel;

/**
 * This class represents a function object that converts an image to greyscale by visualizing its
 * brightness (which is represented by a pixel's luma, intensity, or value).
 */
public class VisualizeBrightness implements Operation {

  /**
   * This enum represents the different possible measures of brightness that we could use to
   * greyscale the image.
   */
  public enum BrightnessMeasure {Value, Intensity, Luma};

  // The brightness measure that we are going to use to greyscale the given image.
  private final BrightnessMeasure measure;

  /**
   * Construct a new VisualizeBrightness with the given BrightnessMeasure.
   * @param measure the measure of brightness by which we are greyscaling
   */
  public VisualizeBrightness(BrightnessMeasure measure) {
    this.measure = measure;
  }

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int[][] copy = model.getImage(name);

    for (int i = 1; i < copy.length; i++) {
      Arrays.fill(copy[i], Compute.compute(measure, copy[i]));
    }
    return copy;
  }

  /**
   * This class represents a function object capable of computing the
   * brightness measure by which we are greyscaling the image.
   */
  private static class Compute {
    /**
     * Greyscale the given pixel by the given BrightnessMeasure.
     * @param measure the BrightnessMeasure by which we are greyscaling
     * @param pixel the pixel that we are greyscaling
     * @return the greyscaled pixel
     * @throws IllegalArgumentException if measure is not a
     *                                  valid BrightnessMeasure (i.e. it is null)
     */
    private static int compute(BrightnessMeasure measure, int[] pixel)
            throws IllegalArgumentException {
      switch (measure) {
        case Intensity:
          return (pixel[0] + pixel[1] + pixel[2]) / 3;
        case Value:
          return Math.max(pixel[0], Math.max(pixel[1], pixel[2]));
        case Luma:
          return (int) (0.2126 * pixel[0] + 0.7152 * pixel[1] + 0.0722 * pixel[2]);
        default:
          throw new IllegalArgumentException("Invalid brightness measure.");
      }
    }
  }
}
