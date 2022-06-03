package model;

/**
 * This interface represents an operation that can be executed on an ImageProcessorModel.
 */
public interface Operation {
  /**
   * Execute this Operation on the given ImageProcessorModel by modifying the image that the model
   * contains.
   * @param model the model on which we are executing this Operation
   */
  void execute(ImageProcessorModel model);
}
