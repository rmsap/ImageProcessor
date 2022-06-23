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
    ImageProcessorView view = new ImageProcessorTextView(model, System.out);
    ImageProcessorController controller = new ImageProcessorControllerImpl(model,
            view, new InputStreamReader(System.in));
    boolean isInvalid = true;

    /*
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorView view = new ImageProcessorTextView(model, System.out);
    ImageProcessorController controller = new ImageProcessorControllerImpl(model,
            view, new InputStreamReader(System.in));

     */


    /*
    if (args.length > 0) {
      // should only run from command line if there is a "q" so the readable doesn't
      // run out of inputs
      boolean hasQ = true;

      if (hasQ) { // we know it's a valid text file
        try {
          File file = new File(args[0]);
          FileReader reader = new FileReader(file);
          controller = new ImageProcessorControllerImpl(model, view, reader);
        } catch (FileNotFoundException bruh) {
          controller = new ImageProcessorControllerImpl(model, view,
                  new InputStreamReader(System.in));
        }
      }

    }

     */
    if (args.length == 2) { // -file nameOfFile.txt
      if (args[0].equals("-file")) {
        if (args[1].substring(args[1].length() - 4).equals(".txt")) { // ends in .txt
          try {
            File file = new File(args[1]);
            FileReader reader = new FileReader(file);
            controller = new ImageProcessorControllerImpl(model, view, reader);
            isInvalid = false;
          } catch (FileNotFoundException bruh) { // if the file doesn't exist
            // does nothing since the boolean is already assigned to true

            /*
            controller = new ImageProcessorControllerImpl(model, view,
                    new InputStreamReader(System.in));

             */
            // should exit if it's invalid and quit
          }
        }
      }

    } else if (args.length == 1) { // checking if it's -text
      if (args[0].equals("-text")) {
        isInvalid = false;
        // don't need to do anything else since it's already initialized to the desire thing
        // in the beginning
//        controller.execute();
      }

    } else if (args.length == 0) { // run the GUI version of the program
      isInvalid = false;
      // make the controller be the GUI version
      GUIViewImpl guiView = new GUIViewImpl("Image Processor");
      Features features = new FeaturesImpl();
      controller = new ImageProcessorControllerGUI(model, guiView, features);
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
