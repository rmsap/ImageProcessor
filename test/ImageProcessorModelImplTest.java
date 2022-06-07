import org.junit.Test;

import imageFormat.PPMImageFormat;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;

import static org.junit.Assert.fail;


/**
 * Represents the test class for a model implementation of Image Processor.
 */
public class ImageProcessorModelImplTest {

  @Test
  public void loadImage() {

  }


  @Test
  public void loadImageInvalid() throws IllegalArgumentException{
    try{ // trying to get from a path that doesn't exist
      ImageProcessorModel model = new ImageProcessorModelImpl();
      model.loadImage("orangejustice","wowzers", new PPMImageFormat());
      fail("An exception was supposed to be caught since orangejustice is not an existing path");

    }
    catch(IllegalArgumentException ie) {
      // exception successfully caught
    }
    try{ // the imageFormat object is null
      ImageProcessorModel model = new ImageProcessorModelImpl();
      model.loadImage("res/4x4ppmOriginal.ppm","wowzers", null);
      fail("An exception was supposed to be caught since orangejustice is not an existing path");

    }
    catch(IllegalArgumentException ie) {
      // exception successfully caught
    }
    try{ // the name is null
      ImageProcessorModel model = new ImageProcessorModelImpl();
      model.loadImage("res/4x4ppmOriginal.ppm",null, new PPMImageFormat());
      fail("An exception was supposed to be caught since the name is null");

    }
    catch(IllegalArgumentException ie) {
      // exception successfully caught
    }

  }

  @Test
  public void saveImage() {

  }


  @Test
  public void saveImageInvalid() throws IllegalArgumentException {
    try{ // the image in the directory does not exist
      ImageProcessorModel model = new ImageProcessorModelImpl();
      model.saveImage("res/Bruh.ppm", "Bruhtonium", new PPMImageFormat());
      fail("An exception was supposed to be caught because the image does not exist");


    }
    catch(IllegalArgumentException ie) {
      // exception successfully caught
    }
    try{ // the path does not exist
      ImageProcessorModel model = new ImageProcessorModelImpl();
      model.loadImage("res/4x4ppmOriginal.ppm","og", new PPMImageFormat());
      model.saveImage("oogabooga/Bruh.ppm", "og", new PPMImageFormat());
      fail("An exception was supposed to be caught because the image does not exist");
    }
    catch(IllegalArgumentException ie) {
      // exception successfully caught
    }
  }


  @Test
  public void getImageInvalid() throws IllegalArgumentException {
    try{
      ImageProcessorModel model = new ImageProcessorModelImpl();
      model.getImage(null);
      fail("An exception was supposed to be caught");

    }
    catch(IllegalArgumentException ie) {
      // Exception successfully caught
    }

  }
}