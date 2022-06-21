package view;

import javax.swing.*;
import java.awt.*;

public class Histogram extends JPanel {
  // Count the occurrence of each pixel color
  private int[] count;

  public Histogram(int[] count) {
    this.count = count;
    repaint();
  }

  protected void paintComponent(Graphics g) {
    if (count != null) {
      super.paintComponent(g);

      // Find the panel size and bar width and interval dynamically
      int width = this.getWidth();
      int height = this.getHeight();
      int interval = (width - 40) / count.length;
      int individualWidth = (int) (((width - 40) / 24) * 0.60);

      // Find the maximum count. The maximum count has the highest bar
      int maxCount = 0;
      for (int i = 0; i < count.length; i++) {
        if (maxCount < count[i])
          maxCount = count[i];
      }

      // x is the start position for the first bar in the histogram
      int x = 0;

      // Draw a horizontal base line
      g.drawLine(10, height - 45, width - 10, height - 45);
      for (int i = 0; i < count.length; i++) {
        // Find the bar height
        int barHeight = (int) (((double) count[i] / (double) maxCount) * (height - 55));

        // Display a bar (i.e. rectangle)
        g.drawRect(x, height - 45 - barHeight, individualWidth, barHeight);

        // Display a letter under the base line
        g.drawString(i + "", x, height - 30);

        // Move x for displaying the next character
        x += interval;
      }
    }
  }
}