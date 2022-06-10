package imageformat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class represents jpg image formats, meaning it can read and save as jpg files.
 */
public class PNGImageFormat implements ImageFormat {

  @Override
  public int[][] read(String path) throws IllegalArgumentException {
    BufferedImage image = null;

    try {
      image = ImageIO.read(new File(path));
//      sc = new Scanner(new FileInputStream(path));
    } catch (IOException e) {
      throw new IllegalArgumentException("File " + path + " does not exist!");
    }

    int imageHeight = image.getHeight();
    int imageWidth = image.getWidth();
    int [][] imagePixels = new int[imageWidth * imageHeight + 1][4]; // make it plus one?
    // need to account for transparency value

    // store width and height inside the first array entry
    imagePixels[0][0] = imageWidth;
    imagePixels[0][1] = imageHeight;
    imagePixels[0][2] = 0; // a filler value that we won't actually use

    int pixelCount = 1; // used to denote which indice of the array we are on
    for(int r = 0; r < imageHeight; r++) {
      for(int c = 0; c < imageWidth; c++) {
        // need to get individual rgb values from the getRGB;
        int pixelData = image.getRGB(c,r); // X and then Y so width then height
        Color colorTemp = new Color(pixelData, true);
        imagePixels[pixelCount][0] = colorTemp.getRed();
//        System.out.println("red " + pixelCount + " :" + imagePixels[pixelCount][0]);
        imagePixels[pixelCount][1] = colorTemp.getGreen();
//        System.out.println("green " + pixelCount + " :" + imagePixels[pixelCount][1]);
        imagePixels[pixelCount][2] = colorTemp.getBlue();
//        System.out.println("blue " + pixelCount + " :" + imagePixels[pixelCount][2]);
        imagePixels[pixelCount][3] = colorTemp.getAlpha();
        System.out.println("alpha " + pixelCount + " :" + imagePixels[pixelCount][3]);
        pixelCount++;
      }
    }

    return imagePixels;
  }

  @Override
  public void save(String path, int[][] image) throws IllegalArgumentException {
    if (image == null || path == null) {
      throw new IllegalArgumentException("Failed to write to file.");
    }
    else if (!(path.substring(path.lastIndexOf('.') + 1).equalsIgnoreCase("png"))) {
      throw new IllegalArgumentException("Image is trying to be saved as something "
              + "other than png.");
    }
    // do i convert to a byte array?
    // convert the 3 values into one RGB value???
    // need to convert to BufferedImage
    // then write the bufferedImage as a jpg file
    try{ // first convert to buffered image
      int width = image[0][0];
      int height = image[0][1];
      BufferedImage newImage = new BufferedImage(image[0][0], image[0][1], BufferedImage.TYPE_INT_ARGB);
      int pixelCount = 1; // starts at index 1 since 0 just contains metaData
      for(int r = 0; r < height; r++ ) {
        for(int c = 0; c < width; c++) {
          // convert 3 values into a unified RGB value
          Color temp = new Color(image[pixelCount][0], image[pixelCount][1], image[pixelCount][2], image[pixelCount][3]);
          int tempRGBColor = temp.getRGB();
          int tempAlphaValue = temp.getAlpha();
          // set each pixel in the buffered image to the rgb value
          newImage.setRGB(c, r, tempRGBColor);
//          newImage.setRGB(c, r, tempAlphaValue);
          pixelCount++;
        }
      }
      // then save bufferedImage as a jpg file
      File output = new File(path);
      ImageIO.write(newImage, "png", output);
    }
    catch(IOException e) {
      throw new IllegalArgumentException("failed to write to file");
    }



  }

  public static void main (String[] args) {
    ImageFormat bruh = new PNGImageFormat();
    int [][] obunga = bruh.read("res/neil.png");
    System.out.print(obunga[0][0]);
    bruh.save("res/NeilBruh.png", obunga);

  }


}
