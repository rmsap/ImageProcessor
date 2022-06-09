import java.io.InputStreamReader;

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
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorView view = new ImageProcessorTextView(model);
    ImageProcessorController controller =
            new ImageProcessorControllerImpl(model, view, new InputStreamReader(System.in));
    controller.execute();
  }
}
