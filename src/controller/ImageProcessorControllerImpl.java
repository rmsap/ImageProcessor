package controller;

import java.io.IOException;

import model.ImageProcessorModel;
import view.ImageProcessorView;

/**
 * Represents an implementation of an ImageProcessorController.
 * The controller enables a user to utilize the various operations of Image Processor.
 */
public class ImageProcessorControllerImpl implements ImageProcessorController {

  private ImageProcessorModel model;

  private ImageProcessorView view;

  private Readable input;


  public ImageProcessorControllerImpl
          (ImageProcessorModel model, ImageProcessorView view, Readable input)
          throws IllegalArgumentException {
    if (model == null || view == null || input == null) {
      throw new IllegalArgumentException("model, view, and readable object cannot be null");
    }
    this.model = model;
    this.view = view;
    this.input = input;


  }


  @Override
  public void execute() throws IllegalStateException {
    try{
      this.view.renderMessage("Welcome to Image Processor!\n");
      this.view.renderMessage("The available commands are listed below:\n");
      this.view.renderMessage("To load an image: load images/fileName.ppm nameToCall\n");
      this.view.renderMessage("fileName represents the name of the file to load\n");
      this.view.renderMessage("nameToCall representing the name to load the image as in the model");
      this.view.renderMessage("To change the brightness of an image: change-brightness scale fileName nameToCall\n");
      this.view.renderMessage("Scale represents an integer to increase or decrease brightness by\n");
      this.view.renderMessage("fileName representing the name of the file to modify\n");
      this.view.renderMessage("nameToCall representing the name to be of the modified image\n");
      this.view.renderMessage("To flip an image horizontally: horizontal-flip fileName nameToCall\n");
      this.view.renderMessage("fileName represents the name of the file to modify\n");
      this.view.renderMessage("nameToCall represents the name to call the modified file\n");
      this.view.renderMessage("To flip an image vertically: vertical-flip fileName nameToCall\n");
      this.view.renderMessage("fileName represents the name of the file to modify\n");
      this.view.renderMessage("nameToCall represents the name to call the modified file\n");
      this.view.renderMessage("To greyscale an image: value-component fileName nameToCall\n");
      this.view.renderMessage("value-component represents the letter i, l, or g\n");
      this.view.renderMessage("To color an image: value-component fileName nameToCall\n");
      this.view.renderMessage("value-component represents the letter r, g, or b\n");
      this.view.renderMessage("To save an image: save images/fileName.ppm nameToSaveAs\n");
      this.view.renderMessage("fileName represents the name of the file to save as\n");
      this.view.renderMessage("nameToSaveAs represents the name of the image to be saved");









    }
    catch(IOException io) {
      throw new IllegalStateException("Failed to transmit to Appendable or read from Readable");
    }

  }
}
