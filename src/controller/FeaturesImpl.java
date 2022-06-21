package controller;

import operations.BrightenOrDarken;
import operations.ColorTransformation;
import operations.ColorTransformation.Transformation;
import operations.Filter;
import operations.Filter.Filters;
import operations.FlipHorizontal;
import operations.FlipVertical;
import operations.Operation;
import operations.VisualizeComponent;
import operations.VisualizeComponent.Component;

public class FeaturesImpl implements Features {
  private ImageProcessorController controller;

  @Override
  public Operation brightenOrDarken(int input) {
    return new BrightenOrDarken(input);
  }

  @Override
  public Operation greyscale() {
    return new ColorTransformation(Transformation.Greyscale);
  }

  @Override
  public Operation sepia() {
    return new ColorTransformation(Transformation.Sepia);
  }

  @Override
  public Operation sharpen() {
    return new Filter(Filters.Sharpen);
  }

  @Override
  public Operation blur() {
    return new Filter(Filters.Blur);
  }

  @Override
  public Operation flipHorizontal() {
    return new FlipHorizontal();
  }

  @Override
  public Operation flipVertical() {
    return new FlipVertical();
  }

  @Override
  public Operation visualizeRed() {
    return new VisualizeComponent(Component.Red);
  }

  @Override
  public Operation visualizeGreen() {
    return new VisualizeComponent(Component.Green);
  }

  @Override
  public Operation visualizeBlue() {
    return new VisualizeComponent(Component.Blue);
  }

  @Override
  public Operation visualizeIntensity() {
    return new VisualizeComponent(Component.Intensity);
  }

  @Override
  public Operation visualizeValue() {
    return new VisualizeComponent(Component.Value);
  }

  @Override
  public Operation visualizeLuma() {
    return new VisualizeComponent(Component.Luma);
  }

  @Override
  public void load(String filePath) {
    if (this.controller instanceof ImageProcessorGUIController) {
      ImageProcessorGUIController c = (ImageProcessorGUIController) this.controller;
      c.load(filePath);
    }
  }

  @Override
  public void save(String filePath) {
    if (this.controller instanceof ImageProcessorGUIController) {
      ImageProcessorGUIController c = (ImageProcessorGUIController) this.controller;
      c.save(filePath);
    }
  }

  @Override
  public void setController(ImageProcessorController controller) {
    this.controller = controller;
  }
}
