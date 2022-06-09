package controller;

/**
 * A controller for the Image Processor.
 * The controller has one method that enables a client to utilize various commands.
 * The controller enables the client to interact with the model and the view.
 */
public interface ImageProcessorController {


  /**
   * The method enables a client to send inputs to the model and outputs to the view.
   * A client is able to call on various methods offered by the model for image processing.
   */
  public void execute() throws IllegalStateException;


}
