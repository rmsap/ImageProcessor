package view;

import java.io.IOException;

import model.ImageProcessorModel;
import model.ImageProcessorViewModel;

/**
 * Represent an abstract class of an implementations of a view for Image Processor.
 * The abstract class contains methods that are used throughout future and present implementations.
 */
public abstract class AbstractImageProcessorView implements ImageProcessorView {
  protected ImageProcessorViewModel model;
  protected Appendable appendable;


  @Override
  public void renderMessage(String message) throws IOException {
    this.appendable.append(message);


  }

}
