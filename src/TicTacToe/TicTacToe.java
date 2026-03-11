package TicTacToe;

import java.io.CharArrayReader;
import java.util.*;

public class TicTacToe {
  private Boolean board[][];
  private char[] players;
  static final int N = 3;
  private Boolean nextPlayer = true;

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
    String rowEntry = " %c |".repeat(N - 1) + " %c \n";
    String rowBorder = "   " + "---+".repeat(N - 1) + "---\n";
    String rowEntryAndBorder = rowEntry + rowBorder;

    // misnomer since we're using alphabets lol
    String columnNumbering = "";

    String boardTemplate = "";
    for (int i = 0; i < N - 1; i++) {
      boardTemplate += (Integer.toString(N - i) + ": " + rowEntryAndBorder);
      columnNumbering += "  " + (char) ('A' + i) + " ";
    }

    boardTemplate += "1: " + rowEntry;
    columnNumbering += "  " + (char) ('A' + N - 1) + " \n";
    columnNumbering = "  " + columnNumbering;

    boardTemplate += columnNumbering;

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

  public int performMove(int row, int col) {
    // perform a move. returns 0 if everything is fine, returns 1 if it can't be
    // placed.

    if (board[row][col] == null) {
      return 1;
    } else {
      board[row][col] = nextPlayer;
      nextPlayer = !nextPlayer;
      return 0;
    }
  }

  public Boolean isOfRightFormat(String userInput) {
    // we want userInput to be the format of A1, B1, etc...
    Boolean isRightSize = (userInput.length() == 2);

    if (!isRightSize) {
      return false;
    }

    Boolean firstCharIsAlphabet = Character.isAlphabetic(userInput.charAt(0));

    Boolean secondCharIsDigit = Character.isAlphabetic(userInput.charAt(1));

    if (!(firstCharIsAlphabet && secondCharIsDigit)) {
      return false;
    }

    Boolean firstCharIsInRange = (userInput.charAt(0) >= 'A')
        && (userInput.charAt(0) < 'A' + N);

    Boolean secondCharIsInRange = userInput.charAt(1) >= 0
        && (userInput.charAt(1) < N);

    return firstCharIsInRange && secondCharIsInRange;
  }

  public int[] convertMoveToIndex(String userInput) {
    // NOTE: assume that it's of the correct format beforehand

    // so the first character is an alphabet
    int col = (int) userInput.charAt(0) - (int) 'A';

    int row = N - (int) (userInput.charAt(1) - '0');

    int arrayToReturn[] = {
        col, row
    };

    return arrayToReturn;
  }

  public Boolean getWinner() {
    // checks who won. If no one won, return null
    return (Boolean) true;
  }

  public static void main(String[] args) {
    // ok we'll now implement the TicTacToe logic
    TicTacToe board = new TicTacToe('x', 'o');

    Boolean[] flattenedBoard = board.getFlattenedBoard();

    System.out.println(board.getFormattedBoard());

    System.out.println(Arrays.toString(board.convertMoveToIndex("C1")));
  }
}
