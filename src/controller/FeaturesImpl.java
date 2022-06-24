package controller;

import operations.BrightenOrDarken;
import operations.ColorTransformation;
import operations.ColorTransformation.Transformation;
import operations.Downscale;
import operations.Filter;
import operations.Filter.Filters;
import operations.FlipHorizontal;
import operations.FlipVertical;
import operations.VisualizeComponent;
import operations.VisualizeComponent.Component;

/**
 * The class represents an implementation of the  Features interface. When called from the view,
 * each of this class's methods will tell the controller to mutate the model in some way or
 * save/load an image.
 */
public class FeaturesImpl implements Features {
  private ImageProcessorGUIController controller;

  @Override
  public void brightenOrDarken(int input) {
    this.controller.doOperation(new BrightenOrDarken(input));
  }

  @Override
  public void greyscale() {
    this.controller.doOperation(new ColorTransformation(Transformation.Greyscale));
  }

  @Override
  public void sepia() {
    this.controller.doOperation(new ColorTransformation(Transformation.Sepia));
  }

  @Override
  public void sharpen() {
    this.controller.doOperation(new Filter(Filters.Sharpen));
  }

  @Override
  public void blur() {
    this.controller.doOperation(new Filter(Filters.Blur));
  }

  @Override
  public void flipHorizontal() {
    this.controller.doOperation(new FlipHorizontal());
  }

  @Override
  public void flipVertical() {
    this.controller.doOperation(new FlipVertical());
  }

  @Override
  public void visualizeRed() {
    this.controller.doOperation(new VisualizeComponent(Component.Red));
  }

  @Override
  public void visualizeGreen() {
    this.controller.doOperation(new VisualizeComponent(Component.Green));
  }

  @Override
  public void visualizeBlue() {
    this.controller.doOperation(new VisualizeComponent(Component.Blue));
  }

  @Override
  public void visualizeIntensity() {
    this.controller.doOperation(new VisualizeComponent(Component.Intensity));
  }

  @Override
  public void visualizeValue() {
    this.controller.doOperation(new VisualizeComponent(Component.Value));
  }

  @Override
  public void visualizeLuma() {
    this.controller.doOperation(new VisualizeComponent(Component.Luma));
  }

  @Override
  public void downscale(int widthPercent, int heightPercent) {
    this.controller.doOperation(new Downscale(widthPercent, heightPercent));
  }

  @Override
  public void load(String filePath) {
    controller.load(filePath);
  }

  @Override
  public void save(String filePath) {
    controller.save(filePath);
  }

  @Override
  public void setController(ImageProcessorGUIController controller) {
    this.controller = controller;
  }
}
