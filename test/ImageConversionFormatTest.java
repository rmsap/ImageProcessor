import org.junit.Before;
import org.junit.Test;

import imageformat.BMPImageFormat;
import imageformat.ImageFormat;
import imageformat.JPGImageFormat;
import imageformat.PNGImageFormat;
import imageformat.PPMImageFormat;

import static org.junit.Assert.assertEquals;

/**
 * The following class tests file conversions and ensures that they have the same pixel values.
 */
public class ImageConversionFormatTest {
  ImageFormat ppm = new PPMImageFormat();
  ImageFormat png = new PNGImageFormat();
  ImageFormat jpg = new JPGImageFormat();
  ImageFormat bmp = new BMPImageFormat();

  @Before
  public void initData() {
    ppm = new PPMImageFormat();
    png = new PNGImageFormat();
    jpg = new JPGImageFormat();
    bmp = new BMPImageFormat();
  }

  @Test
  public void ppmAndPNG() {
    int [][] fromPPM = ppm.read("res/4x4PPMOriginal.ppm");
    png.save("res/4x4PNGOriginal.png",fromPPM);
    int [][] fromPNG = png.read("res/4x4PNGOriginal.png");
    for(int i = 0; i < fromPPM.length; i++) {
      for(int j = 0; j < fromPPM[0].length; j++) {
        assertEquals(fromPPM[i][j], fromPNG[i][j]);
      }
    }
  }

  @Test
  public void ppmAndBMP() {
    int [][] fromPPM = ppm.read("res/4x4PPMOriginal.ppm");
    bmp.save("res/4x4BMPOriginal.bmp",fromPPM);
    int [][] fromBMP = bmp.read("res/4x4BMPOriginal.bmp");
    for(int i = 0; i < fromPPM.length; i++) {
      for(int j = 0; j < fromPPM[0].length; j++) {
        assertEquals(fromPPM[i][j], fromBMP[i][j]);
      }
    }
  }

  @Test
  public void pngAndBMP() {
    int [][] fromPNG = png.read("res/neil.png");
    bmp.save("res/neilBMP.bmp",fromPNG);
    int [][] fromBMP = bmp.read("res/neilBMP.bmp");
    for(int i = 0; i < fromPNG.length; i++) {
      for(int j = 0; j < fromPNG[0].length; j++) {
        assertEquals(fromPNG[i][j], fromBMP[i][j]);
      }
    }
  }

  @Test
  public void bmpAndPNG() {
    int [][] fromBMP = bmp.read("res/sampleBMP.bmp");
    png.save("res/sampleBMPtoPNG.png",fromBMP);
    int [][] fromPNG = png.read("res/sampleBMPtoPNG.png");
    for(int i = 0; i < fromBMP.length; i++) {
      for(int j = 0; j < fromBMP[0].length; j++) {
        assertEquals(fromBMP[i][j], fromPNG[i][j]);
      }
    }
  }


  @Test
  public void jpgAndPPM() {
    int [][] fromJPG = jpg.read("res/Warner.jpg");
    ppm.save("res/WarnerToPPM.ppm",fromJPG);
    int [][] fromPPM = ppm.read("res/WarnerToPPM.ppm");
    for(int i = 0; i < fromJPG.length; i++) {
      for(int j = 0; j < fromJPG[0].length; j++) {
        assertEquals(fromJPG[i][j], fromPPM[i][j]);
      }
    }
  }

  @Test
  public void jpgAndPNG() {
    int [][] fromJPG = jpg.read("res/Warner.jpg");
    png.save("res/WarnerToPNG.png",fromJPG);
    int [][] fromPNG = png.read("res/WarnerToPNG.png");
    for(int i = 0; i < fromJPG.length; i++) {
      for(int j = 0; j < fromJPG[0].length; j++) {
        assertEquals(fromJPG[i][j], fromPNG[i][j]);
      }
    }

  }

  @Test
  public void jpgAndBMP(){
    int [][] fromJPG = jpg.read("res/Warner.jpg");
    bmp.save("res/WarnerToBMP.bmp",fromJPG);
    int [][] fromBMP = png.read("res/WarnerToBMP.bmp");
    for(int i = 0; i < fromJPG.length; i++) {
      for(int j = 0; j < fromJPG[0].length; j++) {
        assertEquals(fromJPG[i][j], fromBMP[i][j]);
      }
    }
  }

  @Test
  public void bmpToPPM() {
    int [][] fromBMP = bmp.read("res/sampleBMP.bmp");
    ppm.save("res/sampleBMPtoPPM.ppm",fromBMP);
    int [][] fromPPM = ppm.read("res/sampleBMPtoPPM.ppm");
    for(int i = 0; i < fromBMP.length; i++) {
      for(int j = 0; j < 3; j++) {
        assertEquals(fromBMP[i][j], fromPPM[i][j]);
      }
    }
  }

  @Test
  public void pngToPPM(){
    int [][] fromPNG = bmp.read("res/neil.png");
    ppm.save("res/neilToPPM.ppm",fromPNG);
    int [][] fromPPM = ppm.read("res/neilToPPM.ppm");
    for(int i = 0; i < fromPNG.length; i++) {
      for(int j = 0; j < 3; j++) {
        assertEquals(fromPNG[i][j], fromPPM[i][j]);
      }
    }
  }




}
