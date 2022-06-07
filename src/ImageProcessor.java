import java.io.InputStreamReader;

import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import model.ImageProcessorModel;
import model.PPMImageProcessorModel;
import view.ImageProcessorTextView;
import view.ImageProcessorView;

/**
 * This class represents a main method to run the image processor program.
 */
public class ImageProcessor {
  /**
   * A simple main method to run the image processor.
   * @param args any command-line arguments necessary to run the image processor
   */
  public static void main(String[] args) throws IllegalStateException {
    ImageProcessorModel model = new PPMImageProcessorModel();

    ImageProcessorView view = new ImageProcessorTextView(model, System.out);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(model, view, new InputStreamReader(System.in));
    controller.execute();
  }
}
