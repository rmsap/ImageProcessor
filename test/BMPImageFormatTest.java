import org.junit.Before;
import org.junit.Test;

import java.io.File;

import imageformat.BMPImageFormat;
import imageformat.ImageFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BMPImageFormatTest {

  ImageFormat bmp = new BMPImageFormat();

  @Before
  public void initData() {
    this.bmp = new BMPImageFormat();
  }

  @Test
  public void testRead() {

  }


  @Test
  public void testSave() {
    int [][] somePPM = bmp.read("res/sampleBMP.bmp");
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

}
