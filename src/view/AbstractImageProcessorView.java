package view;

import java.io.IOException;


import model.ImageProcessorViewModel;

/**
 * Represent an abstract class of an implementations of a view for Image Processor.
 * The abstract class contains methods that are used throughout future and present implementations.
 */
public abstract class AbstractImageProcessorView implements ImageProcessorView {
  protected ImageProcessorViewModel model;
  protected Appendable appendable;

  /**
   * Constructor for the abstract Image Processor view that
   * takes in a model and an Appendable object.
   *
   * @param model      the ImageProcessorViewModel that this will display
   * @param appendable representing an Appendable object.
   * @throws IllegalArgumentException if any of the arguments are null
   */
  AbstractImageProcessorView(ImageProcessorViewModel model, Appendable appendable) {
    if (model == null || appendable == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    this.model = model;
    this.appendable = appendable;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.appendable.append(message);
  }
}
