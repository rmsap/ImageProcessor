package model;

/**
 * This class represents an ImageProcessorModel that works with PPM files.
 */
public class PPMImageProcessorModel extends AbstractImageProcessorModel {
  @Override
  public void loadImage(String path, String name) {
    directory.put(name, ImageUtil.readPPM(path));
  }
}
