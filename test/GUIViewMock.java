import java.awt.*;
import java.io.IOException;

import controller.Features;
import view.ImageProcessorGUIView;

/**
 * A mock of the GUI-based View used for testing purposes. Its methods are not needed, it exists
 * solely so that Java application windows do not open whenever we instantiate a view. As such,
 * all of its methods do not do anything.
 */
public class GUIViewMock implements ImageProcessorGUIView {
  @Override
  public void refresh(Image bruh) {
    return;
  }

  @Override
  public void addFeatures(Features features) {
    return;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    return;
  }
}