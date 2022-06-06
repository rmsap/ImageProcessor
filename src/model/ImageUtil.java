package model;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import imageFormat.PPMImageFormat;
import operations.BrightenOrDarken;
import operations.FlipHorizontal;
import operations.FlipVertical;
import operations.VisualizeBrightness;
import operations.VisualizeRGB;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method
 *  as required.
 */
public class ImageUtil {

  //demo main
  public static void main(String []args) {
      String filename;

      AbstractImageProcessorModel model = new PPMImageProcessorModel();
      model.loadImage("Koala.ppm", "Koala", new PPMImageFormat());
//      System.out.println(model.getImage("Koala")[0][0]);
//      System.out.println(model.getImage("Koala")[0][1]);
      System.out.println(model.getImage("Koala").length);
//      System.out.println(model.getImage("Koala"));
      model.doOperation(new BrightenOrDarken(-1000), "Koala", "Koala black");
      model.saveImage("KoalaBlack.ppm", "Koala black", new PPMImageFormat());
//      System.out.println(model.getImage("Koala brighten"));
//      model.saveImage("KoalaVersion2.ppm", "Koala");

      
//      if (args.length>0) {
//          filename = args[0];
//      }
//      else {
//          filename = "sample.ppm";
//      }
//
//      ImageUtil.readPPM("koala.ppm");
  }
}

