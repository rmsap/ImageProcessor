package model;

import java.io.FileNotFoundException;

import imageFormat.ImageFormat;
import operations.Operation;

/**
 * This interface represents a basic ImageProcessorModel and lays out all methods that any
 * image processor must be able to execute.
 */
public interface ImageProcessorModel {
  /**
   * Load an image with the given file path.
   * @param name the name that this image will henceforth be referred to as
   * @param image the image we are going to load into this model, represented as an array of
   *              pixels (which are 3 integers representing red, green, and blue)
   */
  void loadImage(String name, int[][] image);

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
