import org.junit.Before;
import org.junit.Test;

import java.io.File;

import imageFormat.ImageFormat;
import imageFormat.PPMImageFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class represents tests for ImageFormats.
 */
public class ImageFormatTest {
  ImageFormat ppm;

  @Before
  public void init() {
    ppm = new PPMImageFormat();
  }

  @Test
  public void testRead() {
    int[][] originalImage = ppm.read("res/4x4ppmOriginal.ppm");

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
      ppm.read("this is not a real path.ppm");
      fail("read did not throw an IllegalArgumentException when given an invalid path.");
    }
    catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }

    try {
      ppm.read("res/invalidPPM.ppm");
      fail("read did not throw an IllegalArgumentException when given an invalid PPM file.");
    }
    catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }
  }

  @Test
  public void testSave() {
    int[][] somePPM = {
            {3, 3, 255},
            {100, 0, 0}, {0, 100, 0}, {0, 0, 100},
            {0, 0, 100}, {0, 100, 0}, {100, 0, 0},
            {100, 0, 0}, {0, 100, 0}, {0, 0, 100}};

    // If a file called randomPPM.ppm already exists in the res folder before these tests are run,
    // it must be deleted else these tests will fail. We are assuming that the file does not exist,
    // so we are creating it.
    File somePPMFile = new File("res/somePPM.ppm");
    assertFalse(somePPMFile.exists());
    ppm.save("res/somePPM.ppm", somePPM);
    assertTrue(somePPMFile.exists());

    // Now that we know the file was created properly, test that it was saved with the correct
    // values (i.e. ensure that the save method does not modify the image).
    // Note that we must use the read method to get the int values out of the file we saved.

    int[][] savedFile = ppm.read("res/somePPM.ppm");

    for (int i = 0; i < somePPM.length; i++) {
      for (int j = 0; j < somePPM[i].length; j++) {
        assertEquals(somePPM[i][j], savedFile[i][j]);
      }
    }

    int[][] anotherPPM = {
            {2, 2, 255},
            {255, 255, 255}, {0, 0, 0},
            {0, 0, 0}, {255, 255, 255}};

    // If a file called anotherPPM.ppm does not exist in the res folder before these tests are run,
    // it must be created else these tests will fail. We are assuming that the file already exists,
    // so we are overwriting it.
    File anotherPPMFile = new File("res/anotherPPM.ppm");
    assertTrue(anotherPPMFile.exists());
    ppm.save("res/anotherPPM.ppm", anotherPPM);
    assertTrue(anotherPPMFile.exists());

    // Now that we know the file was created properly, test that it was saved with the correct
    // values (i.e. ensure that the save method does not modify the image).
    // Note that we must use the read method to get the int values out of the file we saved.
    int[][] anotherSavedFile = ppm.read("res/anotherPPM.ppm");

    for (int i = 0; i < anotherPPM.length; i++) {
      for (int j = 0; j < anotherPPM[i].length; j++) {
        assertEquals(anotherPPM[i][j], anotherSavedFile[i][j]);
      }
    }
  }

  @Test
  public void testSaveInvalid() {
    int[][] arr = {{1, 1, 255}, {255, 0, 100}};
    try {
      ppm.save("/res/this path is invalid and will not be able to save.ppm", arr);
      fail("save did not throw an exception when given an invalid path.");
    }
    catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }
    try { // fail to save because array is null
      ppm.save("/res/Bruh.ppm", null);
      fail("save did not throw an exception when given a null for the array.");
    }
    catch (IllegalArgumentException e) {
      // Exception was thrown successfully, let this test pass
    }
  }
}
