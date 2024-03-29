package controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import imageformat.ImageFormat;
import model.ImageProcessorModel;
import operations.BrightenOrDarken;
import operations.Filter;
import operations.Filter.Filters;
import operations.FlipHorizontal;
import operations.FlipVertical;
import operations.Operation;
import operations.ColorTransformation;
import operations.ColorTransformation.Transformation;
import operations.VisualizeComponent;
import operations.VisualizeComponent.Component;
import view.ImageProcessorView;

/**
 * Represents an implementation of an ImageProcessorController.
 * The controller enables a user to utilize the various operations of Image Processor.
 */
public class ImageProcessorControllerImpl extends AbstractImageProcessorController {
  private final Readable input;

  // INVARIANT: All keys contained in operationDirectory are valid user inputs
  private final Map<String, Operation> operationDirectory;

  /**
   * Default constructor for the controller of Image Processor.
   *
   * @param model representing an ImageProcessorModel.
   * @param view  representing an ImageProcessorView.
   * @param input representing a Readable object.
   * @throws IllegalArgumentException if any parameters are null
   */
  public ImageProcessorControllerImpl(ImageProcessorModel model, ImageProcessorView view,
                                      Readable input) throws IllegalArgumentException {
    super(model, view);

    if (input == null) {
      throw new IllegalArgumentException("model, view, and readable object cannot be null");
    }

    this.input = input;

    // Instantiate the operation directory and load all user commands and operations into it
    operationDirectory = new HashMap<String, Operation>();
    operationDirectory.put("flip-horizontal", new FlipHorizontal());
    operationDirectory.put("flip-vertical", new FlipVertical());
    operationDirectory.put("visualize-green", new VisualizeComponent(Component.Green));
    operationDirectory.put("visualize-blue", new VisualizeComponent(Component.Blue));
    operationDirectory.put("visualize-red", new VisualizeComponent(Component.Red));
    operationDirectory.put("visualize-intensity",
            new VisualizeComponent(Component.Intensity));
    operationDirectory.put("visualize-luma", new VisualizeComponent(Component.Luma));
    operationDirectory.put("visualize-value", new VisualizeComponent(Component.Value));
    operationDirectory.put("sepia", new ColorTransformation(Transformation.Sepia));
    operationDirectory.put("greyscale", new ColorTransformation(Transformation.Greyscale));
    operationDirectory.put("change-brightness", new BrightenOrDarken(0));
    operationDirectory.put("blur", new Filter(Filters.Blur));
    operationDirectory.put("sharpen", new Filter(Filters.Sharpen));
  }


  @Override
  public void execute() throws IllegalStateException {
    this.renderMenu();

    boolean quit = false;
    String strInput = "";
    Scanner scan = new Scanner(this.input);

    while (!quit) {
      strInput = scan.next();

      // check if the user quits
      if (strInput.equalsIgnoreCase("q")) {
        quit = true;
      }
      // if the user wants to load an image
      else if (strInput.equalsIgnoreCase("load")) {
        String dest = scan.next();
        String fileName = scan.next();
        this.handleLoad(dest, fileName);
      }
      // check if the user saves
      else if (strInput.equalsIgnoreCase("save")) {
        String imagePath = scan.next();
        String imageName = scan.next();
        this.handleSave(imagePath, imageName);
      }
      // check if a user calls on an operation
      else if (this.operationDirectory.get(strInput) != null) {
        if (strInput.equals("change-brightness")) {
          int scale = 0;
          try {
            scale = Integer.parseInt(scan.next());
          } catch (NumberFormatException e) {
            try {
              this.view.renderMessage("Please enter a valid integer\n");
            } catch (IOException io) {
              throw new IllegalStateException("Failed to transmit to Appendable or read "
                      + "from Readable");
            }
          }

          String imageName = scan.next();
          String newImageName = scan.next();
          this.handleChangeBrightness(scale, imageName, newImageName);

        } else { // it's an operation that doesn't take in an additional parameter
          String imageName = scan.next();
          String newImageName = scan.next();
          this.handleOperation(strInput, imageName, newImageName);
        }
      } else { // the input was invalid, so the user has to reinput
        try {
          this.view.renderMessage("Invalid input, re-enter a valid command\n");
        } catch (IOException io) {
          throw new IllegalStateException("Failed to transmit to Appendable or read "
                  + "from Readable");
        }
      }
    }
    // The user has quit
    try {
      this.view.renderMessage("Image Processor has quit.");
    } catch (IOException io) {
      throw new IllegalStateException("Failed to transmit to Appendable or read from Readable");
    }
  }

