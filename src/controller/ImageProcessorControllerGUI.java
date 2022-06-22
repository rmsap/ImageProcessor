package controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

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
    try{
      this.model.doOperation(op, "image", "image");
      this.view.refresh(this.produceBufferedImage());
    }
    catch(IllegalArgumentException b) {
      try{
        this.view.renderMessage("An image must be loaded to perform an operation.");
      }
      catch(IOException f) {
        // if an exception is caught then gg
        System.out.print("lmao");
      }


    }


  }

  /**
   * Produce a BufferedImage from the image in the model.
   * @return a BufferedImage representing the image in the model.
   */
  private Image produceBufferedImage() {
    int[][] image = this.model.getImage("image");

    // first convert to buffered image
    int width = image[0][0];
    int height = image[0][1];
    // for bmp have to change to rgb instead of argb or it will not save to a bmp file
    BufferedImage newImage = new BufferedImage(image[0][0], image[0][1],
            BufferedImage.TYPE_INT_RGB);
    int pixelCount = 1; // starts at index 1 since 0 just contains metaData
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        Color temp = null;
        // convert 3 values into a unified RGB value
        if (image[pixelCount].length == 4) { // if it's rgba
          temp = new Color(image[pixelCount][0], image[pixelCount][1], image[pixelCount][2],
                  image[pixelCount][3]);
        } else if (image[pixelCount].length == 3) { // if it's only rgb
          temp = new Color(image[pixelCount][0], image[pixelCount][1], image[pixelCount][2]);
        }

        int tempRGBColor = temp.getRGB();
        int tempAlphaValue = temp.getAlpha();
        // set each pixel in the buffered image to the rgb value
        newImage.setRGB(c, r, tempRGBColor);
        //          newImage.setRGB(c, r, tempAlphaValue);
        pixelCount++;
      }
    }
    return newImage;
  }
}
