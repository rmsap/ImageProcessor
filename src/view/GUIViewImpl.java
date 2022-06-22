package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;


/**
 * The class represents an implementation of a GUI view from the GUI interface.
 */
public class GUIViewImpl extends JFrame implements ImageProcessorGUIView {
  private JButton load;
  private JButton save;
  private JButton flipHorizontal;
  private JButton flipVertical;
  private JButton sepia;
  private JButton greyscale;
  private JButton blur;
  private JButton sharpen;
  private JButton visualizeBlue;
  private JButton visualizeGreen;
  private JButton visualizeRed;
  private JButton visualizeValue;
  private JButton visualizeLuma;
  private JButton visualizeIntensity;
  private JButton darken;
  private JButton brighten;


  private JScrollPane imageHouseScrollPane;

//  private JScrollPane actualImageVerticalScrollPane;
//  private JScrollPane actualImageHorizontalScrollPane;
//
//  private JScrollPane histogramVerticalScrollPane;
//  private JScrollPane histogramHorizontalScrollPane;

  private JPanel imageHousePanel;

  private JPanel operationPanel;


  private JLabel imagePicture;
  private JPanel redHistogram;
  private JPanel greenHistogram;
  private JPanel blueHistogram;
  private JPanel intensityHistogram;

  private JLabel histogramPanel;


  private String savePath;
  private String loadPath;


  public GUIViewImpl(String caption) throws IllegalArgumentException {

    super(caption);

    setSize(1000,1000);
    setLocation(100,0);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // this.setResizable(false);
    // this.setMinimumSize(new Dimension(300,300));


    this.setLayout(new FlowLayout());

    // adding the panel that houses all operations
    operationPanel = new JPanel();
    operationPanel.setLayout(new GridLayout(8,2));
    operationPanel.setSize(300,300);
//    this.add(operationPanel);


    //load button
    this.load = new JButton("load");
    this.load.setActionCommand("load button");
    this.operationPanel.add(load);




    //save button
    this.save = new JButton("save");
    this.save.setActionCommand("save button");
    this.operationPanel.add(save);



    //flip-horizontal button
    this.flipHorizontal = new JButton("flip horizontal");
    this.flipHorizontal.setActionCommand("flip-horizontal button");
    this.operationPanel.add(flipHorizontal);

    //flip-vertical button
    this.flipVertical = new JButton("flip vertical");
    this.flipVertical.setActionCommand("flip-vertical button");
    this.operationPanel.add(flipVertical);

    //sepia button
    this.sepia = new JButton("sepia");
    this.sepia.setActionCommand("sepia button");
    this.operationPanel.add(sepia);

    //greyscale
    this.greyscale = new JButton("greyscale");
    this.greyscale.setActionCommand("greyscale button");
    this.operationPanel.add(greyscale);

    //blur button
    this.blur = new JButton("blur");
    this.blur.setActionCommand("blur button");
    this.operationPanel.add(blur);

    //sharpen button
    this.sharpen = new JButton("sharpen");
    this.sharpen.setActionCommand("sharpen button");
    this.operationPanel.add(sharpen);

    //visualizeBlue button
    this.visualizeBlue = new JButton("visualize blue");
    this.visualizeBlue.setActionCommand("visualize-blue button");
    this.operationPanel.add(visualizeBlue);

    //visualizeGreen button
    this.visualizeGreen = new JButton("visualize green");
    this.visualizeGreen.setActionCommand("visualize-green button");
    this.operationPanel.add(visualizeGreen);

    //visualizeRed button
    this.visualizeRed = new JButton("visualize red");
    this.visualizeRed.setActionCommand("visualize-red button");
    this.operationPanel.add(visualizeRed);

    //visualizeValue button
    this.visualizeValue = new JButton("visualize value");
    this.visualizeValue.setActionCommand("visualize-value button");
    this.operationPanel.add(visualizeValue);

    //visualizeLuma button
    this.visualizeLuma = new JButton("visualize luma");
    this.visualizeLuma.setActionCommand("visualize-luma button");
    this.operationPanel.add(visualizeLuma);

    //visualizeIntensity button
    this.visualizeIntensity = new JButton("visualize intensity");
    this.visualizeIntensity.setActionCommand("visualize intensity");
    this.operationPanel.add(visualizeIntensity);

    //darken button
    this.darken = new JButton("darken");
    this.darken.setActionCommand("darken button");
    this.operationPanel.add(darken);

    //brighten button
    this.brighten = new JButton("brighten");
    this.brighten.setActionCommand("brighten button");
    this.operationPanel.add(brighten);

    this.add(operationPanel);


    // making a panel that contains the actual image and the histogram
    imageHousePanel = new JPanel();
    // histogram is to vertically below the image
    imageHousePanel.setLayout(new BoxLayout(imageHousePanel, BoxLayout.PAGE_AXIS));
    // scrollbar around this main panel
    imageHouseScrollPane = new JScrollPane();
//    add(imageHouseScrollPane);


    // setting JLabel containing the actual image
    // adding a scrollbar for the actual image (vertical and horizontal when needed)
    ImageIcon icon = new ImageIcon();
    imagePicture = new JLabel(icon);

    JScrollPane imagePictureScrollPane = new JScrollPane() {
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(200, 200);
      }
    };

