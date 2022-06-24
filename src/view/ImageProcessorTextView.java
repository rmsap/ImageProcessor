package view;


import model.ImageProcessorViewModel;

/**
 * An implementation of a view for Image Processor.
 * This implementation uses a text-based view.
 * Only the texts that show you which commands to use are displayed.
 * The ppm image is not displayed, only saved to the directory and as a ppm file.
 */
public class ImageProcessorTextView extends AbstractImageProcessorView {

  /**
   * Default constructor for the view of Image Processor. Its Appendable will be System.out.
   *
   * @param model representing the ImageProcessorModel.
   * @throws IllegalArgumentException if the argument is null.
   */
  public ImageProcessorTextView(ImageProcessorViewModel model) throws IllegalArgumentException {
    this(model, System.out);
  }


  /**
   * Constructor for the text view of Image Processor
   * that takes in a model and an Appendable object.
   *
   * @param model      the ImageProcessorModel that this view will display.
   * @param appendable representing an Appendable object.
   * @throws IllegalArgumentException if any of the arguments are null
   */
  public ImageProcessorTextView(ImageProcessorViewModel model, Appendable appendable)
          throws IllegalArgumentException {
    super(model, appendable);
  }
}
