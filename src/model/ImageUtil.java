package model;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

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

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file. 
   */
  public static int[][] readPPM(String filename) {
    Scanner sc;
    
    try {
      sc = new Scanner(new FileInputStream(filename));
      StringBuilder builder = new StringBuilder();
      //read the file line by line, and populate a string. This will throw away any comment lines
      while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.charAt(0)!='#') {
          builder.append(s+System.lineSeparator());
        }
      }

      //now set up the scanner to read from the string we just built
      sc = new Scanner(builder.toString());

      String token;

      token = sc.next();
      if (!token.equals("P3")) {
        System.out.println("Invalid PPM file: plain RAW file should begin with P3");
      }
      int width = sc.nextInt();
      System.out.println("Width of image: "+width);
      int height = sc.nextInt();
      System.out.println("Height of image: "+height);
      int maxValue = sc.nextInt();
      System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);

      int[][] arr = new int[width * height][3];

      arr[0][0] = width;
      arr[0][1] = height;
      arr[0][2] = maxValue;

      int count = 0;

      for (int i = 1; i < height * width; i++) {
//        count += 1;

        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        arr[i][0] = r;
        arr[i][1] = g;
        arr[i][2] = b;

//        System.out.println("Color of pixel " + i + ": "+ arr[i][0] + "," + arr[i][1] + "," + arr[i][2]);
      }

      return arr;
    }
    catch (FileNotFoundException e) {
        throw new IllegalArgumentException("File " + filename + " does not exist!");
    }
  }

  //demo main
  public static void main(String []args) {
      String filename;

      AbstractImageProcessorModel model = new PPMImageProcessorModel();
      model.loadImage("Koala.ppm", "Koala");
      System.out.println(model.getImage("Koala")[0][0]);
      System.out.println(model.getImage("Koala")[0][1]);
      System.out.println(model.getImage("Koala").length);
//      System.out.println(model.getImage("Koala"));
//      model.doOperation(new FlipVertical(), "Koala", "Koala vertical");
//      model.saveImage("KoalaVertical.ppm", "Koala vertical");
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

