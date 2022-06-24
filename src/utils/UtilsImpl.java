package utils;

import java.awt.Image;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Represents an implementation of the Utils interface that is concrete.
 * The concrete class allows other class to call on it's utility method.
 */
public class UtilsImpl implements Utils {
  @Override
  public Image createBufferedImage(int[][] image) {
    // first convert to buffered image
    int width = image[0][0];
    int height = image[0][1];
    // for bmp have to change to rgb instead of argb or it will not save to a bmp file
    BufferedImage newImage = new BufferedImage(image[0][0], image[0][1],
            BufferedImage.TYPE_INT_RGB);
    int pixelCount = 1; // starts at index 1 since 0 just contains metaData
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        Color temp = null;
        // convert 3 values into a unified RGB value
        if (image[pixelCount].length == 4) { // if it's rgba
          temp = new Color(image[pixelCount][0], image[pixelCount][1], image[pixelCount][2],
                  image[pixelCount][3]);
        } else if (image[pixelCount].length == 3) { // if it's only rgb
          temp = new Color(image[pixelCount][0], image[pixelCount][1], image[pixelCount][2]);
        }

        int tempRGBColor = temp.getRGB();
        int tempAlphaValue = temp.getAlpha();
        // set each pixel in the buffered image to the rgb value
        newImage.setRGB(c, r, tempRGBColor);
        //          newImage.setRGB(c, r, tempAlphaValue);
        pixelCount++;
      }
    }
    return newImage;
  }
}
