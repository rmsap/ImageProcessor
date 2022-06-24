import org.junit.Test;

import java.io.File;

import controller.Features;
import controller.FeaturesImpl;
import controller.ImageProcessorController;
import controller.ImageProcessorControllerGUI;
import controller.ImageProcessorGUIController;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import operations.BrightenOrDarken;
import view.ImageProcessorGUIView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * The class tests the ImageProcessorControllerGUI implementation.
 */
public class ImageProcessorControllerGUITest {


  @Test
  public void load() {
    // the load method in the model has been thoroughly tested
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorGUIView view = new GUIViewMock();
    Features feat = new FeaturesImpl();
    ImageProcessorGUIController controller = new ImageProcessorControllerGUI(model,
            view, feat);
    feat.setController(controller);
    try {
      model.getImage("image");
      fail("an exception was supposed to be caught");
    } catch (IllegalArgumentException e) {
      // exception successfully caught
    }
    controller.load("res/4x4ppmOriginal.ppm");
    int[][] expected = {
            {4, 4, 255},
            {100, 0, 0}, {100, 0, 0}, {0, 0, 0}, {0, 0, 0},
            {100, 0, 0}, {100, 0, 0}, {0, 0, 0}, {0, 0, 0},
            {0, 100, 0}, {0, 100, 0}, {255, 255, 255}, {255, 255, 255},
            {0, 100, 0}, {0, 100, 0}, {255, 255, 255}, {255, 255, 255}};
    for (int r = 0; r < 17; r++) {
      for (int c = 0; c < 3; c++) {
        assertEquals(expected[r][c], model.getImage("image")[r][c]);
      }
    }
  }

  @Test
  public void save() {
    // all save methods for the supported image formats have already been thoroughly tested
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorGUIView view = new GUIViewMock();
    Features feat = new FeaturesImpl();
    ImageProcessorGUIController controller = new ImageProcessorControllerGUI(model,
            view, feat);
    feat.setController(controller);
    controller.load("res/4x4ppmOriginal.ppm");
    File somePPMFile = new File("res/exampleSave.png");
    assertFalse(somePPMFile.exists());
    controller.save("res/exampleSave.png");
    assertTrue(somePPMFile.exists());


  }

  @Test
  public void doOperation() {
    // don't have to test all operations since they were thoroughly tested in the previous
    // assignments
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorGUIView view = new GUIViewMock();
    Features feat = new FeaturesImpl();
    ImageProcessorGUIController controller = new ImageProcessorControllerGUI(model,
            view, feat);
    feat.setController(controller);
    controller.load("res/4x4ppmOriginal.ppm");
    controller.doOperation(new BrightenOrDarken(-500));
    int[][] expected = {
            {4, 4, 255},
            {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0},
            {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0},
            {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0},
            {0, 00, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[0].length; j++) {
        assertEquals(expected[i][j], model.getImage("image")[i][j]);
      }
    }


  }


  @Test
  public void invalidConstruction() {
    try {
      ImageProcessorController bruh = new ImageProcessorControllerGUI(null,
              null, null);
      fail("exception was supposed to be caught");
    } catch (IllegalArgumentException e) {
      // exception successfully caught
    }
    try {
      ImageProcessorController bruh = new ImageProcessorControllerGUI(new ImageProcessorModelImpl(),
              null, new FeaturesImpl());
      fail("exception was supposed to be caught");
    } catch (IllegalArgumentException e) {
      // exception successfully caught
    }
    try {
      ImageProcessorController bruh = new ImageProcessorControllerGUI(new ImageProcessorModelImpl(),
              null, new FeaturesImpl());
      fail("exception was supposed to be caught");
    } catch (IllegalArgumentException e) {
      // exception successfully caught
    }
    try {
      ImageProcessorController bruh = new ImageProcessorControllerGUI(new ImageProcessorModelImpl(),
              new GUIViewMock(), null);
      fail("exception was supposed to be caught");
    } catch (IllegalArgumentException e) {
      // exception successfully caught
    }
  }
}