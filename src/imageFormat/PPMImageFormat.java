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
  public int[][] read(String path) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(path));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + path + " does not exist!");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Not a valid ppm file.");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    int[][] arr = new int[width * height + 1][3];

    arr[0][0] = width;
    arr[0][1] = height;
    arr[0][2] = maxValue;


    for (int i = 1; i < height * width + 1; i++) {
      int r = sc.nextInt();
      int g = sc.nextInt();
      int b = sc.nextInt();

      arr[i][0] = r;
      arr[i][1] = g;
      arr[i][2] = b;
    }
    return arr;
  }

  @Override
  public void save(String path, int[][] image) throws IllegalArgumentException {
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
