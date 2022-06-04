package view;

import java.io.IOException;

import model.ImageProcessorModel;

/**
 * Represent an abstract class of an implementations of a view for Image Processor.
 * The abstract class contains methods that are used throughout future and present implementations.
 */
public abstract class AbstractImageProcessorView implements ImageProcessorView {
  protected ImageProcessorModel model;
  protected Appendable appendable;


  @Override
  public String toString() {
    return "";
  }



  @Override
  public abstract void renderImage() throws IOException;

  @Override
  public void renderMessage(String message) throws IOException {
    this.appendable.append(message);


  }

}
