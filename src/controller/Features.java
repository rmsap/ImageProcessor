package controller;

import model.ImageProcessorModel;

/**
 * Represents an interface of features (basically ActionListeners, etc).
 * Contains application-specific events.
 * Hides Swing-specific events.
 * The controller should use composition for the Features Interface.
 * Each method is essentially a request.
 */
public interface Features {
  void brightenOrDarken(int input);

  void greyscale();

  void sepia();

  void sharpen();

  void blur();

  void flipHorizontal();

  void flipVertical();

  void visualizeRed();

  void visualizeGreen();

  void visualizeBlue();

  void visualizeIntensity();

  void visualizeValue();

  void visualizeLuma();

  void load(String filePath);

  void save();
}
