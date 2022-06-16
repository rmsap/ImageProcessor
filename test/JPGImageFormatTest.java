import org.junit.Before;
import org.junit.Test;

import java.io.File;

import imageformat.ImageFormat;
import imageformat.JPGImageFormat;
import imageformat.PPMImageFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The following class tests the JPGImageFormat class.
 */
public class JPGImageFormatTest {

  ImageFormat jpg = new JPGImageFormat();

  @Before
  public void init() {
    jpg = new JPGImageFormat();
  }

  @Test
  public void read() {
    ImageFormat ppm = new PPMImageFormat();
    int[][] image1 = jpg.read("res/WarnerCopy.jpg");
    int[][] image2 = jpg.read("res/Warner.jpg");
    for (int i = 0; i < image1.length; i++) {
      for (int j = 0; j < image1[i].length; j++) {
        assertEquals(image1[i][j], image2[i][j]);
      }
    }


    int[][] originalImage = jpg.read("res/4x4BMPOriginal.bmp");

    int[][] expected = {
            {4, 4, 255},
            {100, 0, 0}, {100, 0, 0}, {0, 0, 0}, {0, 0, 0},
            {100, 0, 0}, {100, 0, 0}, {0, 0, 0}, {0, 0, 0},
            {0, 100, 0}, {0, 100, 0}, {255, 255, 255}, {255, 255, 255},
            {0, 100, 0}, {0, 100, 0}, {255, 255, 255}, {255, 255, 255}};

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j], originalImage[i][j]);
      }
    }


  }


  @Test
  public void testReadInvalid() {
    try {
      jpg.read("this is not a real path.jpg");
      fail("read did not throw an IllegalArgumentException when given an invalid path.");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }

    try {
      jpg.read("res/invalidJPG.jpg");
      fail("read did not throw an IllegalArgumentException when given an invalid PPM file.");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }
  }


  // DOES NOT WORK BECAUSE OF LOSSY COMPRESSION
  @Test
  public void testSave() {
    ImageFormat ppm = new PPMImageFormat();
    int[][] warner = ppm.read("res/4x4PPMOriginal.ppm");
    File someJPGFile = new File("res/oogabooga.jpg");
//    assertFalse(someJPGFile.exists());
    jpg.save("res/oogabooga.jpg", warner);
    assertTrue(someJPGFile.exists());

    // Now that we know the file was created properly, test that it was saved with the correct
    // values (i.e. ensure that the save method does not modify the image).
    // Note that we must use the read method to get the int values out of the file we saved.
    int[][] savedFile = jpg.read("res/oogabooga.jpg");


    // because JPG has lossy compression, the rgb values of the pixels will not be saved
    // the exact same as the 2d array in the model
    // as a result, we cannot verify that both the 2d arrays have equal rgb values for every pixel
    /*
    for (int i = 0; i < warner.length; i++) {
      for (int j = 0; j < warner[i].length; j++) {
        assertEquals(warner[i][j], savedFile[i][j]);
      }
    }

     */
  }

  @Test
  public void testSaveInvalid() {
    int[][] arr = {{1, 1, 255}, {255, 0, 100}};
    try { // fail to save because array is null
      jpg.save("res/Bruh.jpg", null);
      fail("save did not throw an exception when given a null for the array.");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }

    try { // fail to save because path is null
      jpg.save(null, arr);
      fail("save did not throw an exception when given a null for the path.");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }

    try { // fail to save because we are trying to save the file as something other than a ppm
      jpg.save("res/notAPPM.ppm", arr);
      fail("save did not throw an exception when attempting to save as something other than a ppm");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }
  }


}
