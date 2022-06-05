package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

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
  public abstract void loadImage(String path, String name);

  @Override
  public void saveImage(String path, String imageName) {
    File imageFile = new File(path);
    int[][] image = this.directory.get(imageName);

    try {
      imageFile.createNewFile();
    } catch (IOException e) {
      throw new IllegalArgumentException("File already exists.");
    }

    try {
      FileWriter writer = new FileWriter(path);

      // Write the PPM header into this file
      writer.write("P3 ");

      // Write all pixels from this image into the file
      for (int[] pixel : image) {
        writer.write(pixel[0] + " " + pixel[1] + " " + pixel[2] + "\n");
      }

      writer.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to write to file.");
    }
  }

  @Override
  public int[][] getImage(String name) {
    if (this.directory.get(name) == null) {
      throw new IllegalArgumentException();
    }
    else {
      return this.directory.get(name);
    }
  }

  @Override
  public void doOperation(Operation op, String name, String dest) {
    this.directory.put(name, op.execute(this, name));
  }
}
