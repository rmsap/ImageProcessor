package view;

import java.awt.*;
import java.awt.image.BufferedImage;

import controller.Features;

/**
 * This interface represents a GUI view for ImageProcessor.
 */
public interface ImageProcessorGUIView extends ImageProcessorView {


  /**
   * Refreshes the screen and updates the image.
   */
  void refresh(Image bruh);

  /**
   * Adds the features to the view.
   * @param features representing the features
   */
  void addFeatures(Features features);


  /**
   * Visualizes the image histogram of the image.
   */
  void visualizeHistogram(Image image);
}
