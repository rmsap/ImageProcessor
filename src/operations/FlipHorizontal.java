package operations;

import model.ImageProcessorModel;
import operations.Operation;

/**
 * This class represents an Operation that flips the given ImageProcessorModel.
 */
public class FlipHorizontal implements Operation {
  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    // need to reverse the array contents horizontally
    // make a 3d arraylist and then convert that array list back to a 2d arraylist
    // [row] [column] [contents in rc]
    return executeAlt(model, name);
//    int rowCounter = 0;
//    int columnCounter = 0;
//    int height = model.getImage(name)[0][1];
//    int width = model.getImage(name)[0][2];
//    int [][][] board = new int[height][width][3];
//    for(int i = 1; i < model.getImage(name).length; i++) {
//      for(int j = 0; j < model.getImage(name)[0].length; j++) {
//        board[rowCounter - 1][columnCounter][j] = model.getImage(name)[i][j];
//        columnCounter++;
//
//      }
//      if(i % 3 == 0) {
//        rowCounter++;
//        columnCounter = 0; // resetting it
//
//      }
//    }
//
//    // re-arrange things in the 3d board
//    int [][][] flippedBoard = new int[board.length][board[0].length][3];
//    for(int k = 0; k < board.length; k++) {
//      // reverse columns in the row
//      board[k] = this.reversedColumns(board[0]);
//    }
//
//
//    // convert 3d board back to 2d board
//    int [][] flippedDeepCopy = new int[model.getImage(name).length][model.getImage(name)[0].length];
//    flippedDeepCopy[0] = model.getImage(name)[0];
//    int anotherCounter = 1;
//    for(int row = 0; row < board.length; row++) {
//      for(int column = 0; column < board[0].length; column++) {
//        flippedDeepCopy[anotherCounter] = board[row][column];
//        anotherCounter++;
//      }
//    }
//
//
//    return flippedDeepCopy;
  }


  private int[][] reversedColumns(int[][] stub) {
    for(int i = 0; i < stub.length / 2; i++) {
      int[] temp = stub[i];
      stub[i] = stub[stub.length - i - 1];
      stub[stub.length - i - 1] = temp;

    }
    return stub;
  }

  // Alternate attempt at execute
  private int[][] executeAlt(ImageProcessorModel model, String name) {
    int [][] copy = model.getImage(name);
    int totalCols = copy.length / copy[0][1];

    for (int i = 1; i < copy.length; i++) {
      int row = i / copy[0][0] + 1;
      int col = i % totalCols;
      this.swap(copy, i, (copy[0][0] * row) - (col - 1));

      // row = 1
      // totalCols = 4
      // col = 1
      // 4 - 1
      // 3

      if (i % (copy[0][0] / 2) == 0) {
        i += copy[0][0] / 2;
      }
    }

    return copy;
  }

  private void swap(int[][] arr, int indexOne, int indexTwo) {
    int[] temp = arr[indexOne];
    arr[indexOne] = arr[indexTwo];
    arr[indexTwo] = temp;
  }

}
