package controller;

import operations.Operation;

/**
 * Represents the interface for the GUI version of a controller for Image Processor.
 */
public interface ImageProcessorGUIController extends ImageProcessorController {
  /**
   * Loads an image into the model.
   * @param filePath representing the path of the file to be loaded
   */
  void load(String filePath);

  /**
   * Saves an image to the given path.
   * @param filePath representing the path that the file is to be saved as
   */
  void save(String filePath);

  void doOperation(Operation op);
}
