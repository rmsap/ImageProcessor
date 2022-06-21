package controller;

import model.ImageProcessorModel;
import operations.Operation;

/**
 * Represents an interface of features (basically ActionListeners, etc).
 * Contains application-specific events.
 * Hides Swing-specific events.
 * The controller should use composition for the Features Interface.
 * Each method is essentially a request.
 */
public interface Features {

  /**
   * Represents the action listener for brightenOrDarken.
   * @param input representing the amount to brighten/darken by.
   */
  Operation brightenOrDarken(int input);

  /**
   * Represents the action listener for greyscale operation.
   */
  Operation greyscale();

  /**
   * Represents the action listener for the sepia operation.
   */
  Operation sepia();

  /**
   * Represents the action listener for the sharpen operation.
   */
  Operation sharpen();

  /**
   * Represents the action listener for the blur operation.
   */
  Operation blur();

  /**
   * Represents the action listener for the operation that flips an image horizontally.
   */
  Operation flipHorizontal();

  /**
   * Represents the action listener for the operation that flips an image vertically.
   */
  Operation flipVertical();

  /**
   * Represents the action listener for the operation that visualizes the red components.
   */
  Operation visualizeRed();

  /**
   * Represents the action listener for the operation that visualizes the green components.
   */
  Operation visualizeGreen();

  /**
   * Represents the action listener for the operation that visualizes the blue components.
   */
  Operation visualizeBlue();

  /**
   * Represents the action listener for the operation that visualizes the intensity of an image.
   */
  Operation visualizeIntensity();

  /**
   * Represents the action listener for the operation that visualize the value of an image's pixels.
   */
  Operation visualizeValue();


  /**
   * Represents the action listener that calls on the operation that visualizes luma.
   */
  Operation visualizeLuma();

  /**
   * Represents the action listener that calls on the model's load method.
   * @param filePath representing the path of the file that is to be loaded.
   */
  void load(String filePath);

  /**
   * Represents the action listener that calls on the model's save method.
   * @param filePath representing the path of the file that is to be saved.
   */
  void save(String filePath);

  void setController(ImageProcessorController controller);
}
