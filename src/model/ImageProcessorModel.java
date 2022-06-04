package model;

import java.awt.image.BufferedImage;

/**
 * This interface represents a basic ImageProcessorModel and lays out all methods that any
 * image processor must be able to execute.
 */
public interface ImageProcessorModel {
  /**
   * Load an image with the given file name.
   * @param fileName the name of the file to load
   */
  void loadImage(String fileName);

  /**
   * Produce an image that visualizes the individual RGB
   * components of the image in this ImageProcessorModel.
   * @return a 2-Dimensional array of floats representing all RGB values in the image representing
   *         the individual RGB components of this image
   */
  float[][] visualizeRGB();

  /**
   * Produce an image that visualizes the brightness
   * of the image in this ImageProcessorModel.
   * @return a 2-Dimensional array of floats representing all pixels in the image visualizing the
   *         brightness of the image in this ImageProcessorModel
   */
  float[][] visualizeBrightness();

  /**
   * Save the image with the given file name.
   * @param fileName the name of the file to save
   */
  void saveImage(String fileName);

  /**
   * Get the image with the given name and return it.
   * @param name the name of the image we want to get
   * @return the image with the given name
   */
  BufferedImage getImage(String name);

  /**
   * Execute the given operation on this ImageProcessorModel.
   * @param op operation to execute
   */
  void doOperation(Operation op);
}
