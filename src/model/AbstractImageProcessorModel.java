package model;

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
  HashMap<String,BufferedImage> directory;

  // contains all the pixels of an image
  // each array has a size of 3 to hold the rgb value for a single pixel
  List<int [] > allPixels;

  @Override
  public void loadImage(String fileName) {

  }

  @Override
  public float[][] visualizeRGB() {
    return new float[0][];
  }

  @Override
  public float[][] visualizeBrightness() {
    return new float[0][];
  }

  @Override
  public void saveImage(String fileName) {

  }

  @Override
  public BufferedImage getImage(String name) {
    return null;
  }

  @Override
  public void doOperation(Operation op) {
    op.execute(this);
  }
}
