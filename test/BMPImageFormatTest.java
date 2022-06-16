import org.junit.Before;
import org.junit.Test;

import java.io.File;

import imageformat.BMPImageFormat;
import imageformat.ImageFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The class represents tests for the BMPImageFormat class.
 */
public class BMPImageFormatTest {

  ImageFormat bmp = new BMPImageFormat();

  @Before
  public void initData() {
    this.bmp = new BMPImageFormat();
  }

  @Test
  public void testRead() {
    int[][] image1 = bmp.read("res/sampleBMP.bmp");
    int[][] image2 = bmp.read("res/sampleCopy.bmp");
    for (int i = 0; i < image1.length; i++) {
      for (int j = 0; j < image1[0].length; j++) {
        assertEquals(image1[i][j], image2[i][j]);
      }
    }

  }


  @Test
  public void testReadInvalid() {
    try {
      bmp.read("this is not a real path.bmp");
      fail("read did not throw an IllegalArgumentException when given an invalid path.");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }

    try {
      bmp.read("res/invalidPPM.bmp");
      fail("read did not throw an IllegalArgumentException when given an invalid BMP file.");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }
  }


  @Test
  public void testSave() {
    int[][] somePPM = bmp.read("res/sampleBMP.bmp");
    // If a file called randomPPM.ppm already exists in the res folder before these tests are run,
    // it must be deleted else these tests will fail. We are assuming that the file does not exist,
    // so we are creating it.
    File somePPMFile = new File("res/someSample.bmp");
    assertFalse(somePPMFile.exists());
    bmp.save("res/someSample.bmp", somePPM);
    assertTrue(somePPMFile.exists());

    // Now that we know the file was created properly, test that it was saved with the correct
    // values (i.e. ensure that the save method does not modify the image).
    // Note that we must use the read method to get the int values out of the file we saved.
    int[][] savedFile = bmp.read("res/someSample.bmp");

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
      bmp.save("res/Bruh.bmp", null);
      fail("save did not throw an exception when given a null for the array.");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }

    try { // fail to save because path is null
      bmp.save(null, arr);
      fail("save did not throw an exception when given a null for the path.");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }

    try { // fail to save because we are trying to save the file as something other than a ppm
      bmp.save("res/notAPPM.ppm", arr);
      fail("save did not throw an exception when attempting to save as something other than a ppm");
    } catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }
  }

}
