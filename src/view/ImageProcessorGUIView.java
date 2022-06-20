package view;

import features.Features;

/**
 * This interface represents a GUI view for ImageProcessor.
 */
public interface ImageProcessorGUIView extends ImageProcessorView {


  /**
   * Refreshes the screen.
   */
  void refresh();

  /**
   * Adds the features to the view.
   * @param features representing the features
   */
  void addFeatures(Features features);
}
