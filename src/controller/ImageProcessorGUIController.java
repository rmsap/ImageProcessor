package controller;

import operations.Operation;

/**
 * This interface represents the methods necessary for a controller for a GUI-based view of the
 * image processor.
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

  /**
   * Execute the given Operation on the model.
   * @param op the operation to execute
   */
  void doOperation(Operation op);
}
