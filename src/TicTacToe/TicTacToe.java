package TicTacToe;

import java.util.*;

public class TicTacToe {
  private Boolean board[][];
  private char[] players;
  static final int N = 3;

  public TicTacToe(char firstPlayer, char secondPlayer) {
    board = new Boolean[N][N];
    players = new char[] { firstPlayer, secondPlayer };
  }

  public char[] mapBooleanArrayToPlayer(Boolean[] booleanArray) {

    char[] playerArray = new char[booleanArray.length];

    for (int i = 0; i < booleanArray.length; i++) {
      if (booleanArray[i] == null) {
        playerArray[i] = ' ';
      } else if (booleanArray[i]) {
        playerArray[i] = players[0];
      } else {
        playerArray[i] = players[1];
      }
    }

    return playerArray;
  }

  public char[][] getBoardAsChar() {
    // this simply turns the 2d array of boolean into a character array
    // True is first player
    // False is second player
    // null is empty character
    char boardAsChar[][] = new char[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (board[i][j] == null) {
          boardAsChar[i][j] = ' ';
        } else if (board[i][j]) {
          boardAsChar[i][j] = players[0];
        } else {
          boardAsChar[i][j] = players[1];
        }
      }
    }
    return boardAsChar;
  }

  // so apparently, you can use generic types
  //

  public Boolean[] getFlattenedBoard() {
    Boolean[] flattenedBoard = new Boolean[N * N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        flattenedBoard[i * N + j] = board[i][j];
      }
    }
    return flattenedBoard;
  }

  public String getFormattedBoard() {
    String formattedBoard;
    String boardTemplate;
    String rowEntry = " %c |".repeat(N - 1) + " %c \n";
    String rowBorder = "---+".repeat(N - 1) + "---\n";
    String rowEntryAndBorder = rowEntry + rowBorder;
    boardTemplate = rowEntryAndBorder.repeat(N - 1) + rowEntry;

    char[] flattenedBoard = mapBooleanArrayToPlayer(getFlattenedBoard());

    // need to convert to generic types... so verbose...
    Character[] flattenedBoardAsCharacter = new Character[flattenedBoard.length];
    for (int i = 0; i < flattenedBoard.length; i++) {
      flattenedBoardAsCharacter[i] = (Character) flattenedBoard[i];
    }

    formattedBoard = String.format(
        boardTemplate,
        (Object[]) flattenedBoardAsCharacter);

    // I still dont' quite get why we need to do Object[] to suppress the warning...

    return formattedBoard;
  }

  public static void main(String[] args) {
    // ok we'll now implement the TicTacToe logic
    TicTacToe board = new TicTacToe('x', 'o');

    Boolean[] flattenedBoard = board.getFlattenedBoard();

    System.out.println(board.getFormattedBoard());
  }
}
