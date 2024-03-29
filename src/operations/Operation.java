package operations;

import model.ImageProcessorModel;

/**
 * This interface represents an operation that
 * can be executed on an image from a ImageProcessorModel.
 */
public interface Operation {
  /**
   * Execute this Operation on the given ImageProcessorModel by modifying
   * the image with the given name that the model contains.
   *
   * @param model the model on which we are executing this Operation
   * @param name  the name of the image in the model on which we are executing this operation
   */
  int[][] execute(ImageProcessorModel model, String name);
}
