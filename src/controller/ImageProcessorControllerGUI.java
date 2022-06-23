package controller;

import java.awt.Image;
import java.io.IOException;

import Utils.UtilsImpl;
import model.ImageProcessorModel;
import operations.Operation;
import view.ImageProcessorGUIView;
import view.ImageProcessorView;

/**
 * This class represents a controller that works with a GUI-based view.
 */
public class ImageProcessorControllerGUI extends AbstractImageProcessorController
        implements ImageProcessorGUIController {
  private final Features features;

  /**
   * Constructs a new ImageProcessorControllerGUI that supports the given features for the given
   * model and view.
   *
   * @param model the model that this controller will control

   * @param view the view that this controller will control
   * @param features the features that this controller supports

   * @param view  the view that this controller will control

   * @throws IllegalArgumentException if any of the parameters are null or if the view is not a GUI
   *                                  view
   */
  public ImageProcessorControllerGUI(ImageProcessorModel model, ImageProcessorView view,
                                     Features features) throws IllegalArgumentException {
    super(model, view);

    if (!(view instanceof ImageProcessorGUIView)) {
      throw new IllegalArgumentException("View must be a GUI view.");
    }

    if (features == null) {
      throw new IllegalArgumentException("None of the parameters can be null.");
    }
    this.features = features;
    this.features.setController(this);
  }

  @Override
  public void execute() {
    // Note that we can cast without using instanceof because the controller could not have been
    // constructed if the view wasn't an ImageProcessorGUIView.
    ((ImageProcessorGUIView) this.view).addFeatures(features);
  }

  @Override
  public void load(String filePath) {
    String fileFormat = filePath.substring(filePath.lastIndexOf('.') + 1);

    if(this.formatDirectory.get(fileFormat) != null) {
      try {
        this.model.loadImage("image", this.formatDirectory.get(fileFormat).read(filePath));
        Image image = this.produceBufferedImage("image");
        ((ImageProcessorGUIView) this.view).refresh(image);
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
      ((ImageProcessorGUIView) this.view).refresh(image);
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
