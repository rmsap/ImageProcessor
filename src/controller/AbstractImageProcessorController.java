package controller;

import java.util.HashMap;
import java.util.Map;

import imageformat.BMPImageFormat;
import imageformat.ImageFormat;
import imageformat.JPGImageFormat;
import imageformat.PNGImageFormat;
import imageformat.PPMImageFormat;
import model.ImageProcessorModel;
import view.ImageProcessorView;

/**
 * This class represents an abstract implementation of an ImageProcessorController that contains
 * methods and fields common to all controllers.
 */
public abstract class AbstractImageProcessorController implements ImageProcessorController {
  protected final ImageProcessorModel model;
  protected final ImageProcessorView view;

  // INVARIANT: All keys contained in formatDirectory are valid file extensions
  protected final Map<String, ImageFormat> formatDirectory;

  /**
   * Constructor that instantiates fields common to all ImageProcessorControllers.
   *
   * @param model representing an ImageProcessorModel.
   * @param view  representing an ImageProcessorView.
   * @throws IllegalArgumentException if any parameters are null
   */
  public AbstractImageProcessorController(ImageProcessorModel model, ImageProcessorView view)
          throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("model, view, and readable object cannot be null");
    }
    this.model = model;
    this.view = view;

    // Instantiate the format directory and load all supported image formats into it
    formatDirectory = new HashMap<String, ImageFormat>();
    formatDirectory.put("ppm", new PPMImageFormat());
    formatDirectory.put("jpg", new JPGImageFormat());
    formatDirectory.put("png", new PNGImageFormat());
    formatDirectory.put("bmp", new BMPImageFormat());
  }

  @Override
  public abstract void execute() throws IllegalStateException;
}
