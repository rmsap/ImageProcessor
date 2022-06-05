package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

/**
 * Represents an abstract class of an ImageProcessorModel.
 * The class possesses methods that are to be used by current and future model implementations.
 */
public class AbstractImageProcessorModel implements ImageProcessorModel {

  // each image has an attached String name
  // the hashmap contains images that can be accessed via their names
  HashMap<String, Image> directory;

  // contains all the pixels of an image
  // each array has a size of 3 to hold the rgb value for a single pixel
  List<int [] > allPixels;

  @Override
  public void loadImage(String path, String name) {

  }

  @Override
  public void visualizeRGB(String dest) {

  }

  @Override
  public void visualizeBrightness(String dest) {

  }

  @Override
  public void saveImage(String path, String imageName) {

  }

  @Override
  public Image getImage(String name) {
    if (this.directory.get(name) == null) {
      throw new IllegalArgumentException();
    }
    else {
      return this.directory.get(name);
    }
  }

  @Override
  public void doOperation(Operation op, String name, String dest) {
    op.execute(this, name);
  }
}
