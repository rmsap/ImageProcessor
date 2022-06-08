import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import imageFormat.ImageFormat;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import operations.Operation;
import view.ImageProcessorTextView;
import view.ImageProcessorView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ImageProcessorControllerImplTest {

  public class MockImageProcessorModel implements ImageProcessorModel {

    private StringBuilder log;

    public MockImageProcessorModel(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void loadImage(String name, int[][] image) {
      log.append(String.format("Name = %s\n", name));

    }

    @Override
    public int[][] getImage(String name) {
      log.append(String.format("Name = %s\n", name));
      return new int[0][0];
    }

    @Override
    public void doOperation(Operation op, String name, String dest) {
      log.append(String.format("Name = %s, Destination = %s\n", name, dest));

    }
  }

  @Before
  public void initData() {

  }

  @Test
  public void inputsToLoadImage() throws IllegalStateException {
    try {
      Readable readable = new StringReader("load Koala.ppm Koala q"); // valid move
      Appendable appendable = new StringBuilder();
      StringBuilder log = new StringBuilder();
      ImageProcessorModel mockModel = new MockImageProcessorModel(log);
      ImageProcessorView view = new ImageProcessorTextView(mockModel, appendable);
      ImageProcessorController controller =
              new ImageProcessorControllerImpl(mockModel, view, readable);
      controller.execute();
      assertEquals("Name = Koala\n", log.toString());


    } catch (IllegalStateException ie) {
      fail("An exception was not supposed to be caught");
    }
  }

  @Test
  public void inputsToSaveImage() throws IllegalStateException {
    try { // getImage() is also indirectly tested in this test
      // NOTE:
      // Running this test will cause a blank ppm file to be saved that has nothing in it
      // and trying to load this created file while using the image processor via the console
      // will result in a NoSuchElement Exception, so do not load the file created from the test
      Readable readable = new StringReader("save Bruh.ppm Bruh q"); // valid move
      Appendable appendable = new StringBuilder();
      StringBuilder log = new StringBuilder();
      ImageProcessorModel mockModel = new MockImageProcessorModel(log);
      ImageProcessorView view = new ImageProcessorTextView(mockModel, appendable);
      ImageProcessorController controller =
              new ImageProcessorControllerImpl(mockModel, view, readable);
      controller.execute();
      assertEquals("Name = Bruh\nName = Bruh\n", log.toString());

    } catch (IllegalStateException is) {
      fail("An exception was not supposed to be caught");
    }
  }

  @Test
  public void inputsToOperation() throws IllegalStateException {
    try {
      Readable readable = new StringReader("flip-horizontal Bruh BruhVert q"); // valid move
      Appendable appendable = new StringBuilder();
      StringBuilder log = new StringBuilder();
      ImageProcessorModel mockModel = new MockImageProcessorModel(log);
      ImageProcessorView view = new ImageProcessorTextView(mockModel, appendable);
      ImageProcessorController controller =
              new ImageProcessorControllerImpl(mockModel, view, readable);
      controller.execute();
      assertEquals("Name = Bruh\nName = Bruh, Destination = BruhVert\n", log.toString());
    } catch (IllegalStateException is) {
      fail("An exception was not supposed to be caught");
    }
    try {
      Readable readable = new StringReader("change-brightness 64 Bruh BruhVert q"); // valid move
      Appendable appendable = new StringBuilder();
      StringBuilder log = new StringBuilder();
      ImageProcessorModel mockModel = new MockImageProcessorModel(log);
      ImageProcessorView view = new ImageProcessorTextView(mockModel, appendable);
      ImageProcessorController controller =
              new ImageProcessorControllerImpl(mockModel, view, readable);
      controller.execute();
      assertEquals("Name = Bruh\nName = Bruh, Destination = BruhVert\n", log.toString());
    } catch (IllegalStateException is) {
      fail("An exception was not supposed to be caught");
    }
  }

  @Test
  public void testOutputsSentToViewAppendable() throws IllegalStateException {

    // test that an invalid command line is being sent


    // test that the operation message is sent


    try { // test that the quit message is being sent
      Readable readable = new StringReader("q"); // valid move
      Appendable appendable = new StringBuilder();
      ImageProcessorModel mockModel = new ImageProcessorModelImpl();
      ImageProcessorView view = new ImageProcessorTextView(mockModel, appendable);
      ImageProcessorController controller =
              new ImageProcessorControllerImpl(mockModel, view, readable);
      controller.execute();
      String[] messages = appendable.toString().split("\n");
      String quitMessage = messages[23];
      assertEquals("Image Processor has quit.", quitMessage);
//      System.out.println(messages.length);

    } catch (IllegalStateException is) {
      fail("An exception was not supposed to be caught");
    }
    try { // test that the load image is sent
      Readable readable = new StringReader("load res/4x4ppmOriginal.ppm Koala q"); // valid move
      Appendable appendable = new StringBuilder();
      ImageProcessorModel mockModel = new ImageProcessorModelImpl();
      ImageProcessorView view = new ImageProcessorTextView(mockModel, appendable);
      ImageProcessorController controller =
              new ImageProcessorControllerImpl(mockModel, view, readable);
      controller.execute();
      String[] messages = appendable.toString().split("\n");
      String loadMessage = messages[23];
      assertEquals("Image has been loaded", loadMessage);
    } catch (IllegalStateException is) {
      fail("An exception was not supposed to be caught");
    }
    try { // test that the save is being sent
      Readable readable = new StringReader("load res/4x4ppmOriginal.ppm " +
              "Koala save Kool.ppm Koala q");
      Appendable appendable = new StringBuilder();
      ImageProcessorModel mockModel = new ImageProcessorModelImpl();
      ImageProcessorView view = new ImageProcessorTextView(mockModel, appendable);
      ImageProcessorController controller =
              new ImageProcessorControllerImpl(mockModel, view, readable);
      controller.execute();
      String[] messages = appendable.toString().split("\n");
      String saveMessage = messages[24];
      assertEquals("Image has been saved", saveMessage);
    } catch (IllegalStateException is) {
      fail("An exception was not supposed to be caught");
    }
    try { // test that the file doesn't exist message is being sent
      Readable readable = new StringReader("load Bruhlick.ppm Bruh q");
      Appendable appendable = new StringBuilder();
      ImageProcessorModel mockModel = new ImageProcessorModelImpl();
      ImageProcessorView view = new ImageProcessorTextView(mockModel, appendable);
      ImageProcessorController controller =
              new ImageProcessorControllerImpl(mockModel, view, readable);
      controller.execute();
      String[] messages = appendable.toString().split("\n");
      String fileMessage = messages[23];
      assertEquals("File doesn't exist, re-enter a valid command", fileMessage);
    } catch (IllegalStateException is) {
      fail("An exception was not supposed to be caught");
    }
    try { // test that the operation performed message is sent
      Readable readable = new StringReader("load res/4x4ppmOriginal.ppm Koala flip-horizontal " +
              "Koala KoalaHor q");
      Appendable appendable = new StringBuilder();
      ImageProcessorModel mockModel = new ImageProcessorModelImpl();
      ImageProcessorView view = new ImageProcessorTextView(mockModel, appendable);
      ImageProcessorController controller =
              new ImageProcessorControllerImpl(mockModel, view, readable);
      controller.execute();
      String[] messages = appendable.toString().split("\n");
      String operationMessage = messages[24];
      assertEquals("Operation has been performed", operationMessage);
    } catch (IllegalStateException is) {
      fail("An exception was not supposed to be caught");
    }
    try { // test that the image exist method is sent
      Readable readable = new StringReader("load res/4x4ppmOriginal.ppm Koala flip-horizontal " +
              "Koolaid KoalaHor q");
      Appendable appendable = new StringBuilder();
      ImageProcessorModel mockModel = new ImageProcessorModelImpl();
      ImageProcessorView view = new ImageProcessorTextView(mockModel, appendable);
      ImageProcessorController controller =
              new ImageProcessorControllerImpl(mockModel, view, readable);
      controller.execute();
      String[] messages = appendable.toString().split("\n");
      String operationMessage = messages[24];
      assertEquals("Something doesn't exist, re-enter a valid command", operationMessage);
    } catch (IllegalStateException is) {
      fail("An exception was not supposed to be caught");
    }
    try { // test that the controller goes through multiple commands
      Readable readable = new StringReader("load res/4x4ppmOriginal.ppm Koala flip-horizontal " +
              "Koala KoalaHor visualize-red KoalaHor KoalaHorRed save " +
              "res/KoalaHorRed.ppm KoalaHorRed q");
      Appendable appendable = new StringBuilder();
      ImageProcessorModel mockModel = new ImageProcessorModelImpl();
      ImageProcessorView view = new ImageProcessorTextView(mockModel, appendable);
      ImageProcessorController controller =
              new ImageProcessorControllerImpl(mockModel, view, readable);
      controller.execute();
      String[] messages = appendable.toString().split("\n");
      String operationMessage1 = messages[24];
      String operationMessage2 = messages[25];
      assertEquals("Operation has been performed", operationMessage1);
      assertEquals("Operation has been performed", operationMessage2);
    } catch (IllegalStateException is) {
      fail("An exception was not supposed to be caught");
    }


  }


  @Test
  public void testExecuteImmediateException() throws IllegalStateException {
    try {
      Readable readable = new StringReader("load res/4x4ppmOriginal.ppm Koala");
      ImageProcessorModel model = new ImageProcessorModelImpl();
      ImageProcessorView view = new ImageProcessorTextView(model,
              new ImageProcessorTextViewTest.MockAppendable());
      ImageProcessorController controller = new ImageProcessorControllerImpl(model, view, readable);
      controller.execute();
      fail("An exception should have been caught but was not");


    } catch (IllegalStateException ie) {
      // exception successfully caught
    }
  }


}
