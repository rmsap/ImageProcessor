package model;

import java.io.FileNotFoundException;
import java.util.HashMap;

import imageFormat.ImageFormat;
import operations.Operation;

/**
 * Represents an abstract class of an ImageProcessorModel.
 * The class possesses methods that are to be used by current and future model implementations.
 */
public abstract class AbstractImageProcessorModel implements ImageProcessorModel {

  // each image has an attached String name
  // the hashmap contains images that can be accessed via their names
  protected HashMap<String, int[][]> directory;

  public AbstractImageProcessorModel() {
    this.directory = new HashMap<String, int[][]>();
  }

  @Override
  public abstract void loadImage(String name, int[][] image)
          throws IllegalArgumentException;

  @Override
  public abstract void saveImage(String path, String imageName, ImageFormat format)
          throws IllegalArgumentException;

  @Override
  public int[][] getImage(String name) throws IllegalArgumentException {
    if (this.directory.get(name) == null) {
      throw new IllegalArgumentException("Image does not exist in the directory.");
    }
    else {
      return this.directory.get(name);
    }
  }

  @Override
  public void doOperation(Operation op, String name, String dest) throws IllegalArgumentException{
    if(op == null || name == null || dest == null) {
      throw new IllegalArgumentException("None of the parameters can be null");
    }
    this.directory.put(dest, op.execute(this, name));
  }
}
