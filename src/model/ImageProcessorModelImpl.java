package model;

import java.util.HashMap;
import java.util.Map;

import operations.Operation;

/**
 * This class represents an ImageProcessorModel implementation. It contains a directory of images
 * and has the ability to load images into the directory, get copies of the image, and operate on
 * the images.
 */
public class ImageProcessorModelImpl implements ImageProcessorModel {
  // Images are represented by the 2-dimensional array of integers in the map. Each row
  // represents a single pixel and the singular column contains every pixel.
  // The Map points the name of an image (as input by the user) to each image.
  private final Map<String, int[][]> directory;

  /**
   * Construct a new ImageProcessorModelImpl by instantiating its directory.
   */
  public ImageProcessorModelImpl() {
    this.directory = new HashMap<String, int[][]>();
  }

  @Override
  public void loadImage(String name, int[][] image) throws IllegalArgumentException {
    if (name == null || image == null) {
      throw new IllegalArgumentException("Neither the name nor the image can be null.");
    }
    directory.put(name, image);
  }

  @Override
  public int[][] getImage(String name) throws IllegalArgumentException {
    if (this.directory.get(name) == null) {
      throw new IllegalArgumentException("Image does not exist in the directory.");
    } else {
      int[][] image = this.directory.get(name);
      int[][] copy = new int[image.length][image[0].length];

      for (int i = 0; i < image.length; i++) {
        for (int j = 0; j < image[i].length; j++) {
          copy[i][j] = image[i][j];
        }
      }
      return copy;
    }
  }

  @Override
  public void doOperation(Operation op, String name, String dest) throws IllegalArgumentException {
    if (op == null || name == null || dest == null) {
      throw new IllegalArgumentException("Cannot perform an operation if the operation, name, or "
              + "destination name are null");
    }
    this.directory.put(dest, op.execute(this, name));
  }
}
