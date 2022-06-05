package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class represents an ImageProcessorModel that works with PPM files.
 */
public class PPMImageProcessorModel extends AbstractImageProcessorModel {
  PPMImageProcessorModel() {
    super();
  }

  @Override
  public void loadImage(String path, String name) {
    directory.put(name, ImageUtil.readPPM(path));
  }

  @Override
  public void saveImage(String path, String imageName) {
    File imageFile = new File(path);
    int[][] image = this.directory.get(imageName);

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
