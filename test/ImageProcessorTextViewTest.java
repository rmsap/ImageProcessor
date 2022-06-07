import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import view.ImageProcessorTextView;
import view.ImageProcessorView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ImageProcessorTextViewTest {
  private ImageProcessorModel model = new ImageProcessorModelImpl();


  /**
   * The class is a mock of an Appendable that will always throw an IOException.
   * An IOException is thrown whenever any of the mock's methods are called.
   */
  public static class MockAppendable implements Appendable {
    /**
     * Throws an IOException whenever the method is called without regard to the intended purpose.
     *
     * @param csq The character sequence to append.  If {@code csq} is
     *            {@code null}, then the four characters {@code "null"} are
     *            appended to this Appendable.
     * @return Appendable
     * @throws IOException whenever the method is called
     */
    @Override
    public Appendable append(CharSequence csq) throws IOException {
      throw new IOException("exception always thrown");
    }

    /**
     * Throws an IOException whenever the method is called without regard for the intended purpose.
     *
     * @param csq   The character sequence from which a subsequence will be
     *              appended.  If {@code csq} is {@code null}, then characters
     *              will be appended as if {@code csq} contained the four
     *              characters {@code "null"}.
     * @param start The index of the first character in the subsequence
     * @param end   The index of the character following the last character in the
     *              subsequence
     * @return Appendable
     * @throws IOException always when the method is called
     */
    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      throw new IOException("exception always thrown");
    }

    /**
     * Throws an IOException whenever the method is called without regard for the intended purpose.
     *
     * @param c The character to append
     * @return Appendable
     * @throws IOException always when the method is called.
     */
    @Override
    public Appendable append(char c) throws IOException {
      throw new IOException("exception always thrown");
    }
  }

  @Before
  public void initData() {

  }

  @Test
  public void renderMessageSuccessful() {
    try {
      Appendable appendable = new StringBuilder();
      ImageProcessorModel model = new ImageProcessorModelImpl();
      ImageProcessorView view = new ImageProcessorTextView(model, appendable);
      view.renderMessage("howdy");
      assertEquals("howdy", appendable.toString());
      view.renderMessage("watch me nae nae");
      assertEquals("howdywatch me nae nae", appendable.toString());

    } catch (IOException e) {
      fail("An exception was not supposed to be thrown");
    }
  }

  @Test
  public void renderMessageThrowException() {
    try {
      Appendable faulty = new MockAppendable();
      ImageProcessorModel model = new ImageProcessorModelImpl();
      ImageProcessorView view = new ImageProcessorTextView(model, faulty);
      view.renderMessage("howdy");
      fail("An exception should have been thrown but was not");

    } catch (IOException ie) {
      // exception successfully caught
    }
  }


}
