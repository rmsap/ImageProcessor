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
   * @param image the image that we are creating a histogram of
   * @return an image representing a histogram of the color distribution of the given image
   */
  Image createHistogram(Image image);
}
