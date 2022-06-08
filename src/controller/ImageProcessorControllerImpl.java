package controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import imageFormat.PPMImageFormat;
import model.ImageProcessorModel;
import operations.BrightenOrDarken;
import operations.FlipHorizontal;
import operations.FlipVertical;
import operations.Operation;
import operations.VisualizeBlue;
import operations.VisualizeGreen;
import operations.VisualizeIntensity;
import operations.VisualizeLuma;
import operations.VisualizeRed;
import operations.VisualizeValue;
import view.ImageProcessorView;

/**
 * Represents an implementation of an ImageProcessorController.
 * The controller enables a user to utilize the various operations of Image Processor.
 */
public class ImageProcessorControllerImpl implements ImageProcessorController {

  private ImageProcessorModel model;

  private ImageProcessorView view;

  private Readable input;

  private Map<String, Operation> operationDirectory;


  public ImageProcessorControllerImpl
          (ImageProcessorModel model, ImageProcessorView view, Readable input)
          throws IllegalArgumentException {
    if (model == null || view == null || input == null) {
      throw new IllegalArgumentException("model, view, and readable object cannot be null");
    }
    this.model = model;
    this.view = view;
    this.input = input;
    operationDirectory = new HashMap<String, Operation>();
    operationDirectory.put("flip-horizontal", new FlipHorizontal());
    operationDirectory.put("flip-vertical", new FlipVertical());
    operationDirectory.put("visualize-green", new VisualizeGreen());
    operationDirectory.put("visualize-blue", new VisualizeBlue());
    operationDirectory.put("visualize-red", new VisualizeRed());
    operationDirectory.put("visualize-intensity", new VisualizeIntensity());
    operationDirectory.put("visualize-luma", new VisualizeLuma());
    operationDirectory.put("visualize-value", new VisualizeValue());
    // need to add the change-brightness method
    operationDirectory.put("change-brightness", new BrightenOrDarken(0));


  }


