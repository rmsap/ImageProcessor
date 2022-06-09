package model;

/**
 * The interface represents a view-model that contains only accessor methods.
 * A view model does not have fields that can mutate the model.
 */
public interface ImageProcessorViewModel {
  /**
   * Get the image with the given name and return it.
   *
   * @param name the name of the image we want to get
   * @return the image with the given name
   * @throws IllegalArgumentException if an image with the given name does not exist in the
   *                                  model's directory
   */
  int[][] getImage(String name) throws IllegalArgumentException;
}
