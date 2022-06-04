package model;

/**
 * This class represents an Operation that brightens or darkens an image by its scale.
 */
public class BrightenOrDarken implements Operation {
  int scale;

  /**
   * Construct a new BrightenOrDarken Operation with the given scale.
   * @param scale the scale of this BrightenOrDarken
   */
  public BrightenOrDarken(int scale) {
    this.scale = scale;
  }

  @Override
  public void execute(ImageProcessorModel model) {


  }
}
