import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import controller.Features;
import controller.FeaturesImpl;
import controller.ImageProcessorControllerGUI;
import controller.ImageProcessorGUIController;
import imageformat.PPMImageFormat;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import operations.BrightenOrDarken;
import view.ImageProcessorGUIView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class contains tests for Features.
 */
public class FeaturesTest {
  private Features features;
  private ImageProcessorGUIController controller;
  private ImageProcessorModel model;
  private ImageProcessorGUIView view;

  @Before
  public void init() {
    this.model = new ImageProcessorModelImpl();
    this.model.loadImage("koala", new PPMImageFormat().read("res/Koala.ppm"));
    this.model.loadImage("4x4", new PPMImageFormat().read("res/4x4ppmOriginal.ppm"));
    this.model.loadImage("4x3", new PPMImageFormat().read("res/4x3ppmOriginal.ppm"));
    this.view = new GUIViewMock();
    this.features = new FeaturesImpl();
    this.controller = new ImageProcessorControllerGUI(model, view, features);
    this.features.setController(this.controller);
  }

  // For all of these tests (except setController), we test that the feature works properly by
  // ensuring that the image in the model was manipulated properly.

  @Test
  public void testBrightenOrDarkenBrighten() {
    int[][] originalKoalaColors = model.getImage("koala");

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the koala by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "koala", "image");
    this.features.brightenOrDarken(50);

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Brightened image was not added to the directory of the model properly.");
    }

    int[][] brightenedImage = model.getImage("image");


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
  public void testBrightenOrDarkenDarken() {
    int[][] originalKoalaColors = model.getImage("koala");

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the koala by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "koala", "image");
    this.features.brightenOrDarken(-50);

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Darkened image was not added to the directory of the model properly.");
    }

    int[][] darkenedImage = model.getImage("image");

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

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the koala by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "koala", "image");
    this.features.brightenOrDarken(0);

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] imageCopy = model.getImage("image");

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
  public void testGreyscale() {
    int[][] originalKoalaColors = model.getImage("koala");

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the koala by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "koala", "image");
    this.features.greyscale();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaLuma = model.getImage("image");

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

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the koala by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "koala", "image");
    this.features.sepia();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaSepia = model.getImage("image");

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
  public void testSharpen() {
    int[][] originalKoalaColors = model.getImage("koala");

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the koala by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "koala", "image");
    this.features.sharpen();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaSharp = model.getImage("image");

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

    // Similarly, we now make the image "4x4" named image
    this.model.doOperation(new BrightenOrDarken(0), "4x4", "image");
    this.features.sharpen();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] fourByFourSharp = model.getImage("image");

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
  public void testBlur() {
    int[][] originalKoalaColors = model.getImage("koala");

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the koala by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "koala", "image");
    this.features.blur();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaBlur = model.getImage("image");

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

    // Similarly, we now make the image "4x4" named image
    this.model.doOperation(new BrightenOrDarken(0), "4x4", "image");
    this.features.blur();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] fourByFourBlur = model.getImage("image");

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
  public void testFlipHorizontal() {
    int[][] flippedImageExpected = {
            {4, 4, 255},
            {0, 0, 0}, {0, 0, 0}, {100, 0, 0}, {100, 0, 0},
            {0, 0, 0}, {0, 0, 0}, {100, 0, 0}, {100, 0, 0},
            {255, 255, 255}, {255, 255, 255}, {0, 100, 0}, {0, 100, 0},
            {255, 255, 255}, {255, 255, 255}, {0, 100, 0}, {0, 100, 0}};

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the 4x4 image by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "4x4", "image");
    this.features.flipHorizontal();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] flippedImage = model.getImage("image");

    // Test that each pixel in the new image was swapped with the corresponding pixel further down
    // in the same row
    for (int i = 0; i < flippedImage.length; i++) {
      for (int j = 0; j < flippedImage[i].length; j++) {
        assertEquals(flippedImageExpected[i][j], flippedImage[i][j]);
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

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the 4x4 image by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "4x4", "image");
    this.features.flipVertical();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] flippedImage = model.getImage("image");

    // Test that each pixel in the new image was swapped with the corresponding pixel further down
    // in the same column
    for (int i = 0; i < flippedImage.length; i++) {
      for (int j = 0; j < flippedImage[i].length; j++) {
        assertEquals(flippedImageExpected[i][j], flippedImage[i][j]);
      }
    }
  }

  @Test
  public void testVisualizeRed() {
    int[][] originalKoalaColors = model.getImage("koala");

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the koala image by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "koala", "image");
    this.features.visualizeRed();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaRed = model.getImage("image");

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

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the koala image by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "koala", "image");
    this.features.visualizeGreen();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaGreen = model.getImage("image");

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

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the koala image by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "koala", "image");
    this.features.visualizeBlue();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaBlue = model.getImage("image");

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

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the koala image by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "koala", "image");
    this.features.visualizeValue();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaValue = model.getImage("image");

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

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the koala image by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "koala", "image");
    this.features.visualizeIntensity();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaIntensity = model.getImage("image");

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

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the koala image by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "koala", "image");
    this.features.visualizeLuma();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaLuma = model.getImage("image");

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
  public void testLoad() {
    // Test loading ppm
    features.load("res/4x4ppmOriginal.ppm");
    int[][] originalImage = model.getImage("image");

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

    // Test loading jpg
    features.load("res/4x4ppmOriginalToJPG.ppm");
    originalImage = model.getImage("image");

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j], originalImage[i][j]);
      }
    }

    // Test loading png
    features.load("res/4x4PNGOriginal.png");
    originalImage = model.getImage("image");

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j], originalImage[i][j]);
      }
    }
  }

  @Test
  public void testSave() {
    // The save method in the controller will attempt to save an image called "image" so we rename
    // "koala" "image" so that it will find the image. Also note that this test will only pass
    // if there is not initially an image called featuresSave.ppm in the res folder
    this.model.doOperation(new BrightenOrDarken(0), "4x4", "image");
    File somePPMFile = new File("res/featuresSave.ppm");
    assertFalse(somePPMFile.exists());
    features.save("res/featuresSave.ppm");
    assertTrue(somePPMFile.exists());

    // Now test that the file was saved properly
    int[][] expected = {
            {4, 4, 255},
            {100, 0, 0}, {100, 0, 0}, {0, 0, 0}, {0, 0, 0},
            {100, 0, 0}, {100, 0, 0}, {0, 0, 0}, {0, 0, 0},
            {0, 100, 0}, {0, 100, 0}, {255, 255, 255}, {255, 255, 255},
            {0, 100, 0}, {0, 100, 0}, {255, 255, 255}, {255, 255, 255}};
    features.load("res/featuresSave.ppm");
    int[][] actual = model.getImage("image");

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j], actual[i][j]);
      }
    }

    //
    File somePNGFile = new File("res/featuresSavePNG.png");
    assertFalse(somePNGFile.exists());
    features.save("res/featuresSavePNG.png");
    assertTrue(somePNGFile.exists());

    // Now test that the file was saved properly
    features.load("res/featuresSavePNG.ppm");
    int[][] actualPNG = model.getImage("image");

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j], actualPNG[i][j]);
      }
    }
  }

  @Test
  public void testSetController() {
    // We test setting the controller by testing if we can do any other methods in the Features
    // since they all use the controller
    this.features.setController(this.controller);

    features.load("res/4x4ppmOriginal.ppm");
    int[][] originalImage = model.getImage("image");

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

    int[][] originalKoalaColors = model.getImage("koala");

    // The doOperation method in the control will only work if there is an image named "image"
    // so we will brighten the koala image by 0 (in effect doing nothing) and name it koala so that it
    // will be manipulated when the feature calls on the controller.
    this.model.doOperation(new BrightenOrDarken(0), "koala", "image");
    this.features.visualizeLuma();

    try {
      model.getImage("image");
    } catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    int[][] koalaLuma = model.getImage("image");

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

  /**
   * A mock of the GUI-based View used for testing purposes. Its methods are not needed, it exists
   * solely so that Java application windows do not open whenever we instantiate a view. As such,
   * all of its methods do not do anything.
   */
  private class GUIViewMock implements ImageProcessorGUIView {
    @Override
    public void refresh(Image bruh) {
      return ;
    }

    @Override
    public void addFeatures(Features features) {
      return ;
    }

    @Override
    public void renderMessage(String message) throws IOException {
      return ;
    }
  }
}
