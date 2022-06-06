package view;

import java.io.IOException;

/**
 * Represents the view of Image Processor.
 * The view enables images to be displayed along with processing changes.
 */
public interface ImageProcessorView {



  /**
   * Renders a given message to the view.
   * @param message representing the String that is passed in.
   */
  public void renderMessage(String message) throws IOException;

}