    imagePictureScrollPane.setViewportView(imagePicture);
    imageHousePanel.add(imagePictureScrollPane);


    redHistogram = new JPanel();
    blueHistogram = new JPanel();
    greenHistogram = new JPanel();
    intensityHistogram = new JPanel();
    // setting the JLabel containing the histogram
    JScrollPane redHistogramScrollPane = new JScrollPane(redHistogram);
//    imageHousePanel.add(redHistogram);
    JScrollPane greenHistogramScrollPane = new JScrollPane(greenHistogram);
//    imageHousePanel.add(greenHistogram);
    JScrollPane blueHistogramScrollPane = new JScrollPane(blueHistogram);
//    imageHousePanel.add(blueHistogram);
    JScrollPane intensityHistogramScrollPane = new JScrollPane(intensityHistogram);
//    imageHousePanel.add(intensityHistogram);

    ImageIcon icon1 = new ImageIcon();
    histogramPanel = new JLabel(icon1);

    JScrollPane histogramScrollPane = new JScrollPane() {
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(300, 300);
      }
    };

    histogramScrollPane.setViewportView(histogramPanel);
    imageHousePanel.add(histogramScrollPane);
//    imageHousePanel.add(histogramPanel);

    // setting the main scroll pane for the images panel
    imageHousePanel.add(imageHouseScrollPane);
//    imageHousePanel.add(redHistogramScrollPane);
//    imageHousePanel.add(blueHistogramScrollPane);
//    imageHousePanel.add(greenHistogramScrollPane);
//    imageHousePanel.add(intensityHistogramScrollPane);

