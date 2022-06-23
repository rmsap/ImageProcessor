package controller;

/**
 * Represents an interface of features that the view can trigger at specific events.
 * Contains application-specific events.
 */
public interface Features {

  /**
   * Represents the action listener for brightenOrDarken.
   * @param input representing the amount to brighten/darken by.
   */
  void brightenOrDarken(int input);

  /**
   * Represents the action listener for greyscale operation.
   */
  void greyscale();

  /**
   * Represents the action listener for the sepia operation.
   */
  void sepia();

  /**
   * Represents the action listener for the sharpen operation.
   */
  void sharpen();

  /**
   * Represents the action listener for the blur operation.
   */
  void blur();

  /**
   * Represents the action listener for the operation that flips an image horizontally.
   */
  void flipHorizontal();

  /**
   * Represents the action listener for the operation that flips an image vertically.
   */
  void flipVertical();

  /**
   * Represents the action listener for the operation that visualizes the red components.
   */
  void visualizeRed();

  /**
   * Represents the action listener for the operation that visualizes the green components.
   */
  void visualizeGreen();

  /**
   * Represents the action listener for the operation that visualizes the blue components.
   */
  void visualizeBlue();

  /**
   * Represents the action listener for the operation that visualizes the intensity of an image.
   */
  void visualizeIntensity();

  /**
   * Represents the action listener for the operation that visualize the value of an image's pixels.
   */
  void visualizeValue();


  /**
   * Represents the action listener that calls (directly or indirectly) on the
   * operation that visualizes luma.
   */
  void visualizeLuma();

  /**
   * Represents the action listener that calls (directly or indirectly) on the model's load method.
   * @param filePath representing the path of the file that is to be loaded.
   */
  void load(String filePath);

  /**
   * Represents the action listener that calls (directly or indirectly) on the model's save method.
   * @param filePath representing the path of the file that is to be saved.
   */
  void save(String filePath);

  /**
   * Set the Controller of this Features to the given controller.
   * @param controller the controller to set this Features's controller to
   */
  void setController(ImageProcessorGUIController controller);
}
