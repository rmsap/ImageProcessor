import org.junit.Test;

import imageformat.ImageFormat;
import imageformat.PPMImageFormat;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Represents the test class for a model implementation of Image Processor.
 */
public class ImageProcessorModelImplTest {

  @Test
  public void loadImageValid() {
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageFormat ppm = new PPMImageFormat();
    int[][] originalImage = ppm.read("res/4x4ppmOriginal.ppm");

    int[][] expected = {
            {4, 4, 255},
            {100, 0, 0}, {100, 0, 0}, {0, 0, 0}, {0, 0, 0},
            {100, 0, 0}, {100, 0, 0}, {0, 0, 0}, {0, 0, 0},
            {0, 100, 0}, {0, 100, 0}, {255, 255, 255}, {255, 255, 255},
            {0, 100, 0}, {0, 100, 0}, {255, 255, 255}, {255, 255, 255}};

    model.loadImage("originalPic", ppm.read("res/4x4ppmOriginal.ppm"));


    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j], model.getImage("originalPic")[i][j]);
      }
    }

  }


  @Test
  public void loadImageInvalid() throws IllegalArgumentException {
    try { // trying to get from a path that doesn't exist
      ImageProcessorModel model = new ImageProcessorModelImpl();
      model.loadImage("wowzers", new PPMImageFormat().read("orangejustice"));
      fail("An exception was supposed to be caught since orangejustice is not an existing path");

    } catch (IllegalArgumentException ie) {
      // exception successfully caught
    }
    try { // the imageFormat object is null
      ImageProcessorModel model = new ImageProcessorModelImpl();
      model.loadImage("wowzers", null);
      fail("An exception was supposed to be caught since no image was provided");

    } catch (IllegalArgumentException ie) {
      // exception successfully caught
    }
    try { // the name is null
      ImageProcessorModel model = new ImageProcessorModelImpl();
      model.loadImage(null, new PPMImageFormat().read("res/4x4ppmOriginal.ppm"));
      fail("An exception was supposed to be caught since the name is null");

    } catch (IllegalArgumentException ie) {
      // exception successfully caught
    }

  }


  @Test
  public void getImageInvalid() throws IllegalArgumentException {
    try {
      ImageProcessorModel model = new ImageProcessorModelImpl();
      model.getImage(null);
      fail("An exception was supposed to be caught");

    } catch (IllegalArgumentException ie) {
      // Exception successfully caught
    }

  }

  @Test
  public void getImageValid() throws IllegalArgumentException {
    ImageProcessorModel model = new ImageProcessorModelImpl();
    model.loadImage("originalPic", new PPMImageFormat().read("res/4x4ppmOriginal.ppm"));
    assertEquals(255, model.getImage("originalPic")[0][2]);
    assertEquals(4, model.getImage("originalPic")[0][0]);
    assertEquals(4, model.getImage("originalPic")[0][1]);
    assertEquals(0, model.getImage("originalPic")[1][1]);
  }


}