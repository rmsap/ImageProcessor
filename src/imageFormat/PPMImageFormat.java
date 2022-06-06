package imageFormat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class represents PPM image formats, meaning that it can read PPM files and save images as
 * PPM files.
 */
public class PPMImageFormat implements ImageFormat {
  @Override
  public int[][] read(String path) {
    Scanner sc;

      try {
        sc = new Scanner(new FileInputStream(path));
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
//      System.out.println("Width of image: "+width);
        int height = sc.nextInt();
//      System.out.println("Height of image: "+height);
        int maxValue = sc.nextInt();
//      System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);

        int[][] arr = new int[width * height + 1][3];

        arr[0][0] = width;
        arr[0][1] = height;
        arr[0][2] = maxValue;

        int count = 0;

        for (int i = 1; i < height * width + 1; i++) {
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
        throw new IllegalArgumentException("File " + path + " does not exist!");
      }
  }

  @Override
  public void save(String path, int[][] image) {
    File imageFile = new File(path);

    try {
      imageFile.createNewFile();
    } catch (IOException e) {
      throw new IllegalArgumentException("File already exists.");
    }

    try {
      FileWriter writer = new FileWriter(path);

      // Write the PPM header into this file
      writer.write("P3 ");

      // Write all pixels from this image into the file
      for (int[] pixel : image) {
        writer.write(pixel[0] + " " + pixel[1] + " " + pixel[2] + "\n");
      }

      writer.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to write to file.");
    }
  }
}
