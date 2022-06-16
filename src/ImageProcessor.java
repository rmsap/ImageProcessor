import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
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
    if(args.length > 0) {
      // should only run from command line if there is a "q" so the readable doesn't run out of inputs
      boolean hasQ = true;

      if(hasQ) { // we know it's a valid text file
        try{
          File file = new File(args[0]);
          FileReader reader = new FileReader(file);
          controller = new ImageProcessorControllerImpl(model, view, reader);
        }
        catch(FileNotFoundException bruh) {
          controller = new ImageProcessorControllerImpl(model, view, new InputStreamReader(System.in));
        }
      }

    }
    controller.execute();
  }
}
