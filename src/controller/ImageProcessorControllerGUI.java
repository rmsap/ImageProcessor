package controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import imageformat.ImageFormat;
import model.ImageProcessorModel;
import operations.BrightenOrDarken;
import operations.ColorTransformation;
import operations.Filter;
import operations.FlipHorizontal;
import operations.FlipVertical;
import operations.VisualizeComponent;
import view.GUIViewImpl;

/**
 * The class represents a controller implementation that works with the view.
 * Should the GUI controller implement the Features Interface?
 */
<<<<<<< HEAD
public class ImageProcessorControllerGUI implements ImageProcessorController, Features {
  private final ImageProcessorModel model;
  private final GUIViewImpl view;
  private final Features features;

  // INVARIANT: All keys contained in formatDirectory are valid file extensions
  private final Map<String, ImageFormat> formatDirectory;

  /**
   * Constructs a new ImageProcessorControllerGUI that supports the given features for the given
   * model and view.
   * @param model the model that this controller will control
   * @param view the view that this controller will control
   * @param features the features that this controller will support
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public ImageProcessorControllerGUI(GUIViewImpl view, ImageProcessorModel model,
                                     Features features) throws IllegalArgumentException {
    if (model == null || view == null || features == null) {
      throw new IllegalArgumentException("None of the parameters can be null.");
    }
    this.model = model;
    this.view = view;
    this.features = features;
    this.formatDirectory = new HashMap<String, ImageFormat>();
  }

=======
public class ImageProcessorControllerGUI implements ImageProcessorController{
  // the controller should have some object that implements the Features interface
>>>>>>> 322d272f90315fe6793c0b4fb2829e8a09fca6ba
  @Override
  public void execute() throws IllegalStateException {
    this.view.addFeatures(this.features);
  }

  @Override
  public void brightenOrDarken(int input) {
    this.model.doOperation(new BrightenOrDarken(input), "image", "image");
    this.view.refresh(this.produceBufferedImage());
  }

  @Override
  public void greyscale() {
    this.model.doOperation(new ColorTransformation(ColorTransformation.Transformation.Greyscale),
            "image", "image");
    this.view.refresh(this.produceBufferedImage());
  }

  @Override
  public void sepia() {
    this.model.doOperation(new ColorTransformation(ColorTransformation.Transformation.Sepia),
            "image", "image");
    this.view.refresh(this.produceBufferedImage());
  }

  @Override
  public void sharpen() {
    this.model.doOperation(new Filter(Filter.Filters.Sharpen), "image", "image");
    this.view.refresh(this.produceBufferedImage());
  }

  @Override
  public void blur() {
    this.model.doOperation(new Filter(Filter.Filters.Blur), "image", "image");
    this.view.refresh(this.produceBufferedImage());
  }

  @Override
  public void flipHorizontal() {
    this.model.doOperation(new FlipHorizontal(), "image", "image");
    this.view.refresh(this.produceBufferedImage());
  }

  @Override
  public void flipVertical() {
    this.model.doOperation(new FlipVertical(), "image", "image");
    this.view.refresh(this.produceBufferedImage());
  }

  @Override
  public void visualizeRed() {
    this.model.doOperation(new VisualizeComponent(VisualizeComponent.Component.Red),
            "image", "image");
    this.view.refresh(this.produceBufferedImage());
  }

  @Override
  public void visualizeGreen() {
    this.model.doOperation(new VisualizeComponent(VisualizeComponent.Component.Green),
            "image", "image");
    this.view.refresh(this.produceBufferedImage());
  }

  @Override
  public void visualizeBlue() {
    this.model.doOperation(new VisualizeComponent(VisualizeComponent.Component.Blue),
            "image", "image");
    this.view.refresh(this.produceBufferedImage());
  }

  @Override
  public void visualizeIntensity() {
    this.model.doOperation(new VisualizeComponent(VisualizeComponent.Component.Intensity),
            "image", "image");
    this.view.refresh(this.produceBufferedImage());
  }

  @Override
  public void visualizeValue() {
    this.model.doOperation(new VisualizeComponent(VisualizeComponent.Component.Value),
            "image", "image");
    this.view.refresh(this.produceBufferedImage());
  }

  @Override
  public void visualizeLuma() {
    this.model.doOperation(new VisualizeComponent(VisualizeComponent.Component.Luma),
            "image", "image");
    this.view.refresh(this.produceBufferedImage());
  }

  @Override
  public void load(String filePath) {
    String fileFormat = filePath.substring(filePath.lastIndexOf('.') + 1);

    // Not sure if this needs to be in a try catch so just leaving it for now
    try {
      this.model.loadImage(filePath, this.formatDirectory.get(fileFormat).read("image"));
      this.view.refresh();
    } catch (IllegalArgumentException e) {

    }
  }

  @Override
  public void save() {

  }

  /**
   * Produce a BufferedImage from the image in the model.
   * @return a BufferedImage representing the image in the model.
   */
  private BufferedImage produceBufferedImage() {
    int[][] imageArr = this.model.getImage("image");
    BufferedImage image = new BufferedImage(imageArr[0][0], imageArr[0][1],
            BufferedImage.TYPE_INT_ARGB);

    boolean hasOpacity = imageArr.length > 3;

    for (int i = 1; i < imageArr.length; i++) {
        Color color;

      if (hasOpacity) {
        color = new Color(imageArr[i][0], imageArr[i][1], imageArr[i][2], imageArr[i][3]);
      }
      else {
        color = new Color(imageArr[i][0], imageArr[i][1], imageArr[i][2], 255);
      }

      image.setRGB(i / imageArr[0][0], i % imageArr[0][0] - 1, color.getRGB());
    }

    return image;
  }
}
