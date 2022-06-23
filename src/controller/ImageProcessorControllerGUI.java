package controller;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Utils.UtilsImpl;
import imageformat.BMPImageFormat;
import imageformat.ImageFormat;
import imageformat.JPGImageFormat;
import imageformat.PNGImageFormat;
import imageformat.PPMImageFormat;
import model.ImageProcessorModel;
import operations.Operation;
import view.ImageProcessorGUIView;

/**
 * The class represents a controller implementation that works with the view.
 */

public class ImageProcessorControllerGUI implements ImageProcessorGUIController {
  private final ImageProcessorModel model;
  private final ImageProcessorGUIView view;

  // INVARIANT: All keys contained in formatDirectory are valid file extensions
  private final Map<String, ImageFormat> formatDirectory;

  private final Features features;

  /**
   * Constructs a new ImageProcessorControllerGUI that supports the given features for the given
   * model and view.
   *
   * @param model the model that this controller will control

   * @param view the view that this controller will control
   * @param features the features that this controller supports

   * @param view  the view that this controller will control

   * @throws IllegalArgumentException if any of the parameters are null
   */
  public ImageProcessorControllerGUI(ImageProcessorModel model, ImageProcessorGUIView view,
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

  @Override
  public void execute() throws IllegalStateException {
    this.view.addFeatures(features);
  }

  @Override
  public void load(String filePath) {
    String fileFormat = filePath.substring(filePath.lastIndexOf('.') + 1);

    if(this.formatDirectory.get(fileFormat) != null) {
      try {
        this.model.loadImage("image", this.formatDirectory.get(fileFormat).read(filePath));
        Image image = this.produceBufferedImage("image");
        this.view.refresh(image);
      } catch (IllegalArgumentException e) {
        try {
          this.view.renderMessage("Invalid image.");
        } catch (IOException io) {
          // This IOException can never be thrown -- it is inherited from the interface and so needs
          // to be caught, but rendering a message on the GUI will never throw an IOException.
        }
      }
    }
    else {
      try {
        this.view.renderMessage("The file loaded must be .ppm, .png, .bmp, or .jpg");
      } catch (IOException f) {
        // This IOException can never be thrown -- it is inherited from the interface and so needs
        // to be caught, but rendering a message on the GUI will never throw an IOException.
      }
    }

  }

  @Override
  public void save(String filePath) {
    String fileFormat = filePath.substring(filePath.lastIndexOf('.') + 1);

    if (this.formatDirectory.get(fileFormat) != null) {
      try {
        this.formatDirectory.get(fileFormat).save(filePath, this.model.getImage("image"));
      } catch (IllegalArgumentException i) {
        try {
          this.view.renderMessage("An image must be loaded before saving.");
        } catch (IOException f) {
          // This IOException can never be thrown -- it is inherited from the interface and so needs
          // to be caught, but rendering a message on the GUI will never throw an IOException.
        }
      }
    }
    else {
      try {
        this.view.renderMessage("Image must be saved as either .jpg, .ppm, .png, or .bmp");
      } catch (IOException f) {
        // This IOException can never be thrown -- it is inherited from the interface and so needs
        // to be caught, but rendering a message on the GUI will never throw an IOException.
      }
    }
  }

  @Override
  public void doOperation(Operation op) {
    try {
      this.model.doOperation(op, "image", "image");
      Image image = this.produceBufferedImage("image");
      this.view.refresh(image);
    } catch (IllegalArgumentException b) {
      try {
        this.view.renderMessage("An image must be loaded to perform an operation.");
      } catch (IOException f) {
        // This IOException can never be thrown -- it is inherited from the interface and so needs
        // to be caught, but rendering a message on the GUI will never throw an IOException.
      }
    }
  }

  /**
   * Produce a BufferedImage from the image in the model.
   *
   * @param imageName the name of the image to produce a buffered image of
   * @return a BufferedImage representing the image in the model.
   */
  private Image produceBufferedImage(String imageName) {
    return new UtilsImpl().createBufferedImage(this.model.getImage(imageName));
  }
}
