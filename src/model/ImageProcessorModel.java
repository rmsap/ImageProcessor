package model;

import operations.Operation;

/**
 * This interface represents a basic ImageProcessorModel and lays out all methods that any
 * image processor must be able to execute.
 */
public interface ImageProcessorModel {
  /**
   * Load an image with the given file name.
   * @param path the path of the image that is being loading
   * @param name the name that this image will henceforth be referred to as
   */
  void loadImage(String path, String name);

  /**
   * Save the image with the given file name.
   * @param path the path that the image should be saved to,
   *             which should include the name of the file
   * @param imageName the name of the image to save
   */
  void saveImage(String path, String imageName);

  /**
   * Get the image with the given name and return it.
   * @param name the name of the image we want to get
   * @return the image with the given name
   */
  int[][] getImage(String name);

  /**
   * Execute the given operation on the Image with the given name in this ImageProcessorModel.
   * @param op operation to execute
   * @param name the name of the image that we are executing the operation on
   * @param dest the name of the new image that is being produced
   */
  void doOperation(Operation op, String name, String dest);
}