//    imageHousePanel.add(histogramScrollPane);

    // adding a border around imagePanel
    imageHousePanel.setBorder(BorderFactory.createTitledBorder("Using Password fields"));
    add(imageHousePanel);




    // setting the supposed image

    // setting the supposed image histogram

    pack();
    setVisible(true);


  }


  @Override
  public void refresh(Image bruh) {
    ImageIcon image = new ImageIcon(bruh);
//    for (int i = 0; i < 4; i++) {
//      for (int j = 0; j < 4; j++) {
//        System.out.println(new Color(bruh.getRGB(i,j)).getRed());
//      }
//    }
    this.imagePicture.setIcon(image); // putting the image in the JLabel

    // also need to refresh the histogram

    // Set up arrays to count each occurrence of each color channel/intensity
  }

  @Override
  public void addFeatures(Features features) {
    // follow this format
    /*
    echoButton.addActionListener(evt -> features.echoOutput(input.getText()));
    toggleButton.addActionListener(evt -> features.toggleColor());
    exitButton.addActionListener(evt -> features.exitProgram());
     */
    this.save.addActionListener(evt -> {
      final JFileChooser fchooser = new JFileChooser(".");
      int retvalue = fchooser.showSaveDialog(GUIViewImpl.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        features.save(f.getPath());
      }
    });
    this.load.addActionListener(evt -> {
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "PPM, JPG, PNG, & BMP Images", "jpg", "ppm", "png", "bmp");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(GUIViewImpl.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        features.load(f.getPath());
      }
    });
    this.brighten.addActionListener(evt -> features.brightenOrDarken(1));
    this.darken.addActionListener(evt -> features.brightenOrDarken( -1));
    this.flipVertical.addActionListener(evt -> features.flipVertical());
    this.flipHorizontal.addActionListener(evt -> features.flipHorizontal());
    this.visualizeRed.addActionListener(evt -> features.visualizeRed());
    this.visualizeBlue.addActionListener(evt -> features.visualizeBlue());
    this.visualizeGreen.addActionListener(evt -> features.visualizeGreen());
    this.visualizeIntensity.addActionListener(evt -> features.visualizeIntensity());
    this.visualizeLuma.addActionListener(evt -> features.visualizeLuma());
    this.visualizeValue.addActionListener(evt -> features.visualizeValue());
    this.greyscale.addActionListener(evt -> features.greyscale());
    this.sepia.addActionListener(evt -> features.sepia());
    this.blur.addActionListener(evt -> features.blur());
    this.sharpen.addActionListener(evt -> features.sharpen());



  }

  @Override
  public void visualizeHistogram(Image image) {
    int[] redCounts = new int[256];
    int[] greenCounts = new int[256];
    int[] blueCounts = new int[256];
    int[] intensityCounts = new int[256];

    // Set each index of each array to 0
    Arrays.fill(redCounts, 0);
    Arrays.fill(greenCounts, 0);
    Arrays.fill(blueCounts, 0);
    Arrays.fill(intensityCounts, 0);

    if (image instanceof BufferedImage) {
      BufferedImage buffered = (BufferedImage) image;

      for (int i = 0; i < buffered.getWidth(); i++) {
        for (int j = 0; j < buffered.getHeight(); j++) {
          Color color = new Color(buffered.getRGB(i, j));
          redCounts[color.getRed()]++;
          greenCounts[color.getGreen()]++;
          blueCounts[color.getBlue()]++;
          intensityCounts[(color.getRed() + color.getGreen() + color.getBlue()) / 3]++;
        }
      }
    }

    BufferedImage imageRed = new BufferedImage(256, 300, BufferedImage.TYPE_INT_ARGB);
    BufferedImage imageGreen = new BufferedImage(256, 300, BufferedImage.TYPE_INT_ARGB);
    BufferedImage imageBlue = new BufferedImage(256, 300, BufferedImage.TYPE_INT_ARGB);
    BufferedImage imageIntensity = new BufferedImage(256,
            300, BufferedImage.TYPE_INT_ARGB);

    int maxValue = Math.max(Arrays.stream(redCounts).max().getAsInt(),
            Math.max(Arrays.stream(greenCounts).max().getAsInt(),
            Math.max(Arrays.stream(blueCounts).max().getAsInt(),
                    Arrays.stream(intensityCounts).max().getAsInt())));

    int minValue = Math.min(Arrays.stream(redCounts).min().getAsInt(),
            Math.min(Arrays.stream(greenCounts).min().getAsInt(),
                    Math.min(Arrays.stream(blueCounts).min().getAsInt(),
                            Arrays.stream(intensityCounts).min().getAsInt())));

    int range = maxValue - minValue;

    for (int i = 0; i < 256; i++) {
      for (int j = 0; j < (int) ((redCounts[i] - minValue) / ((double) range) * 300); j++) {
        imageRed.setRGB(i, 299 - j, new Color(100, 0, 0, 75).getRGB());
      }

      for (int j = 0; j < (int) ((greenCounts[i] - minValue) / ((double) range) * 300); j++) {
        imageGreen.setRGB(i, 299 - j, new Color(0, 100, 0, 75).getRGB());
      }

      for (int j = 0; j < (int) ((blueCounts[i] - minValue) / ((double) range) * 300); j++) {
        imageBlue.setRGB(i, 299 - j, new Color(0, 0, 100, 75).getRGB());
      }

      for (int j = 0; j < (int) ((intensityCounts[i] - minValue) / ((double) range) * 300); j++) {
        imageIntensity.setRGB(i, 299 - j, new Color(100, 100, 50, 75).getRGB());
      }
    }

    BufferedImage histogram = new BufferedImage(256, 300, BufferedImage.TYPE_INT_ARGB);

    Graphics g = histogram.getGraphics();
    g.drawImage(imageRed, 0, 0, null);
    g.drawImage(imageGreen, 0, 0, null);
    g.drawImage(imageBlue, 0, 0, null);
    g.drawImage(imageGreen, 0, 0, null);

    ImageIcon icon = new ImageIcon(histogram);
    histogramPanel.setIcon(icon);
  }


  @Override
  public void renderMessage(String message) throws IOException {
    // should make a panel with the message and show it
    JLabel text = new JLabel(message);
    this.add(text);

  }
}
