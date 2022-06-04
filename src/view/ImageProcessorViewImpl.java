package view;

import model.ImageProcessorModel;

/**
 * An implementation of a view for Image Processor.
 * This implementation uses a text-based view.
 */
public class ImageProcessorViewImpl extends AbstractImageProcessorView{


  /**
   * Default constructor for the view of Image Processor.
   * @param model representing the ImageProcessorModel.
   * @throws IllegalArgumentException if the argument is null.
   */
  public ImageProcessorViewImpl(ImageProcessorModel model) throws IllegalArgumentException {
    if(model == null) {
      throw new IllegalArgumentException("Parameter cannot be null");
    }
    this.model = model;
    this.appendable = System.out;
  }


  /**
   * Constructor for the view of Image Processor that takes in a model and an Appendable object.
   * @param model representing an ImageProcessorModel.
   * @param appendable representing an Appendable object.
   * @throws IllegalArgumentException if any of the arguments are null
   */
  public ImageProcessorViewImpl(ImageProcessorModel model, Appendable appendable)
          throws IllegalArgumentException {
    if(model == null || appendable == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    this.model = model;
    this.appendable = appendable;
  }


  /**
   * The overridden version of the method uses a text-based view.
   */
  @Override
  public void renderImage() {

  }
}
