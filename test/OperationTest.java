import org.junit.Before;
import org.junit.Test;

import imageFormat.PPMImageFormat;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import operations.BrightenOrDarken;
import operations.FlipHorizontal;
import operations.FlipVertical;
import operations.Greyscale;
import operations.Greyscale.GreyscaleFactor;

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

    model.loadImage("koala", new PPMImageFormat().read("koala.ppm"));
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
    model.doOperation(new Greyscale(GreyscaleFactor.Red), "koala", "koala-red");

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
    model.doOperation(new Greyscale(GreyscaleFactor.Green), "koala", "koala-green");

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
    model.doOperation(new Greyscale(GreyscaleFactor.Blue), "koala", "koala-blue");

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
    model.doOperation(new Greyscale(GreyscaleFactor.Value),
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
    model.doOperation(new Greyscale(GreyscaleFactor.Intensity),
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
    model.doOperation(new Greyscale(GreyscaleFactor.Luma),
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
}
