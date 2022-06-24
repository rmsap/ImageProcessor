package operations;

import model.ImageProcessorModel;

/**
 * This class represents an Operation that downscales (decreases the size of) an image.
 */
public class Downscale implements Operation {
  private final int widthPercent;
  private final int heightPercent;

  /**
   * Construct a new DownScale Operation that downscales an image to the given width and height.
   * @param widthPercent the percent to decrease the width of the image to
   * @param heightPercent the percent to decrease the height of the image to
   */
  public Downscale(int widthPercent, int heightPercent) {
    this.widthPercent = widthPercent;
    this.heightPercent = heightPercent;
  }

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int[][] image = model.getImage(name);

    int newWidth = (int) (image[0][0] * (this.widthPercent / 100.0));
    int newHeight = (int) (image[0][1] * (this.heightPercent / 100.0));

    int[][] downscaledImage = new int[newWidth * newHeight + 1][image[1].length];
    downscaledImage[0][0] = newWidth;
    downscaledImage[0][1] = newHeight;
    downscaledImage[0][2] = image[0][2];

    for (int i = 1; i < downscaledImage.length; i++) {
      int row = i / downscaledImage[0][0] + 1;
      int col = i % downscaledImage[0][0];

      // If the col is 0 we are in the last column which should be equal to the width, not 0
      if (col == 0) {
        col = downscaledImage[0][0];
      }

      double xProportion = ((double) col) / downscaledImage[0][0];
      double yProportion = ((double) row) / downscaledImage[0][1];

      double pixelInImageX = xProportion * image[0][0];
      double pixelInImageY = yProportion * image[0][1];

      int pixelAX = (int) Math.floor(pixelInImageX);
      int pixelAY = (int) Math.floor(pixelInImageY);
      int pixelA = pixelAX + image[0][0] * pixelAY;
      if (pixelA >= image.length) {
        pixelA = image.length - 1;
      }

      int pixelBX = (int) Math.ceil(pixelInImageX);
      int pixelBY = (int) Math.floor(pixelInImageY);
      int pixelB = pixelBX + image[0][0] * pixelBY;
      if (pixelB >= image.length) {
        pixelB = image.length - 1;
      }

      int pixelCX = (int) Math.floor(pixelInImageX);
      int pixelCY = (int) Math.ceil(pixelInImageY);
      int pixelC = pixelCX + image[0][0] * pixelCY;
      if (pixelC >= image.length) {
        pixelC = image.length - 1;
      }

      int pixelDX = (int) Math.ceil(pixelInImageX);
      int pixelDY = (int) Math.ceil(pixelInImageY);
      int pixelD = pixelDX + image[0][0] * pixelDY;
      if (pixelD >= image.length) {
        pixelD = image.length - 1;
      }

      for (int j = 0; j < downscaledImage[i].length; j++) {
        int ceilingX;
        int ceilingY;

        if (pixelInImageX % 1 == 0) {
          ceilingX = (int) pixelInImageX + 1;
        }
        else {
          ceilingX = (int) Math.ceil(pixelInImageX);
        }

        if (pixelInImageY % 1 == 0) {
          ceilingY = (int) pixelInImageY + 1;
        }
        else {
          ceilingY = (int) Math.ceil(pixelInImageY);
        }

        double m = image[pixelB][j]
                * (pixelInImageX - Math.floor(pixelInImageX))
                + image[pixelA][j]
                * (ceilingX - pixelInImageX);

        double n = image[pixelD][j]
                * (pixelInImageX - Math.floor(pixelInImageX))
                + image[pixelC][j]
                * (ceilingX - pixelInImageX);

        int pixelColor = (int) (n
                * (pixelInImageY - Math.floor(pixelInImageY))
                + m
                * (ceilingY - pixelInImageY));

        downscaledImage[i][j] = pixelColor;
      }
    }

    return downscaledImage;
  }
}
