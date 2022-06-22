package controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import imageformat.BMPImageFormat;
import imageformat.ImageFormat;
import imageformat.JPGImageFormat;
import imageformat.PNGImageFormat;
import imageformat.PPMImageFormat;
import model.ImageProcessorModel;
import operations.Operation;
import view.GUIViewImpl;

/**
 * The class represents a controller implementation that works with the view.
 * Should the GUI controller implement the Features Interface?
 */

public class ImageProcessorControllerGUI implements ImageProcessorGUIController {
  private final ImageProcessorModel model;
  private final GUIViewImpl view;

  // INVARIANT: All keys contained in formatDirectory are valid file extensions
  private final Map<String, ImageFormat> formatDirectory;

  private final Features features;

  /**
   * Constructs a new ImageProcessorControllerGUI that supports the given features for the given
   * model and view.
   * @param model the model that this controller will control
   * @param view the view that this controller will control
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
    this.features.setController(this);
    this.formatDirectory = new HashMap<String, ImageFormat>();
    this.formatDirectory.put("ppm", new PPMImageFormat());
    this.formatDirectory.put("png", new PNGImageFormat());
    this.formatDirectory.put("jpg", new JPGImageFormat());
    this.formatDirectory.put("bmp", new BMPImageFormat());
  }



  // the controller should have some object that implements the Features interface

  @Override
  public void execute() throws IllegalStateException {
    this.view.addFeatures(features);
  }

//  @Override
//  public void brightenOrDarken(int input) {
//    this.model.doOperation(new BrightenOrDarken(input), "image", "image");
//    this.view.refresh(this.produceBufferedImage());
//  }
//
//  @Override
//  public void greyscale() {
//    this.model.doOperation(new ColorTransformation(ColorTransformation.Transformation.Greyscale),
//            "image", "image");
//    this.view.refresh(this.produceBufferedImage());
//  }
//
//  @Override
//  public void sepia() {
//    this.model.doOperation(new ColorTransformation(ColorTransformation.Transformation.Sepia),
//            "image", "image");
//    this.view.refresh(this.produceBufferedImage());
//  }
//
//  @Override
//  public void sharpen() {
//    this.model.doOperation(new Filter(Filter.Filters.Sharpen), "image", "image");
//    this.view.refresh(this.produceBufferedImage());
//  }
//
//  @Override
//  public void blur() {
//    this.model.doOperation(new Filter(Filter.Filters.Blur), "image", "image");
//    this.view.refresh(this.produceBufferedImage());
//  }
//
//  @Override
//  public void flipHorizontal() {
//    this.model.doOperation(new FlipHorizontal(), "image", "image");
//    this.view.refresh(this.produceBufferedImage());
//  }
//
//  @Override
//  public void flipVertical() {
//    this.model.doOperation(new FlipVertical(), "image", "image");
//    this.view.refresh(this.produceBufferedImage());
//  }
//
//  @Override
//  public void visualizeRed() {
//    this.model.doOperation(new VisualizeComponent(VisualizeComponent.Component.Red),
//            "image", "image");
//    this.view.refresh(this.produceBufferedImage());
//  }
//
//  @Override
//  public void visualizeGreen() {
//    this.model.doOperation(new VisualizeComponent(VisualizeComponent.Component.Green),
//            "image", "image");
//    this.view.refresh(this.produceBufferedImage());
//  }
//
//  @Override
//  public void visualizeBlue() {
//    this.model.doOperation(new VisualizeComponent(VisualizeComponent.Component.Blue),
//            "image", "image");
//    this.view.refresh(this.produceBufferedImage());
//  }
//
//  @Override
//  public void visualizeIntensity() {
//    this.model.doOperation(new VisualizeComponent(VisualizeComponent.Component.Intensity),
//            "image", "image");
//    this.view.refresh(this.produceBufferedImage());
//  }
//
//  @Override
//  public void visualizeValue() {
//    this.model.doOperation(new VisualizeComponent(VisualizeComponent.Component.Value),
//            "image", "image");
//    this.view.refresh(this.produceBufferedImage());
//  }
//
//  @Override
//  public void visualizeLuma() {
//    this.model.doOperation(new VisualizeComponent(VisualizeComponent.Component.Luma),
//            "image", "image");
//    this.view.refresh(this.produceBufferedImage());
//  }

  @Override
  public void load(String filePath) {
    String fileFormat = filePath.substring(filePath.lastIndexOf('.') + 1);

    // Not sure if this needs to be in a try catch so just leaving it for now
    try {
      this.model.loadImage("image", this.formatDirectory.get(fileFormat).read(filePath));
      this.view.refresh(this.produceBufferedImage());
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void save(String filePath) {
    String fileFormat = filePath.substring(filePath.lastIndexOf('.') + 1);

    this.formatDirectory.get(fileFormat).save(filePath, this.model.getImage("image"));
  }

  @Override
  public void doOperation(Operation op) {
    this.model.doOperation(op, "image", "image");
    this.view.refresh(this.produceBufferedImage());
  }

  /**
   * Produce a BufferedImage from the image in the model.
   * @return a BufferedImage representing the image in the model.
   */
  private BufferedImage produceBufferedImage() {
    int[][] imageArr = this.model.getImage("image");
    BufferedImage image = new BufferedImage(imageArr[0][0], imageArr[0][1],
            BufferedImage.TYPE_INT_ARGB);

    boolean hasOpacity = imageArr[1].length > 3;

    int curPixel = 1;
    for (int i = 0; i < imageArr[0][0]; i++) {
      for (int j = 0; j < imageArr[0][1]; j++) {
        Color color;

        if (hasOpacity) {
          color = new Color(imageArr[curPixel][0], imageArr[curPixel][1],
                  imageArr[curPixel][2], imageArr[curPixel][3]);
        } else {
          color = new Color(imageArr[curPixel][0], imageArr[curPixel][1],
                  imageArr[curPixel][2]);
        }

        image.setRGB(i, j, color.getRGB());
        curPixel++;
      }
    }

    return image;
  }
}
