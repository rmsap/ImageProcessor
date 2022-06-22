package Utils;

import java.awt.*;


/**
 * Represents the Utils interface that has methods that can be applied to by other classes.
 */
public interface Utils {
  /**
   * Creates a BufferedImage given a 2d array of pixels.
   * @param image representing the 2d array of pixels that hold rgb values.
   * @return a BufferedImage that has the corresponding pixels values from the given 2d array
   */
  Image createBufferedImage(int[][] image);
}
