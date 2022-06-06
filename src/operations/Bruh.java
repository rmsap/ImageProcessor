package operations;

public class Bruh {
  public static void main(String [] args) {
    int [] arr = new int [10];
    int counter = 0;
    for(int i = 0; i < 10; i++) {
      arr[i] = counter;
      counter++;
    }

    int [][] board = new int[3][3];
    int width = 3;
    int height = 3;
    int rowCounter = 0;
    int columnCounter = 0;
    for(int j = 0; j < height; j++) {
      for(int k = 0; k < width; k++) {
        board[j][k] = arr[j * width + k + 1];
      }
    }
//    System.out.println(board[0][0]);
    reversedColumns(board);
//    System.out.println(board[2][1]);
    int [] arr2 = new int[10];
    int rCounter = 1;
    arr2[0] = arr[0];
    for(int a = 0; a < board.length; a++) {
      for(int b = 0; b < board[0].length; b++) {
        arr2[rCounter] = board[a][b];
        System.out.println(arr2[rCounter]);
        rCounter++;
      }
    }

  }

  private static int[][] reversedColumns(int[][] stub) {
    for(int i = 0; i < stub.length / 2; i++) {
      int[] temp = stub[i];
      stub[i] = stub[stub.length - i - 1];
      stub[stub.length - i - 1] = temp;

    }
    return stub;
  }

}
