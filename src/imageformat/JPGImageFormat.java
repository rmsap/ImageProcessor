package imageformat;



import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * This class represents jpg image formats, meaning it can read and save as jpg files.
 */
public class JPGImageFormat extends AbstractModernImageFormat {
  @Override
  public int[][] read(String path) throws IllegalArgumentException {
    Scanner sc;
    BufferedImage image = null;

    try {
      image = ImageIO.read(new File(path));
    } catch (IOException e) {
      throw new IllegalArgumentException("File " + path + " does not exist!");
    }

    int imageHeight = image.getHeight();
    int imageWidth = image.getWidth();
    int[][] imagePixels = new int[imageWidth * imageHeight + 1][3]; // make it plus one?

    // store width and height inside the first array entry
    imagePixels[0][0] = imageWidth;
    imagePixels[0][1] = imageHeight;
    imagePixels[0][2] = 255; // a filler value that we won't actually use
    // we need a helper function that finds that max value of out of all the pixels

    int pixelCount = 1; // used to denote which indice of the array we are on
    for (int r = 0; r < imageHeight; r++) {
      for (int c = 0; c < imageWidth; c++) {
        // need to get individual rgb values from the getRGB;
        int pixelData = image.getRGB(c, r); // X and then Y so width then height
        Color colorTemp = new Color(pixelData, true);
        imagePixels[pixelCount][0] = colorTemp.getRed();
        imagePixels[pixelCount][1] = colorTemp.getGreen();
        imagePixels[pixelCount][2] = colorTemp.getBlue();
        pixelCount++;
      }
    }

    return imagePixels;
  }

  @Override
  public void save(String path, int[][] image) throws IllegalArgumentException {
    if (image == null || path == null) {
      throw new IllegalArgumentException("Failed to write to file.");
    } else if (!(path.substring(path.lastIndexOf('.') + 1)
            .equalsIgnoreCase("jpg"))) {
      throw new IllegalArgumentException("Image is trying to be saved as something "
              + "other than jpg.");
    }
    try {
      // first convert to buffered image
      // then save bufferedImage as a jpg file
      BufferedImage created = this.createBufferedImage(image);
      File output = new File(path);
      ImageIO.write(created, "jpg", output);
    } catch (IOException e) {
      throw new IllegalArgumentException("failed to write to file");
    }
  }
}