  @Override
  public void execute() throws IllegalStateException {
    try {
      this.view.renderMessage("Welcome to Image Processor!\n");
      this.view.renderMessage("The available commands are listed below:\n");
      this.view.renderMessage("To load an image: load fileName nameToCall\n");
      this.view.renderMessage("fileName represents the name of the file to load\n");
      this.view.renderMessage("nameToCall representing the name to load the image as in the model");
      this.view.renderMessage("To change the brightness of an image: change-brightness scale" +
              " fileName nameToCall\n");
      this.view.renderMessage("Scale represents an integer to increase or decrease brightness by\n");
      this.view.renderMessage("fileName representing the name of the file to modify\n");
      this.view.renderMessage("nameToCall representing the name to be of the modified image\n");
      this.view.renderMessage("To flip an image horizontally: flip-horizontal fileName nameToCall\n");
      this.view.renderMessage("fileName represents the name of the file to modify\n");
      this.view.renderMessage("nameToCall represents the name to call the modified file\n");
      this.view.renderMessage("To flip an image vertically: flip-vertical fileName nameToCall\n");
      this.view.renderMessage("fileName represents the name of the file to modify\n");
      this.view.renderMessage("nameToCall represents the name to call the modified file\n");
      this.view.renderMessage("To visualize green component: visualize-green " +
              "fileName nameToCall\n");
      this.view.renderMessage("To visualize blue component: visualize-blue fileName nameToCall\n");
      this.view.renderMessage("To visualize red component: visualize-red fileName nameToCall\n");
      this.view.renderMessage("To visualize intensity: visualize-intensity fileName nameToCall\n");
      this.view.renderMessage("To visualize luma: visualize-luma fileName nameToCall\n");
      this.view.renderMessage("To visualize value: visualize-value fileName nameToCall\n");
      this.view.renderMessage("To save an image: save images/fileName.ppm nameToSaveAs\n");
      this.view.renderMessage("fileName represents the name of the file to save as\n");
      this.view.renderMessage("nameToSaveAs represents the name of the image to be saved\n");
      // 25


      boolean quit = false;
      String strInput = "";
      Scanner scan = new Scanner(this.input);

      while (!quit) {
        strInput = scan.next();

        // need to check if the user quits
        if (strInput.equalsIgnoreCase("q")) {
          quit = true;
        }
        // if the user wants to load an image
        else if (strInput.equalsIgnoreCase("load")) {
          String dest = scan.next();
          String fileName = scan.next();
//          String fileFormat = scan.next();
          String fileFormat = dest.substring(dest.lastIndexOf('.') + 1); // last 3 letters
          try {
            // checks that the format is a ppm
            if (fileFormat.equals("ppm")) {
              this.model.loadImage(dest, fileName, new PPMImageFormat());
              this.view.renderMessage("Image has been loaded\n");
            }
            else {
              this.view.renderMessage("The file-type is not supported, re-input a valid command");
            }


          } catch (IllegalArgumentException iae) { // file isn't found
            this.view.renderMessage("File doesn't exist, re-enter a valid command\n");
          }


        }
        // need to check if the user saves
        else if (strInput.equalsIgnoreCase("save")) {
          try {
            String imagePath = scan.next();
            String imageName = scan.next();

            String fileType = imagePath.substring(imagePath.lastIndexOf('.') + 1);
            if (fileType.equals("ppm")) { // if it's to be saved as a ppm file
              if (model.getImage(imageName) == null) { // then make the user reinput a valid command
                this.view.renderMessage("Image doesn't exist, re-enter a valid command\n");
              } else { // you are able to successfully save an image
                this.model.saveImage(imagePath, imageName, new PPMImageFormat());
                this.view.renderMessage("Image has been saved\n");
              }

            }
            else {
              this.view.renderMessage("The file-type is not supported, re-input a valid command");
            }
          } catch (IllegalArgumentException iae) {
            this.view.renderMessage("Something doesn't exist, re-enter a valid command\n");
          }


        }

        // need to check if a user calls on an operation
        else if (this.operationDirectory.get(strInput) != null) {
          // need to deal with additional stuff if they are using change-brightness
          // that takes in an additional parameter that is an integer

          // need to deal with exception handling if it's not in the hashmap
          if (strInput.equals("change-brightness")) {
            try {
              int scale = Integer.parseInt(scan.next());
              String imageName = scan.next();
              String newImageName = scan.next();
              if (this.model.getImage(imageName) == null) {
                this.view.renderMessage("Image doesn't exist, re-enter a valid command\n");
              } else {
                this.model.doOperation(new BrightenOrDarken(scale), imageName, newImageName);
                this.view.renderMessage("Operation has been performed\n");
              }
            } catch (NumberFormatException e) {
              this.view.renderMessage("Image doesn't exist, re-enter a valid command\n");
            }
          } else { // it's an operation that doesn't take in an additional parameter
            try {
              String imageName = scan.next();
              String newImageName = scan.next();
              if (this.model.getImage(imageName) == null) { // image doesn't exist in the directory
                this.view.renderMessage("Image doesn't exist, re-enter a valid command\n");
              } else {

                // image does exist, so do the operation
                this.model.doOperation(this.operationDirectory.get(strInput),
                        imageName, newImageName);
                this.view.renderMessage("Operation has been performed\n");
              }
            } catch (IllegalArgumentException iae) {
              this.view.renderMessage("Something doesn't exist, re-enter a valid command\n");
            }

          }


        } else { // the input was invalid, so the user has to reinput
          this.view.renderMessage("Invalid input, re-enter a valid command\n");
        }


      }

      // assuming that the user has quit
      this.view.renderMessage("Image Processor has quit.");


    } catch (IOException io) {
      throw new IllegalStateException("Failed to transmit to Appendable or read from Readable");
    }

  }
}
