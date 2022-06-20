package view;

/**
 * This interface represents a GUI view for ImageProcessor.
 */
public interface ImageProcessorGUIView {


  /**
   * Refreshes the screen.
   */
  void refresh();

  /**
   * Displays all action buttons and the space for the image and the image histogram.
   */
  void displayDefaultScreen();
}
