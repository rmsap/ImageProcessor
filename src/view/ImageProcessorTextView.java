package view;

import model.ImageProcessorModel;
import model.ImageProcessorViewModel;

/**
 * An implementation of a view for Image Processor.
 * This implementation uses a text-based view.
 * Only the texts that show you which commands to use are displayed.
 * The ppm image is not displayed, only saved to the directory and as a ppm file.
 */
public class ImageProcessorTextView extends AbstractImageProcessorView {

  /**
   * Default constructor for the view of Image Processor.
   *
   * @param model representing the ImageProcessorModel.
   * @throws IllegalArgumentException if the argument is null.
   */
  public ImageProcessorTextView(ImageProcessorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Parameter cannot be null");
    }
    this.model = model;
    this.appendable = System.out;
  }


  /**
   * Constructor for the view of Image Processor that takes in a model and an Appendable object.
   *
   * @param model      representing an ImageProcessorModel.
   * @param appendable representing an Appendable object.
   * @throws IllegalArgumentException if any of the arguments are null
   */
  public ImageProcessorTextView(ImageProcessorViewModel model, Appendable appendable)
          throws IllegalArgumentException {
    if (model == null || appendable == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    this.model = model;
    this.appendable = appendable;
  }

}
