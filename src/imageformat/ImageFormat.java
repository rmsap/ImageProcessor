package imageformat;

/**
 * This interface represents the methods needed to read and write to specific image formats
 * (e.g. ppm, png, jpg).
 */
public interface ImageFormat {
  /**
   * Read an image with the given file path.
   *
   * @param path the path of the image that is being loading
   * @return the image we are reading represented as a 2-dimensional array of integers
   *          (each inner array has length 3 if it is representing r, g, b, 4 if it is r, g, b, a,
   *          etc.)
   * @throws IllegalArgumentException if the file cannot be found
   *                                  or if the file is not a valid ppm file
   */
  int[][] read(String path) throws IllegalArgumentException;

  /**
   * Save the given image to the given file path.
   *
   * @param path  the path that the image should be saved to,
   *              which should include the name of the file
   * @param image the image to save represented as a 2-dimensional array of integers
   * @throws IllegalArgumentException if it either the image or path are null
   */
  void save(String path, int[][] image) throws IllegalArgumentException;
}
