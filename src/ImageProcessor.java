import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;


import controller.Features;
import controller.FeaturesImpl;
import controller.ImageProcessorController;
import controller.ImageProcessorControllerGUI;
import controller.ImageProcessorControllerImpl;
import controller.ImageProcessorGUIController;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import view.GUIViewImpl;
import view.ImageProcessorTextView;
import view.ImageProcessorView;

/**
 * This class represents a main method to run the image processor program.
 */
public class ImageProcessor {
  /**
   * A simple main method to run the image processor.
   *
   * @param args any command-line arguments necessary to run the image processor
   */
  public static void main(String[] args) throws IllegalStateException {
    // must be able to take in a text file of command arguments
    // if a valid script is provided, it should run it and then exit
    // a valid text will have q denoting when to quit
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorView view;
    ImageProcessorController controller;
    boolean isInvalid = true;

    if (args.length >= 1 && args[0].equals("-file")) {
      if (args[1].endsWith(".txt")) { // ends in .txt
        try {
          File file = new File(args[1]);
          FileReader reader = new FileReader(file);
          view = new ImageProcessorTextView(model, System.out);
          controller = new ImageProcessorControllerImpl(model, view, reader);
          isInvalid = false;
        } catch (FileNotFoundException bruh) { // if the file doesn't exist
          // does nothing since the boolean is already assigned to true
        }
      }
    }
    if (args.length >= 1 && args[0].equals("-text")) {
      isInvalid = false;
      view = new ImageProcessorTextView(model, System.out);
      controller = new ImageProcessorControllerImpl(model, view,
              new InputStreamReader(System.in));
    } else { // run the GUI version of the program
      isInvalid = false;
      // make the controller be the GUI version
      view = new GUIViewImpl("Image Processor");
      Features features = new FeaturesImpl();
      controller = new ImageProcessorControllerGUI(model, view, features);
      features.setController((ImageProcessorGUIController) controller);
    }


    if(!isInvalid) { // will run the controller if nothing invalid occurred
      controller.execute();
    }
    else if (isInvalid) { // message and quit
      System.out.print("something invalid occurred");
    }
  }
}
