import org.junit.Before;
import org.junit.Test;

import imageFormat.PPMImageFormat;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import operations.BrightenOrDarken;
import operations.VisualizeBlue;
import operations.VisualizeGreen;
import operations.VisualizeIntensity;
import operations.VisualizeRed;
import operations.VisualizeValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class contains tests for all types of Operations (e.g. anything in the operation package).
 */
public class TestOperations {
  ImageProcessorModel model;

  @Before
  public void init() {
    model = new ImageProcessorModelImpl();

    model.loadImage("koala.ppm", "koala", new PPMImageFormat());
    model.loadImage("res/4x4ppmOriginal.ppm", "4x4", new PPMImageFormat());
  }
  @Test
  public void testBrighten() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new BrightenOrDarken(50), "koala", "koala-brighten");

    try {
      model.getImage("koala-brighten");

      // Test that the header row of the new image is equal to the header row of the original image
      assertEquals(model.getImage("koala-brighten")[0][0], originalKoalaColors[0][0]);
      assertEquals(model.getImage("koala-brighten")[0][1], originalKoalaColors[0][1]);
      assertEquals(model.getImage("koala-brighten")[0][2], originalKoalaColors[0][2]);

      // Test that each pixel in the new image is brightened by a scale of 50
      // (because the BrightenOrDarken was constructed with 50 as its scale)
      for (int i = 1; i < model.getImage("koala-brighten").length; i++) {
        if (originalKoalaColors[i][0] + 50 > 255) {
          assertEquals(model.getImage("koala-brighten")[i][0], 255);
        }
        else {
          assertEquals(model.getImage("koala-brighten")[i][0],
                  originalKoalaColors[i][0] + 50);
        }

        if (originalKoalaColors[i][1] + 50 > 255) {
          assertEquals(model.getImage("koala-brighten")[i][1], 255);
        }
        else {
          assertEquals(model.getImage("koala-brighten")[i][1],
                  originalKoalaColors[i][1] + 50);
        }

        if (originalKoalaColors[i][2] + 50 > 255) {
          assertEquals(model.getImage("koala-brighten")[i][2], 255);
        }
        else {
          assertEquals(model.getImage("koala-brighten")[i][2],
                  originalKoalaColors[i][2] + 50);
        }
      }
    }
    catch (IllegalArgumentException e) {
      fail("Brightened image was not added to the directory of the model properly.");
    }
  }

  @Test
  public void testDarken() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new BrightenOrDarken(-50), "koala", "koala-darkened");

    try {
      model.getImage("koala-darkened");

      // Test that the header row of the new image is equal to the header row of the original image
      assertEquals(model.getImage("koala-darkened")[0][0], originalKoalaColors[0][0]);
      assertEquals(model.getImage("koala-darkened")[0][1], originalKoalaColors[0][1]);
      assertEquals(model.getImage("koala-darkened")[0][2], originalKoalaColors[0][2]);

      // Test that each pixel in the new image is darkened by a scale of 50
      // (because the BrightenOrDarken was constructed with -50 as its scale)
      for (int i = 1; i < model.getImage("koala-darkened").length; i++) {
        if (originalKoalaColors[i][0] - 50 < 0) {
          assertEquals(model.getImage("koala-darkened")[i][0], 0);
        }
        else {
          assertEquals(model.getImage("koala-darkened")[i][0],
                  originalKoalaColors[i][0] - 50);
        }

        if (originalKoalaColors[i][1] - 50 < 0) {
          assertEquals(model.getImage("koala-darkened")[i][1], 0);
        }
        else {
          assertEquals(model.getImage("koala-darkened")[i][1],
                  originalKoalaColors[i][1] - 50);
        }

        if (originalKoalaColors[i][2] - 50 < 0) {
          assertEquals(model.getImage("koala-darkened")[i][2], 0);
        }
        else {
          assertEquals(model.getImage("koala-darkened")[i][2],
                  originalKoalaColors[i][2] - 50);
        }
      }
    }
    catch (IllegalArgumentException e) {
      fail("Darkened image was not added to the directory of the model properly.");
    }
  }

  @Test
  public void testBrightenOrDarkenZero() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new BrightenOrDarken(0), "koala", "koala-copy");

    try {
      model.getImage("koala-copy");

      // Test that each pixel in the new image is the same as the old image
      // (brightening/darkening by 0 should have no effect)
      for (int i = 0; i < model.getImage("koala-copy").length; i++) {
        assertEquals(model.getImage("koala-copy")[i][0],
                originalKoalaColors[i][0]);
        assertEquals(model.getImage("koala-copy")[i][1],
                originalKoalaColors[i][1]);
        assertEquals(model.getImage("koala-copy")[i][2],
                originalKoalaColors[i][2]);
      }
    }
    catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }
  }

  @Test
  public void testFlipHorizontal() {

  }

  @Test
  public void testFlipVertical() {

  }

  @Test
  public void testVisualizeRed() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new VisualizeRed(), "koala", "koala-red");

    try {
      model.getImage("koala-red");
    }
    catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(model.getImage("koala-red")[0][0], originalKoalaColors[0][0]);
    assertEquals(model.getImage("koala-red")[0][1], originalKoalaColors[0][1]);
    assertEquals(model.getImage("koala-red")[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image has all 3 of its RGB values
    // set to the red value of the corresponding pixel in the original image
    for (int i = 1; i < model.getImage("koala-red").length; i++) {
      assertEquals(model.getImage("koala-red")[i][0],
              originalKoalaColors[i][0]);
      assertEquals(model.getImage("koala-red")[i][1],
              originalKoalaColors[i][0]);
      assertEquals(model.getImage("koala-red")[i][2],
              originalKoalaColors[i][0]);
    }
  }

  @Test
  public void testVisualizeGreen() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new VisualizeGreen(), "koala", "koala-green");

    try {
      model.getImage("koala-green");
    }
    catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(model.getImage("koala-green")[0][0], originalKoalaColors[0][0]);
    assertEquals(model.getImage("koala-green")[0][1], originalKoalaColors[0][1]);
    assertEquals(model.getImage("koala-green")[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image has all 3 of its RGB values
    // set to the green value of the corresponding pixel in the original image
    for (int i = 1; i < model.getImage("koala-green").length; i++) {
      assertEquals(model.getImage("koala-green")[i][0],
              originalKoalaColors[i][1]);
      assertEquals(model.getImage("koala-green")[i][1],
              originalKoalaColors[i][1]);
      assertEquals(model.getImage("koala-green")[i][2],
              originalKoalaColors[i][1]);
    }
  }

  @Test
  public void testVisualizeBlue() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new VisualizeBlue(), "koala", "koala-blue");

    try {
      model.getImage("koala-blue");
    }
    catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(model.getImage("koala-blue")[0][0], originalKoalaColors[0][0]);
    assertEquals(model.getImage("koala-blue")[0][1], originalKoalaColors[0][1]);
    assertEquals(model.getImage("koala-blue")[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image has all 3 of its RGB values
    // set to the green value of the corresponding pixel in the original image
    for (int i = 1; i < model.getImage("koala-blue").length; i++) {
      assertEquals(model.getImage("koala-blue")[i][0],
              originalKoalaColors[i][2]);
      assertEquals(model.getImage("koala-blue")[i][1],
              originalKoalaColors[i][2]);
      assertEquals(model.getImage("koala-blue")[i][2],
              originalKoalaColors[i][2]);
    }
  }

  @Test
  public void testVisualizeValue() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new VisualizeValue(), "koala", "koala-value");

    try {
      model.getImage("koala-value");
    }
    catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(model.getImage("koala-value")[0][0], originalKoalaColors[0][0]);
    assertEquals(model.getImage("koala-value")[0][1], originalKoalaColors[0][1]);
    assertEquals(model.getImage("koala-value")[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image has all 3 of its RGB values
    // set to the green value of the corresponding pixel in the original image
    for (int i = 1; i < model.getImage("koala-value").length; i++) {
      int value = Math.max(originalKoalaColors[i][0],
              Math.max(originalKoalaColors[i][1], originalKoalaColors[i][2]));
      assertEquals(model.getImage("koala-value")[i][0], value);
      assertEquals(model.getImage("koala-value")[i][1], value);
      assertEquals(model.getImage("koala-value")[i][2], value);
    }
  }

  @Test
  public void testVisualizeIntensity() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new VisualizeIntensity(), "koala", "koala-intensity");

    try {
      model.getImage("koala-intensity");
    }
    catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(model.getImage("koala-intensity")[0][0], originalKoalaColors[0][0]);
    assertEquals(model.getImage("koala-intensity")[0][1], originalKoalaColors[0][1]);
    assertEquals(model.getImage("koala-intensity")[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image has all 3 of its RGB values
    // set to the green value of the corresponding pixel in the original image
    for (int i = 1; i < model.getImage("koala-intensity").length; i++) {
      int intensity = (originalKoalaColors[i][0] + originalKoalaColors[i][1]
              + originalKoalaColors[i][2]) / 3;
      assertEquals(model.getImage("koala-intensity")[i][0], intensity);
      assertEquals(model.getImage("koala-intensity")[i][1], intensity);
      assertEquals(model.getImage("koala-intensity")[i][2], intensity);
    }
  }

  @Test
  public void testVisualizeLuma() {
    int[][] originalKoalaColors = model.getImage("koala");
    model.doOperation(new VisualizeIntensity(), "koala", "koala-luma");

    try {
      model.getImage("koala-luma");
    }
    catch (IllegalArgumentException e) {
      fail("Copied image was not added to the directory of the model properly.");
    }

    // Test that the header row of the new image is equal to the header row of the original image
    assertEquals(model.getImage("koala-luma")[0][0], originalKoalaColors[0][0]);
    assertEquals(model.getImage("koala-luma")[0][1], originalKoalaColors[0][1]);
    assertEquals(model.getImage("koala-luma")[0][2], originalKoalaColors[0][2]);

    // Test that each pixel in the new image has all 3 of its RGB values
    // set to the green value of the corresponding pixel in the original image
    for (int i = 1; i < model.getImage("koala-luma").length; i++) {
      double luma = 0.2126 * originalKoalaColors[i][0] + 0.7152 * originalKoalaColors[i][1]
              + 0.0722 * originalKoalaColors[i][2];
      assertEquals(model.getImage("koala-luma")[i][0], (int) luma);
      assertEquals(model.getImage("koala-luma")[i][1], (int) luma);
      assertEquals(model.getImage("koala-luma")[i][2], (int) luma);
    }
  }
}
