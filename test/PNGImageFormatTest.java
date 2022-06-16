import org.junit.Before;
import org.junit.Test;

import java.io.File;

import imageformat.ImageFormat;
import imageformat.PNGImageFormat;
import imageformat.PPMImageFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The following class tests the PNGImageFormat class.
 */
public class PNGImageFormatTest {
  ImageFormat png = new PNGImageFormat();


  @Before
  public void initData() {
    this.png = new PNGImageFormat();
  }


  @Test
  public void testRead() {
    int[][] neil1 = png.read("res/neil.png");
    int[][] neil2 = png.read("res/NeilBruh.png");

    for (int i = 0; i < neil1.length; i++) {
      for (int j = 0; j < neil1[0].length; j++) {
        assertEquals(neil1[i][j], neil2[i][j]);
      }
    }
  }


  @Test
  public void testReadInvalid() {
    try {
      png.read("this is not a real path.png");
      fail("read did not throw an IllegalArgumentException when given an invalid path.");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }

    try {
      png.read("res/invalidPPM.png");
      fail("read did not throw an IllegalArgumentException when given an invalid png file.");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }
  }


  // everything works

  @Test
  public void testSave() {
    int[][] somePPM = png.read("res/neil.png");
    // If a file called randomPPM.ppm already exists in the res folder before these tests are run,
    // it must be deleted else these tests will fail. We are assuming that the file does not exist,
    // so we are creating it.
    File somePPMFile = new File("res/someNeil.png");
//    assertFalse(somePPMFile.exists());
    png.save("res/someNeil.png", somePPM);
    assertTrue(somePPMFile.exists());

    // Now that we know the file was created properly, test that it was saved with the correct
    // values (i.e. ensure that the save method does not modify the image).
    // Note that we must use the read method to get the int values out of the file we saved.
    int[][] savedFile = png.read("res/someNeil.png");

    for (int i = 0; i < somePPM.length; i++) {
      for (int j = 0; j < somePPM[i].length; j++) {
        assertEquals(somePPM[i][j], savedFile[i][j]);
      }
    }


    ImageFormat ppm = new PPMImageFormat();
    int[][] some = ppm.read("res/4x4PPMOriginal.ppm");
    // If a file called randomPPM.ppm already exists in the res folder before these tests are run,
    // it must be deleted else these tests will fail. We are assuming that the file does not exist,
    // so we are creating it.
    File wow = new File("res/4x4.png");
//    assertFalse(somePPMFile.exists());
    png.save("res/4x4.png", some);
    assertTrue(somePPMFile.exists());

    // Now that we know the file was created properly, test that it was saved with the correct
    // values (i.e. ensure that the save method does not modify the image).
    // Note that we must use the read method to get the int values out of the file we saved.
    int[][] dang = png.read("res/4x4.png");

    for (int i = 0; i < somePPM.length; i++) {
      for (int j = 0; j < somePPM[i].length; j++) {
        assertEquals(somePPM[i][j], savedFile[i][j]);
      }
    }


  }


  @Test
  public void testSaveInvalid() {
    int[][] arr = {{1, 1, 255}, {255, 0, 100}};
    try { // fail to save because array is null
      png.save("res/Bruh.png", null);
      fail("save did not throw an exception when given a null for the array.");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }

    try { // fail to save because path is null
      png.save(null, arr);
      fail("save did not throw an exception when given a null for the path.");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }

    try { // fail to save because we are trying to save the file as something other than a ppm
      png.save("res/notAPPM.jpg", arr);
      fail("save did not throw an exception when attempting to save as something other than a ppm");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }
  }


}
