import imageFormat.PPMImageFormat;
import model.ImageProcessorModel;
import model.PPMImageProcessorModel;
import operations.BrightenOrDarken;

/**
 * This class represents a main method to run the image processor program.
 */
public class ImageProcessor {
  /**
   * A simple main method to run the image processor.
   * @param args any command-line arguments necessary to run the image processor
   */
  public static void main(String[] args) {
    ImageProcessorModel model = new PPMImageProcessorModel();
    model.loadImage("Koala.ppm", "Koala", new PPMImageFormat());
//      System.out.println(model.getImage("Koala")[0][0]);
//      System.out.println(model.getImage("Koala")[0][1]);
    System.out.println(model.getImage("Koala").length);
//      System.out.println(model.getImage("Koala"));
    model.doOperation(new BrightenOrDarken(-1000), "Koala", "Koala black");
    model.saveImage("KoalaBlack.ppm", "Koala black", new PPMImageFormat());
//      System.out.println(model.getImage("Koala brighten"));
//      model.saveImage("KoalaVersion2.ppm", "Koala");
  }
}
