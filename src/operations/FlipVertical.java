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
    int rowCounter = 0;
    int columnCounter = 0;
    int height = model.getImage(name)[0][1];
    int width = model.getImage(name)[0][2];
    int [][][] board = new int[height][width][3];
    for(int i = 1; i < model.getImage(name).length; i++) {
      for(int j = 0; j < model.getImage(name)[0].length; j++) {
        board[rowCounter - 1][columnCounter][j] = model.getImage(name)[i][j];
        columnCounter++;

      }
      if(i % 3 == 0) {
        rowCounter++;
        columnCounter = 0; // resetting it

      }
    }

    // re-arrange things in the 3d board
//    int [][][] flippedBoard = new int[board.length][board[0].length][3];
    this.flippedRows(board);


    // convert 3d board back to 2d board
    int [][] flippedDeepCopy = new int[model.getImage(name).length][model.getImage(name)[0].length];
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
