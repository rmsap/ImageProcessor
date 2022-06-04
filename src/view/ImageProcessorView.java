package view;

import java.io.IOException;

/**
 * Represents the view of Image Processor.
 * The view enables images to be displayed along with processing changes.
 */
public interface ImageProcessorView {

  public String toString();


  /**
   * Renders the image.
   * The method calls on the overridden toString() method to render the image.
   */
  public void renderImage() throws IOException;

  /**
   * Renders a given message to the view.
   * @param message representing the String that is passed in.
   */
  public void renderMessage(String message) throws IOException;

}
