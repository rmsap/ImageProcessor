import org.junit.Before;
import org.junit.Test;

import imageformat.PPMImageFormat;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import operations.BrightenOrDarken;
import operations.Downscale;
import operations.Filter;
import operations.Filter.Filters;
import operations.FlipHorizontal;
import operations.FlipVertical;
import operations.ColorTransformation;
import operations.ColorTransformation.Transformation;
import operations.VisualizeComponent;
import operations.VisualizeComponent.Component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class contains tests for all types of Operations (e.g. anything in the operation package).
 */
public class OperationTest {
  ImageProcessorModel model;

  @Before
  public void init() {
    model = new ImageProcessorModelImpl();

    model.loadImage("koala", new PPMImageFormat().read("res/Koala.ppm"));
    model.loadImage("4x4", new PPMImageFormat().read("res/4x4ppmOriginal.ppm"));
    model.loadImage("4x3", new PPMImageFormat().read("res/4x3ppmOriginal.ppm"));
  }

  @Test
  public void testBrighten() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new BrightenOrDarken(50), "koala", "koala-brighten");

    try {
      model.getImage("koala-brighten");
    } catch (IllegalArgumentException e) {
      fail("Brightened image was not added to the directory of the model properly.");
    }

    int[][] brightenedImage = model.getImage("koala-brighten");


    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(brightenedImage[0][0], originalKoalaColors[0][0]);
    assertEquals(brightenedImage[0][1], originalKoalaColors[0][1]);
    assertEquals(brightenedImage[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image is brightened by a scale of 50
    // (because the BrightenOrDarken was constructed with 50 as its scale)
    for (int i = 1; i < brightenedImage.length; i++) {
      for (int j = 0; j < brightenedImage[i].length; j++) {
        assertEquals(Math.min(originalKoalaColors[i][j] + 50, 255),
                brightenedImage[i][j]);
      }
    }
  }

  @Test
  public void testDarken() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new BrightenOrDarken(-50), "koala", "koala-darkened");

    try {
      model.getImage("koala-darkened");
    } catch (IllegalArgumentException e) {
      fail("Darkened image was not added to the directory of the model properly.");
    }

    int[][] darkenedImage = model.getImage("koala-darkened");

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(darkenedImage[0][0], originalKoalaColors[0][0]);
    assertEquals(darkenedImage[0][1], originalKoalaColors[0][1]);
    assertEquals(darkenedImage[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image is darkened by a scale of 50
    // (because the BrightenOrDarken was constructed with -50 as its scale)
    for (int i = 1; i < darkenedImage.length; i++) {
      for (int j = 0; j < darkenedImage[i].length; j++) {
        assertEquals(Math.max(0, originalKoalaColors[i][j] - 50),
                darkenedImage[i][j]);
      }
    }
  }

  @Test
  public void testBrightenOrDarkenZero() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new BrightenOrDarken(0), "koala", "koala-copy");

    try {
      model.getImage("koala-copy");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] imageCopy = model.getImage("koala-copy");

    // Test that each pixel in the new image is the same as the old image
    // (brightening/darkening by 0 should have no effect)
    for (int i = 0; i < imageCopy.length; i++) {
      assertEquals(imageCopy[i][0],
              originalKoalaColors[i][0]);
      assertEquals(imageCopy[i][1],
              originalKoalaColors[i][1]);
      assertEquals(imageCopy[i][2],
              originalKoalaColors[i][2]);
    }
  }

  @Test
  public void testFlipHorizontal() {
    int[][] flippedImageExpected = {
            {4, 4, 255},
            {0, 0, 0}, {0, 0, 0}, {100, 0, 0}, {100, 0, 0},
            {0, 0, 0}, {0, 0, 0}, {100, 0, 0}, {100, 0, 0},
            {255, 255, 255}, {255, 255, 255}, {0, 100, 0}, {0, 100, 0},
            {255, 255, 255}, {255, 255, 255}, {0, 100, 0}, {0, 100, 0}};

    model.doOperation(new FlipHorizontal(), "4x4", "4x4-flipped");

    try {
      model.getImage("4x4-flipped");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] flippedImage = model.getImage("4x4-flipped");

    // Test that each pixel in the new image was swapped with the corresponding pixel further down
    // in the same row
    for (int i = 0; i < flippedImage.length; i++) {
      for (int j = 0; j < flippedImage[i].length; j++) {
        assertEquals(flippedImageExpected[i][j], flippedImage[i][j]);
      }
    }

    int[][] flippedImageExpected2 = {
            {4, 3, 255},
            {0, 0, 0}, {0, 0, 0}, {100, 0, 0}, {100, 0, 0},
            {100, 0, 0}, {100, 0, 0}, {0, 100, 0}, {0, 100, 0},
            {0, 100, 0}, {0, 100, 0}, {255, 255, 255}, {255, 255, 255}};

    model.doOperation(new FlipHorizontal(), "4x3", "4x3-flipped-hor");
    try {
      model.getImage("4x3-flipped-hor");
    } catch (IllegalArgumentException e) {
      fail("An exception was not supposed to be caught");
    }
    int[][] flippedImage2 = model.getImage("4x3-flipped-hor");
    // Test that each pixel in the new image was swapped with the corresponding pixel further down
    // in the same row
    for (int i = 0; i < flippedImage2.length; i++) {
      for (int j = 0; j < flippedImage2[i].length; j++) {
        assertEquals(flippedImageExpected2[i][j], flippedImage2[i][j]);
      }
    }

    int[][] flippedImageExpected3 = {
            {3, 2, 255},
            {100, 100, 100}, {255, 255, 255}, {0, 0, 0},
            {25, 25, 25}, {0, 0, 0}, {255, 255, 255}};

    model.loadImage("3x2", new PPMImageFormat().read("res/3x2.ppm"));
    model.doOperation(new FlipHorizontal(), "3x2", "3x2flippedHorizontal");

    int[][] flippedImage3 = model.getImage("3x2flippedHorizontal");
    // Test that each pixel in the new image was swapped with the corresponding pixel further down
    // in the same row
    for (int i = 0; i < flippedImage3.length; i++) {
      for (int j = 0; j < flippedImage3[i].length; j++) {
        assertEquals(flippedImageExpected3[i][j], flippedImage3[i][j]);
      }
    }
  }

  @Test
  public void testFlipVertical() {
    int[][] flippedImageExpected = {
            {4, 4, 255},
            {0, 100, 0}, {0, 100, 0}, {255, 255, 255}, {255, 255, 255},
            {0, 100, 0}, {0, 100, 0}, {255, 255, 255}, {255, 255, 255},
            {100, 0, 0}, {100, 0, 0}, {0, 0, 0}, {0, 0, 0},
            {100, 0, 0}, {100, 0, 0}, {0, 0, 0}, {0, 0, 0}};

    model.doOperation(new FlipVertical(), "4x4", "4x4-flipped");

    try {
      model.getImage("4x4-flipped");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] flippedImage = model.getImage("4x4-flipped");

    // Test that each pixel in the new image was swapped with the corresponding pixel further down
    // in the same column
    for (int i = 0; i < flippedImage.length; i++) {
      for (int j = 0; j < flippedImage[i].length; j++) {
        assertEquals(flippedImageExpected[i][j], flippedImage[i][j]);
      }
    }

    int[][] flippedImageExpected2 = {
            {4, 3, 255},
            {255, 255, 255}, {255, 255, 255}, {0, 100, 0}, {0, 100, 0},
            {0, 100, 0}, {0, 100, 0}, {100, 0, 0}, {100, 0, 0},
            {100, 0, 0}, {100, 0, 0}, {0, 0, 0}, {0, 0, 0},
    };
    model.doOperation(new FlipVertical(), "4x3", "4x3-vertical");
    try {
      model.getImage("4x3-vertical");
    } catch (IllegalArgumentException e) {
      fail("An exception was not supposed to be caught");
    }
    int[][] flippedImage2 = model.getImage("4x3-vertical");
    // Test that each pixel in the new image was swapped with the corresponding pixel further down
    // in the same column
    for (int i = 0; i < flippedImage2.length; i++) {
      for (int j = 0; j < flippedImage2[i].length; j++) {
        assertEquals(flippedImageExpected2[i][j], flippedImage2[i][j]);
      }
    }

  }

  @Test
  public void testVisualizeRed() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new VisualizeComponent(Component.Red), "koala", "koala-red");

    try {
      model.getImage("koala-red");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaRed = model.getImage("koala-red");

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(koalaRed[0][0], originalKoalaColors[0][0]);
    assertEquals(koalaRed[0][1], originalKoalaColors[0][1]);
    assertEquals(koalaRed[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image has all 3 of its RGB values
    // set to the red value of the corresponding pixel in the original image
    for (int i = 1; i < koalaRed.length; i++) {
      assertEquals(koalaRed[i][0],
              originalKoalaColors[i][0]);
      assertEquals(koalaRed[i][1],
              originalKoalaColors[i][0]);
      assertEquals(koalaRed[i][2],
              originalKoalaColors[i][0]);
    }
  }

  @Test
  public void testVisualizeGreen() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new VisualizeComponent(Component.Green), "koala", "koala-green");

    try {
      model.getImage("koala-green");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaGreen = model.getImage("koala-green");

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(koalaGreen[0][0], originalKoalaColors[0][0]);
    assertEquals(koalaGreen[0][1], originalKoalaColors[0][1]);
    assertEquals(koalaGreen[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image has all 3 of its RGB values
    // set to the green value of the corresponding pixel in the original image
    for (int i = 1; i < koalaGreen.length; i++) {
      assertEquals(koalaGreen[i][0],
              originalKoalaColors[i][1]);
      assertEquals(koalaGreen[i][1],
              originalKoalaColors[i][1]);
      assertEquals(koalaGreen[i][2],
              originalKoalaColors[i][1]);
    }
  }

  @Test
  public void testVisualizeBlue() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new VisualizeComponent(Component.Blue), "koala", "koala-blue");

    try {
      model.getImage("koala-blue");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaBlue = model.getImage("koala-blue");

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(koalaBlue[0][0], originalKoalaColors[0][0]);
    assertEquals(koalaBlue[0][1], originalKoalaColors[0][1]);
    assertEquals(koalaBlue[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image has all 3 of its RGB values
    // set to the green value of the corresponding pixel in the original image
    for (int i = 1; i < koalaBlue.length; i++) {
      assertEquals(koalaBlue[i][0],
              originalKoalaColors[i][2]);
      assertEquals(koalaBlue[i][1],
              originalKoalaColors[i][2]);
      assertEquals(koalaBlue[i][2],
              originalKoalaColors[i][2]);
    }
  }

  @Test
  public void testVisualizeValue() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new VisualizeComponent(Component.Value),
            "koala", "koala-value");

    try {
      model.getImage("koala-value");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaValue = model.getImage("koala-value");

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(koalaValue[0][0], originalKoalaColors[0][0]);
    assertEquals(koalaValue[0][1], originalKoalaColors[0][1]);
    assertEquals(koalaValue[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image has all 3 of its RGB values
    // set to the green value of the corresponding pixel in the original image
    for (int i = 1; i < koalaValue.length; i++) {
      int value = Math.max(originalKoalaColors[i][0],
              Math.max(originalKoalaColors[i][1], originalKoalaColors[i][2]));
      assertEquals(koalaValue[i][0], value);
      assertEquals(koalaValue[i][1], value);
      assertEquals(koalaValue[i][2], value);
    }
  }

  @Test
  public void testVisualizeIntensity() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new VisualizeComponent(Component.Intensity),
            "koala", "koala-intensity");

    try {
      model.getImage("koala-intensity");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaIntensity = model.getImage("koala-intensity");

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(koalaIntensity[0][0], originalKoalaColors[0][0]);
    assertEquals(koalaIntensity[0][1], originalKoalaColors[0][1]);
    assertEquals(koalaIntensity[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image has all 3 of its RGB values
    // set to the green value of the corresponding pixel in the original image
    for (int i = 1; i < koalaIntensity.length; i++) {
      int intensity = (originalKoalaColors[i][0] + originalKoalaColors[i][1]
              + originalKoalaColors[i][2]) / 3;
      assertEquals(koalaIntensity[i][0], intensity);
      assertEquals(koalaIntensity[i][1], intensity);
      assertEquals(koalaIntensity[i][2], intensity);
    }
  }

  @Test
  public void testVisualizeLuma() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new VisualizeComponent(Component.Luma),
            "koala", "koala-luma");

    try {
      model.getImage("koala-luma");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaLuma = model.getImage("koala-luma");

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(koalaLuma[0][0], originalKoalaColors[0][0]);
    assertEquals(koalaLuma[0][1], originalKoalaColors[0][1]);
    assertEquals(koalaLuma[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image has all 3 of its RGB values
    // set to the green value of the corresponding pixel in the original image
    for (int i = 1; i < koalaLuma.length; i++) {
      double luma = 0.2126 * originalKoalaColors[i][0] + 0.7152 * originalKoalaColors[i][1]
              + 0.0722 * originalKoalaColors[i][2];
      assertEquals(koalaLuma[i][0], (int) luma);
      assertEquals(koalaLuma[i][1], (int) luma);
      assertEquals(koalaLuma[i][2], (int) luma);
    }
  }

  @Test
  public void testGreyscale() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new ColorTransformation(Transformation.Greyscale),
            "koala", "koala-luma");

    try {
      model.getImage("koala-luma");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaLuma = model.getImage("koala-luma");

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(koalaLuma[0][0], originalKoalaColors[0][0]);
    assertEquals(koalaLuma[0][1], originalKoalaColors[0][1]);
    assertEquals(koalaLuma[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image has all 3 of its RGB values
    // set to the green value of the corresponding pixel in the original image
    for (int i = 1; i < koalaLuma.length; i++) {
      double luma = 0.2126 * originalKoalaColors[i][0] + 0.7152 * originalKoalaColors[i][1]
              + 0.0722 * originalKoalaColors[i][2];
      assertEquals(koalaLuma[i][0], (int) luma);
      assertEquals(koalaLuma[i][1], (int) luma);
      assertEquals(koalaLuma[i][2], (int) luma);
    }
  }

  @Test
  public void testSepia() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new ColorTransformation(Transformation.Sepia), "koala",
            "koala-sepia");

    try {
      model.getImage("koala-sepia");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaSepia = model.getImage("koala-sepia");

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(koalaSepia[0][0], originalKoalaColors[0][0]);
    assertEquals(koalaSepia[0][1], originalKoalaColors[0][1]);
    assertEquals(koalaSepia[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image had the appropriate color transformation applied to it.
    for (int i = 1; i < koalaSepia.length; i++) {
      assertEquals(Math.min((int) (0.393 * originalKoalaColors[i][0] + 0.769 *
              originalKoalaColors[i][1]
              + 0.189 * originalKoalaColors[i][2]), 255), koalaSepia[i][0]);
      assertEquals(Math.min((int) (0.349 * originalKoalaColors[i][0] + 0.686 *
              originalKoalaColors[i][1]
              + 0.168 * originalKoalaColors[i][2]), 255), koalaSepia[i][1]);
      assertEquals(Math.min((int) (0.272 * originalKoalaColors[i][0] + 0.534 *
              originalKoalaColors[i][1]
              + 0.131 * originalKoalaColors[i][2]), 255), koalaSepia[i][2]);
    }
  }

  @Test
  public void testBlur() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new Filter(Filters.Blur), "koala", "koala-blur");

    try {
      model.getImage("koala-blur");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaBlur = model.getImage("koala-blur");

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(koalaBlur[0][0], originalKoalaColors[0][0]);
    assertEquals(koalaBlur[0][1], originalKoalaColors[0][1]);
    assertEquals(koalaBlur[0][2], originalKoalaColors[0][2]);

    double[][] kernel = {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};
    int kernelRows = kernel.length;
    int numPixelsPerRow = originalKoalaColors.length / originalKoalaColors[0][1];

    // Iterate through every pixel
    for (int i = 1; i < originalKoalaColors.length; i++) {
      // Iterate through each color channel of the ith pixel
      for (int j = 0; j < 3; j++) {
        double newColor = 0;
        // Iterate through each row of the kernel
        for (int k = 0; k < kernelRows; k++) {
          int multiplicativeFactor = k - kernelRows / 2;
          int centerPixelInRowToChange = i + (multiplicativeFactor * numPixelsPerRow);
          // Iterate through each column of the given row in the kernel
          for (int l = 0; l < kernel[k].length; l++) {
            int numLeftOrRight = l - kernel[k].length / 2;
            int lastPixelInRow;

            if (i % numPixelsPerRow != 0) {
              lastPixelInRow = i + (numPixelsPerRow - (i % numPixelsPerRow)); // math bad
            } else {
              lastPixelInRow = i;
            }

            if ((i + numLeftOrRight <= lastPixelInRow)
                    && !(i % numPixelsPerRow <= Math.abs(numLeftOrRight) && i % numPixelsPerRow != 0
                    && numLeftOrRight < 0)
                    && centerPixelInRowToChange + numLeftOrRight > 0
                    && centerPixelInRowToChange + numLeftOrRight < originalKoalaColors.length
                    && centerPixelInRowToChange > 0
                    && centerPixelInRowToChange < originalKoalaColors.length) {
              newColor += kernel[k][l]
                      * originalKoalaColors[centerPixelInRowToChange + numLeftOrRight][j];
            }
          }
        }
        if (newColor > 255) {
          newColor = 255;
        } else if (newColor < 0) {
          newColor = 0;
        }

        assertEquals((int) newColor, koalaBlur[i][j]);
        originalKoalaColors[i][j] = (int) newColor;
      }
    }

    model.doOperation(new Filter(Filters.Blur), "4x4", "4x4-blur");

    try {
      model.getImage("4x4-blur");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] fourByFourBlur = model.getImage("4x4-blur");

    assertEquals(4, fourByFourBlur[0][0]);
    assertEquals(4, fourByFourBlur[0][1]);
    assertEquals(255, fourByFourBlur[0][2]);

    assertEquals(56, fourByFourBlur[1][0]);
    assertEquals(0, fourByFourBlur[1][1]);
    assertEquals(0, fourByFourBlur[1][2]);

    assertEquals(50, fourByFourBlur[2][0]);
    assertEquals(0, fourByFourBlur[2][1]);
    assertEquals(0, fourByFourBlur[2][2]);

    assertEquals(12, fourByFourBlur[3][0]);
    assertEquals(0, fourByFourBlur[3][1]);
    assertEquals(0, fourByFourBlur[3][2]);

    assertEquals(1, fourByFourBlur[4][0]);
    assertEquals(0, fourByFourBlur[4][1]);
    assertEquals(0, fourByFourBlur[4][2]);

    assertEquals(47, fourByFourBlur[5][0]);
    assertEquals(18, fourByFourBlur[5][1]);
    assertEquals(0, fourByFourBlur[5][2]);

    assertEquals(57, fourByFourBlur[6][0]);
    assertEquals(36, fourByFourBlur[6][1]);
    assertEquals(15, fourByFourBlur[6][2]);

    assertEquals(59, fourByFourBlur[7][0]);
    assertEquals(58, fourByFourBlur[7][1]);
    assertEquals(49, fourByFourBlur[7][2]);

    assertEquals(56, fourByFourBlur[8][0]);
    assertEquals(55, fourByFourBlur[8][1]);
    assertEquals(53, fourByFourBlur[8][2]);

    assertEquals(9, fourByFourBlur[9][0]);
    assertEquals(60, fourByFourBlur[9][1]);
    assertEquals(0, fourByFourBlur[9][2]);

    assertEquals(62, fourByFourBlur[10][0]);
    assertEquals(108, fourByFourBlur[10][1]);
    assertEquals(52, fourByFourBlur[10][2]);

    assertEquals(165, fourByFourBlur[11][0]);
    assertEquals(176, fourByFourBlur[11][1]);
    assertEquals(160, fourByFourBlur[11][2]);

    assertEquals(142, fourByFourBlur[12][0]);
    assertEquals(144, fourByFourBlur[12][1]);
    assertEquals(141, fourByFourBlur[12][2]);

    assertEquals(5, fourByFourBlur[13][0]);
    assertEquals(51, fourByFourBlur[13][1]);
    assertEquals(3, fourByFourBlur[13][2]);

    assertEquals(51, fourByFourBlur[14][0]);
    assertEquals(91, fourByFourBlur[14][1]);
    assertEquals(48, fourByFourBlur[14][2]);

    assertEquals(135, fourByFourBlur[15][0]);
    assertEquals(144, fourByFourBlur[15][1]);
    assertEquals(133, fourByFourBlur[15][2]);

    assertEquals(108, fourByFourBlur[16][0]);
    assertEquals(110, fourByFourBlur[16][1]);
    assertEquals(108, fourByFourBlur[16][2]);
  }

  @Test
  public void testSharpen() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new Filter(Filters.Sharpen), "koala", "koala-sharp");

    try {
      model.getImage("koala-sharp");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaSharp = model.getImage("koala-sharp");

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(koalaSharp[0][0], originalKoalaColors[0][0]);
    assertEquals(koalaSharp[0][1], originalKoalaColors[0][1]);
    assertEquals(koalaSharp[0][2], originalKoalaColors[0][2]);

    double[][] kernel = {
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}};
    int kernelRows = kernel.length;
    int numPixelsPerRow = originalKoalaColors.length / originalKoalaColors[0][1];

    // Iterate through every pixel
    for (int i = 1; i < originalKoalaColors.length; i++) {
      // Iterate through each color channel of the ith pixel
      for (int j = 0; j < 3; j++) {
        double newColor = 0;
        // Iterate through each row of the kernel
        for (int k = 0; k < kernelRows; k++) {
          int multiplicativeFactor = k - kernelRows / 2;
          int centerPixelInRowToChange = i + (multiplicativeFactor * numPixelsPerRow);
          // Iterate through each column of the given row in the kernel
          for (int l = 0; l < kernel[k].length; l++) {
            int numLeftOrRight = l - kernel[k].length / 2;
            int lastPixelInRow;

            if (i % numPixelsPerRow != 0) {
              lastPixelInRow = i + (numPixelsPerRow - (i % numPixelsPerRow)); // math bad
            } else {
              lastPixelInRow = i;
            }

            if ((i + numLeftOrRight <= lastPixelInRow)
                    && !(i % numPixelsPerRow <= Math.abs(numLeftOrRight) && i % numPixelsPerRow != 0
                    && numLeftOrRight < 0)
                    && centerPixelInRowToChange + numLeftOrRight > 0
                    && centerPixelInRowToChange + numLeftOrRight < originalKoalaColors.length
                    && centerPixelInRowToChange > 0
                    && centerPixelInRowToChange < originalKoalaColors.length) {
              newColor += (kernel[k][l]
                      * originalKoalaColors[centerPixelInRowToChange + numLeftOrRight][j]);
            }


          }
        }
        if (newColor > 255) {
          newColor = 255;
        } else if (newColor < 0) {
          newColor = 0;
        }

        assertEquals((int) newColor, koalaSharp[i][j]);
        originalKoalaColors[i][j] = (int) newColor;
      }
    }

    model.doOperation(new Filter(Filters.Sharpen), "4x4", "4x4-sharp");

    try {
      model.getImage("4x4-sharp");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] fourByFourSharp = model.getImage("4x4-sharp");

    assertEquals(4, fourByFourSharp[0][0]);
    assertEquals(4, fourByFourSharp[0][1]);
    assertEquals(255, fourByFourSharp[0][2]);

    assertEquals(143, fourByFourSharp[1][0]);
    assertEquals(0, fourByFourSharp[1][1]);
    assertEquals(0, fourByFourSharp[1][2]);

    assertEquals(122, fourByFourSharp[2][0]);
    assertEquals(0, fourByFourSharp[2][1]);
    assertEquals(0, fourByFourSharp[2][2]);

    assertEquals(0, fourByFourSharp[3][0]);
    assertEquals(0, fourByFourSharp[3][1]);
    assertEquals(0, fourByFourSharp[3][2]);

    assertEquals(0, fourByFourSharp[4][0]);
    assertEquals(0, fourByFourSharp[4][1]);
    assertEquals(0, fourByFourSharp[4][2]);

    assertEquals(127, fourByFourSharp[5][0]);
    assertEquals(0, fourByFourSharp[5][1]);
    assertEquals(0, fourByFourSharp[5][2]);

    assertEquals(166, fourByFourSharp[6][0]);
    assertEquals(0, fourByFourSharp[6][1]);
    assertEquals(0, fourByFourSharp[6][2]);

    assertEquals(102, fourByFourSharp[7][0]);
    assertEquals(51, fourByFourSharp[7][1]);
    assertEquals(63, fourByFourSharp[7][2]);

    assertEquals(53, fourByFourSharp[8][0]);
    assertEquals(51, fourByFourSharp[8][1]);
    assertEquals(79, fourByFourSharp[8][2]);

    assertEquals(0, fourByFourSharp[9][0]);
    assertEquals(104, fourByFourSharp[9][1]);
    assertEquals(0, fourByFourSharp[9][2]);

    assertEquals(122, fourByFourSharp[10][0]);
    assertEquals(246, fourByFourSharp[10][1]);
    assertEquals(69, fourByFourSharp[10][2]);

    assertEquals(255, fourByFourSharp[11][0]);
    assertEquals(255, fourByFourSharp[11][1]);
    assertEquals(255, fourByFourSharp[11][2]);

    assertEquals(255, fourByFourSharp[12][0]);
    assertEquals(255, fourByFourSharp[12][1]);
    assertEquals(255, fourByFourSharp[12][2]);

    assertEquals(0, fourByFourSharp[13][0]);
    assertEquals(142, fourByFourSharp[13][1]);
    assertEquals(0, fourByFourSharp[13][2]);

    assertEquals(38, fourByFourSharp[14][0]);
    assertEquals(255, fourByFourSharp[14][1]);
    assertEquals(63, fourByFourSharp[14][2]);

    assertEquals(255, fourByFourSharp[15][0]);
    assertEquals(255, fourByFourSharp[15][1]);
    assertEquals(255, fourByFourSharp[15][2]);

    assertEquals(255, fourByFourSharp[16][0]);
    assertEquals(255, fourByFourSharp[16][1]);
    assertEquals(255, fourByFourSharp[16][2]);
  }

  @Test
  public void testDownscale() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new Downscale(80, 80), "koala", "koala-80");

    try {
      model.getImage("koala-80");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaEighty = model.getImage("koala-80");

    // Test that the width and height were changed properly
    assertEquals((int) (0.8 * originalKoalaColors[0][0]), koalaEighty[0][0]);
    assertEquals((int) (0.8 * originalKoalaColors[0][1]), koalaEighty[0][1]);
    assertEquals(originalKoalaColors[0][2], koalaEighty[0][2]);

    // Now test that each pixel was given its new value properly
    int newWidth = (int) (originalKoalaColors[0][0] * 0.8);
    int newHeight = (int) (originalKoalaColors[0][1] * 0.8);

    for (int i = 1; i < newWidth * newHeight + 1; i++) {
      int row = i / newWidth + 1;
      int col = i % newWidth;

      if (col == 0) {
        col = newWidth;
      }

      double xProportion = ((double) col) / newWidth;
      double yProportion = ((double) row) / newHeight;

      double pixelInImageX = xProportion * originalKoalaColors[0][0];
      double pixelInImageY = yProportion * originalKoalaColors[0][1];

      int pixelAX = (int) Math.floor(pixelInImageX);
      int pixelAY = (int) Math.floor(pixelInImageY);
      int pixelA = pixelAX + originalKoalaColors[0][0] * pixelAY;
      if (pixelA >= originalKoalaColors.length) {
        pixelA = originalKoalaColors.length - 1;
      }

      int pixelBX = (int) Math.ceil(pixelInImageX);
      int pixelBY = (int) Math.floor(pixelInImageY);
      int pixelB = pixelBX + originalKoalaColors[0][0] * pixelBY;
      if (pixelB >= originalKoalaColors.length) {
        pixelB = originalKoalaColors.length - 1;
      }

      int pixelCX = (int) Math.floor(pixelInImageX);
      int pixelCY = (int) Math.ceil(pixelInImageY);
      int pixelC = pixelCX + originalKoalaColors[0][0] * pixelCY;
      if (pixelC >= originalKoalaColors.length) {
        pixelC = originalKoalaColors.length - 1;
      }

      int pixelDX = (int) Math.ceil(pixelInImageX);
      int pixelDY = (int) Math.ceil(pixelInImageY);
      int pixelD = pixelDX + originalKoalaColors[0][0] * pixelDY;
      if (pixelD >= originalKoalaColors.length) {
        pixelD = originalKoalaColors.length - 1;
      }

      for (int j = 0; j < 3; j++) {
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

        double m = originalKoalaColors[pixelB][j]
                * (pixelInImageX - Math.floor(pixelInImageX))
                + originalKoalaColors[pixelA][j]
                * (ceilingX - pixelInImageX);

        double n = originalKoalaColors[pixelD][j]
                * (pixelInImageX - Math.floor(pixelInImageX))
                + originalKoalaColors[pixelC][j]
                * (ceilingX - pixelInImageX);

        int pixelColor = (int) (n
                * (pixelInImageY - Math.floor(pixelInImageY))
                + m
                * (ceilingY - pixelInImageY));

        assertEquals(pixelColor, koalaEighty[i][j]);
      }
    }
  }
}
