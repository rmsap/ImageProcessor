package operations;

import model.ImageProcessorModel;
import operations.Operation;

/**
 * The class represents the operation that allows a user to flip an image vertically.
 */
public class FlipVertical implements Operation {
  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    // need to reverse the array contents vertically
    // make a 3d arraylist and then convert that array list back to a 2d arraylist
    // [row] [column] [contents in rc that is an array of 3]
    int width = model.getImage(name)[0][0];
    int height = model.getImage(name)[0][1];
    int [][][] board = new int[height][width][3];
    for(int i = 0; i < height; i++) {
      for(int j = 0; j < width; j++) {
        board[i][j] = model.getImage(name)[((i * width) + j + 1)];
      }
    }

    this.flippedRows(board);


    // convert 3d board back to 2d board
    int [][] flippedDeepCopy = new int [model.getImage(name).length][model.getImage(name)[0].length];
    flippedDeepCopy[0] = model.getImage(name)[0];
    int anotherCounter = 1;
    for(int row = 0; row < board.length; row++) {
      for(int column = 0; column < board[0].length; column++) {
        flippedDeepCopy[anotherCounter] = board[row][column];
        anotherCounter++;
      }
    }
    return flippedDeepCopy;
  }

  private int [][][] flippedRows(int [][][] stub) {
    for(int i = 0; i < stub.length / 2; i++) {
      int[][] temp = stub[i];
      stub[i] = stub[stub.length - i - 1];
      stub[stub.length - i - 1] = temp;

    }
    return stub;
  }
}
