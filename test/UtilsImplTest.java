import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import Utils.UtilsImpl;

import static org.junit.Assert.assertEquals;


/**
 * The class has the test for the utility method of UtilsImpl.
 */
public class UtilsImplTest {

  @Test
  public void createBufferedImage() {
    int[][] expected = {
            {4, 4, 255},
            {100, 0, 0}, {100, 0, 0}, {0, 0, 0}, {0, 0, 0},
            {100, 0, 0}, {100, 0, 0}, {0, 0, 0}, {0, 0, 0},
            {0, 100, 0}, {0, 100, 0}, {255, 255, 255}, {255, 255, 255},
            {0, 100, 0}, {0, 100, 0}, {255, 255, 255}, {255, 255, 255}};

    BufferedImage bruh = (BufferedImage) new UtilsImpl().createBufferedImage(expected);
    int imageHeight = 4;
    int imageWidth = 4;
    int[][] imagePixels = new int[17][3];
    int pixelCount = 1; // used to denote which indice of the array we are on
    for (int r = 0; r < imageHeight; r++) {
      for (int c = 0; c < imageWidth; c++) {
        // need to get individual rgb values from the getRGB;
        int pixelData = bruh.getRGB(c, r); // X and then Y so width then height
        Color colorTemp = new Color(pixelData, true);
        imagePixels[pixelCount][0] = colorTemp.getRed();
        imagePixels[pixelCount][1] = colorTemp.getGreen();
        imagePixels[pixelCount][2] = colorTemp.getBlue();
        pixelCount++;
      }
    }
    for (int i = 1; i < expected.length; i++) {
      for (int j = 0; j < expected[0].length; j++) {
        assertEquals(expected[i][j], imagePixels[i][j]);
      }
    }

  }
}