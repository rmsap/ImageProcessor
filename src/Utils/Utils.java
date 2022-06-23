package Utils;

import java.awt.Image;


/**
 * This interface represents useful methods that do not fit into any one class.
 */
public interface Utils {
  /**
   * Creates an Image given a 2d array of pixels.
   * @param image representing the 2d array of pixels that hold rgb values.
   * @return an Image that has the corresponding pixels values from the given 2d array
   */
  Image createBufferedImage(int[][] image);
}
