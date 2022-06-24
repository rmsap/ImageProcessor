package imageformat;



import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.UtilsImpl;

/**
 * This class represents an abstract image format that supports modern image formats.
 * The modern image formats that it supports includes bmp, png, and jpg files.
 */
public abstract class AbstractModernImageFormat implements ImageFormat {
  @Override
  public abstract int[][] read(String path) throws IllegalArgumentException;

  @Override
  public abstract void save(String path, int[][] image) throws IllegalArgumentException;


  protected int[][] bmpAndPNGLoad(String path) throws IllegalArgumentException {
    BufferedImage image = null;

    try {
      image = ImageIO.read(new File(path));
      //      sc = new Scanner(new FileInputStream(path));
    } catch (IOException e) {
      throw new IllegalArgumentException("File " + path + " does not exist!");
    }

    int imageHeight = image.getHeight();
    int imageWidth = image.getWidth();
    int[][] imagePixels = new int[imageWidth * imageHeight + 1][4]; // make it plus one?
    // need to account for transparency value

    // store width and height inside the first array entry
    imagePixels[0][0] = imageWidth;
    imagePixels[0][1] = imageHeight;
    imagePixels[0][2] = 255; // a filler value that we won't actually use

    int pixelCount = 1; // used to denote which indice of the array we are on
    for (int r = 0; r < imageHeight; r++) {
      for (int c = 0; c < imageWidth; c++) {
        // need to get individual rgb values from the getRGB;
        int pixelData = image.getRGB(c, r); // X and then Y so width then height
        Color colorTemp = new Color(pixelData, true);
        imagePixels[pixelCount][0] = colorTemp.getRed();
        imagePixels[pixelCount][1] = colorTemp.getGreen();
        imagePixels[pixelCount][2] = colorTemp.getBlue();
        imagePixels[pixelCount][3] = colorTemp.getAlpha();
        pixelCount++;
      }
    }

    return imagePixels;
  }


  protected Image createBufferedImage(int[][] image) {
    return new UtilsImpl().createBufferedImage(image);
    /*
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

     */
  }
}
