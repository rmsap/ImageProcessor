package operations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import model.ImageProcessorModel;

/**
 * This class represents a function object that visualizes a component of an image.
 * Components come from either its colors or its brightness.
 */
public class VisualizeComponent implements Operation {

  /**
   * This enum represents possible components that we could visualize in an image. An enum is used
   * here to ensure that it is impossible to enter an invalid key into the Map created below.
   */
  public enum Component { Red, Green, Blue, Value, Intensity, Luma }

  // A map of Components to Integers so that we can get
  // value of the component that we are visualizing
  private final Map<Component, Function<int[], Integer>> components;

  // The factor that we will be using to greyscale this image.
  private final Component c;

  /**
   * Construct a new VisualizeComponent object with the given color to visualize.
   *
   * @param c the component that we are visualizing
   */
  public VisualizeComponent(Component c) {
    this.c = c;

    components = new HashMap<Component, Function<int[], Integer>>();

    // Put all greyscale factors in the map and point them to
    // the function that will return the correct color of a pixel.
    this.components.put(Component.Red, pixel -> pixel[0]);
    this.components.put(Component.Green, pixel -> pixel[1]);
    this.components.put(Component.Blue, pixel -> pixel[2]);
    this.components.put(Component.Value,
        pixel -> Math.max(pixel[0], Math.max(pixel[1], pixel[2])));
    this.components.put(Component.Intensity,
        pixel -> (pixel[0] + pixel[1] + pixel[2]) / 3);
    this.components.put(Component.Luma,
        pixel -> (int) (0.2126 * pixel[0] + 0.7152 * pixel[1] + 0.0722 * pixel[2]));
  }

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int[][] copy = model.getImage(name);

    for (int i = 1; i < copy.length; i++) {
      Arrays.fill(copy[i], this.components.get(this.c).apply(copy[i]));
    }
    return copy;
  }
}