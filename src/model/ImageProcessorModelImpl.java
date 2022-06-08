package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import imageFormat.ImageFormat;

/**
 * This class represents an ImageProcessorModel implementation.
 */
public class ImageProcessorModelImpl extends AbstractImageProcessorModel {
  /**
   * Construct a new ImageProcessorModelImpl.
   */
  public ImageProcessorModelImpl() {
    super();
  }

  @Override
  public void loadImage(String name, int[][] image)
          throws IllegalArgumentException {
    if(name == null || image == null) {
      throw new IllegalArgumentException("None of the parameters can be null");
    }
    directory.put(name, image);
  }
}
