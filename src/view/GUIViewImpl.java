package view;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import features.Features;
import model.ImageProcessorViewModel;


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

  private Appendable appendable;


  public GUIViewImpl(String caption) throws IllegalArgumentException {

    super(caption);

    setSize(1000,1000);
    setLocation(200,200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // this.setResizable(false);
    // this.setMinimumSize(new Dimension(300,300));

    this.setLayout(new FlowLayout());

    //load button
    this.load = new JButton("load");
    this.load.setActionCommand("load button");
    this.add(load);




    //save button
    this.save = new JButton("save");
    this.save.setActionCommand("save button");
    this.add(save);



    //flip-horizontal button
    this.flipHorizontal = new JButton("flip horizontal");
    this.flipHorizontal.setActionCommand("flip-horizontal button");
    this.add(flipHorizontal);

    //flip-vertical button
    this.flipVertical = new JButton("flip vertical");
    this.flipVertical.setActionCommand("flip-vertical button");
    this.add(flipVertical);

    //sepia button
    this.sepia = new JButton("sepia");
    this.sepia.setActionCommand("sepia button");
    this.add(sepia);

    //greyscale
    this.greyscale = new JButton("greyscale");
    this.greyscale.setActionCommand("greyscale button");
    this.add(greyscale);

    //blur button
    this.blur = new JButton("blur");
    this.blur.setActionCommand("blur button");
    this.add(blur);

    //sharpen button
    this.sharpen = new JButton("sharpen");
    this.sharpen.setActionCommand("sharpen button");
    this.add(sharpen);

    //visualizeBlue button
    this.visualizeBlue = new JButton("visualize blue");
    this.visualizeBlue.setActionCommand("visualize-blue button");
    this.add(visualizeBlue);

    //visualizeGreen button
    this.visualizeGreen = new JButton("visualize green");
    this.visualizeGreen.setActionCommand("visualize-green button");
    this.add(visualizeGreen);

    //visualizeRed button
    this.visualizeRed = new JButton("visualize red");
    this.visualizeRed.setActionCommand("visualize-red button");
    this.add(visualizeRed);

    //visualizeValue button
    this.visualizeValue = new JButton("visualize value");
    this.visualizeValue.setActionCommand("visualize-value button");
    this.add(visualizeValue);

    //visualizeLuma button
    this.visualizeLuma = new JButton("visualize luma");
    this.visualizeLuma.setActionCommand("visualize-luma button");
    this.add(visualizeLuma);

    //visualizeIntensity button
    this.visualizeIntensity = new JButton("visualize intensity");
    this.visualizeIntensity.setActionCommand("visualize intensity");
    this.add(visualizeIntensity);

    //darken button
    this.darken = new JButton("darken");
    this.darken.setActionCommand("darken button");
    this.add(darken);

    //brighten button
    this.brighten = new JButton("brighten");
    this.brighten.setActionCommand("brighten button");
    this.add(darken);

    pack();



  }


  @Override
  public void refresh() {

  }

  @Override
  public void addFeatures(Features features) {
    // follow this format
    /*
    echoButton.addActionListener(evt -> features.echoOutput(input.getText()));
    toggleButton.addActionListener(evt -> features.toggleColor());
    exitButton.addActionListener(evt -> features.exitProgram());
     */



  }


  @Override
  public void renderMessage(String message) throws IOException {

  }
}
