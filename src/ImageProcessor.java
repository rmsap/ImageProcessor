import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import imageFormat.PPMImageFormat;
import model.ImageProcessorModel;
import model.PPMImageProcessorModel;
import operations.BrightenOrDarken;
import operations.FlipVertical;
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
    try{
      ImageProcessorModel model = new PPMImageProcessorModel();
      model.loadImage("Koala.ppm", "Koala", new PPMImageFormat());
//      System.out.println(model.getImage("Koala")[0][0]);
//      System.out.println(model.getImage("Koala")[0][1]);
      System.out.println(model.getImage("Koala").length);
//      System.out.println(model.getImage("Koala"));
//      model.doOperation(new FlipVertical(), "Koala", "Koala vertical");
//      model.saveImage("KoalaBlack.ppm", "Koala vertical", new PPMImageFormat());
//      System.out.println(model.getImage("Koala brighten"));
//      model.saveImage("KoalaVersion2.ppm", "Koala");

      ImageProcessorView view = new ImageProcessorTextView(model, System.out);
      ImageProcessorController controller =
              new ImageProcessorControllerImpl(model, view, new InputStreamReader(System.in));
      controller.execute();

    }
    catch(FileNotFoundException f) {
      throw new IllegalStateException("something went wrong when it was not supposed to");

    }

  }
}
