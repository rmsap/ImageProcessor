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
  public ImageProcessorModelImpl() {
    super();
  }

  @Override
  public void loadImage(String path, String name, ImageFormat format)
          throws IllegalArgumentException {
    if(path == null || name == null || format == null) {
      throw new IllegalArgumentException("None of the parameters can be null");
    }
    directory.put(name, format.read(path));
  }

  @Override
  public void saveImage(String path, String imageName, ImageFormat format)
          throws IllegalArgumentException {
    if(path == null || imageName == null || format == null) {
      throw new IllegalArgumentException("None of the parameters can be null");
    }
    format.save(path, this.directory.get(imageName));
  }
}