  /**
   * Instruct the view to render the menu that includes a list of available commands.
   *
   * @throws IllegalStateException if the controller fails to transmit the menu to the view
   */
  private void renderMenu() throws IllegalStateException {
    try {
      this.view.renderMessage("Welcome to Image Processor!\n");
      this.view.renderMessage("The available commands are listed below:\n");
      this.view.renderMessage("To load an image: load fileName nameToCall\n");
      this.view.renderMessage("fileName represents the name of the file to " +
              "load with the path information\n");
      this.view.renderMessage("nameToCall representing the name to load the image as in the model");
      this.view.renderMessage("To change the brightness of an image: change-brightness scale" +
              " imageName nameToCall\n");
      this.view.renderMessage("Scale represents an integer to increase or " +
              "decrease brightness by\n");
      this.view.renderMessage("imageName representing the name of the image to modify\n");
      this.view.renderMessage("nameToCall representing the name to be of the modified image\n");
      this.view.renderMessage("To flip an image horizontally: flip-horizontal " +
              "imageName nameToCall\n");
      this.view.renderMessage("imageName represents the name of the image to modify\n");
      this.view.renderMessage("nameToCall represents the name to call the modified image\n");
      this.view.renderMessage("To flip an image vertically: flip-vertical imageName nameToCall\n");
      this.view.renderMessage("imageName represents the name of the image to modify\n");
      this.view.renderMessage("nameToCall represents the name to call the modified image\n");
      this.view.renderMessage("To visualize green component: visualize-green " +
              "imageName nameToCall\n");
      this.view.renderMessage("To visualize blue component: visualize-blue imageName nameToCall\n");
      this.view.renderMessage("To visualize red component: visualize-red imageName nameToCall\n");
      this.view.renderMessage("To visualize intensity: visualize-intensity imageName nameToCall\n");
      this.view.renderMessage("To visualize luma: visualize-luma imageName nameToCall\n");
      this.view.renderMessage("To visualize value: visualize-value imageName nameToCall\n");
      this.view.renderMessage("To greyscale: greyscale imageName nameToCall\n");
      this.view.renderMessage("To blur: blur imageName nameToCall\n");
      this.view.renderMessage("To sharpen: sharpen imageName nameToCall\n");
      this.view.renderMessage("To save an image: save filePath imageName\n");
      this.view.renderMessage("filePath represents the name of the file to save as" +
              " along with it's specified path\n");
      this.view.renderMessage("imageName represents the name of the image to be saved " +
              "as a file\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to transmit menu of commands to Appendable.");
    }
  }

  /**
   * Handle telling the model to load an image with the given file name and name to refer to it as
   * in the program.
   *
   * @param dest     the name to refer to the file as henceforth in the program
   * @param fileName the name of the file we want to load
   * @throws IllegalStateException if the view fails to write to its Appendable
   */
  private void handleLoad(String dest, String fileName) throws IllegalStateException {
    String fileFormat = dest.substring(dest.lastIndexOf('.') + 1);

    if (this.formatDirectory.get(fileFormat) != null) {
      try {
        this.model.loadImage(fileName, this.formatDirectory.get(fileFormat).read(dest));
        try {
          this.view.renderMessage("Image has been loaded\n");
        } catch (IOException e) { // file isn't found
          throw new IllegalStateException("Failed to write to Appendable.");
        }
      } catch (IllegalArgumentException e) {
        try {
          this.view.renderMessage("File doesn't exist or type is not supported, "
                  + "re-enter a valid command\n");
        } catch (IOException io) {
          throw new IllegalStateException("Failed to write to Appendable.");
        }
      }

    } else {
      try {
        this.view.renderMessage("The file-type is not supported\n");
      } catch (IOException ei) {
        throw new IllegalStateException("Failed to write to appendable");
      }

    }
  }

  /**
   * Handle telling the ImageFormat to save the image with the given name to the given path.
   *
   * @param imagePath the path that the image is going to be saved to
   * @param imageName the name of the image that is going to be saved
   * @throws IllegalStateException if the view fails to write to its Appendable
   */
  private void handleSave(String imagePath, String imageName) throws IllegalStateException {
    String fileType = imagePath.substring(imagePath.lastIndexOf('.') + 1);

    ImageFormat fileFormat = this.formatDirectory.get(fileType);
    if (fileFormat != null) {
      try {
        try {
          fileFormat.save(imagePath, model.getImage(imageName));
          this.view.renderMessage("Image has been saved\n");
        } catch (IllegalArgumentException e) {
          this.view.renderMessage("An image with the given name does not exist in the model " +
                  "or the file path is invalid.\n");
          System.out.println(e.getMessage());
        }
      } catch (IOException io) {
        throw new IllegalStateException("Failed to write to Appendable");
      }
    } else {
      try {
        this.view.renderMessage("The file-type is not supported, re-input a valid command");
      } catch (IOException e) {
        throw new IllegalStateException("Failed to write to Appendable");
      }
    }
  }

  private void handleChangeBrightness(int scale, String imageName, String newImageName) {
    try {
      try {
        this.model.doOperation(new BrightenOrDarken(scale), imageName, newImageName);
        this.view.renderMessage("Operation has been performed\n");
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("Image doesn't exist, re-enter a valid command\n");
      }
    } catch (IOException io) {
      throw new IllegalStateException("Failed to write to Appendable");
    }
  }

  private void handleOperation(String operation, String imageName, String newImageName)
          throws IllegalStateException {
    try {
      try {
        // image exists, so do the operation
        this.model.doOperation(this.operationDirectory.get(operation),
                imageName, newImageName);
        this.view.renderMessage("Operation has been performed\n");
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("Image doesn't exist, re-enter a valid command\n");
      }
    } catch (IOException e) {
      throw new IllegalStateException("Failed to write to Appendable");
    }
  }
}
