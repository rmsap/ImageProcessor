package model;

/**
 * Represents an abstract class of an ImageProcessorModel.
 * The class possesses methods that are to be used by current and future model implementations.
 */
public class AbstractImageProcessorModel implements ImageProcessorModel {
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
  public void doOperation(Operation op) {
    op.execute(this);
  }
}
