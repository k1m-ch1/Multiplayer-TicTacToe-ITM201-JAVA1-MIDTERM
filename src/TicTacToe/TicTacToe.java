package TicTacToe;

public class TicTacToe {
  private Boolean board[][];
  private char[] players;
  static final int N = 3;
  private boolean nextPlayer = true;

  public TicTacToe(char firstPlayer, char secondPlayer) {
    board = new Boolean[N][N];
    players = new char[] { firstPlayer, secondPlayer };
  }

  public char[] getPlayers() {
    return players;
  }

  public boolean getNextPlayer() {
    return nextPlayer;
  }

  public char mapBooleanToPlayer(Boolean playerAsBoolean) {
    if (playerAsBoolean == null) {
      return ' ';
    }
    return playerAsBoolean ? players[0] : players[1];
  }

  public char[] mapBooleanArrayToPlayer(Boolean[] booleanArray) {

    char[] playerArray = new char[booleanArray.length];

    for (int i = 0; i < booleanArray.length; i++) {
      playerArray[i] = mapBooleanToPlayer(booleanArray[i]);
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

  public int performMove(int[] coords) {
    // perform a move. returns 0 if everything is fine, returns 1 if it can't be
    // placed.
    int row = coords[0];
    int col = coords[1];
    if (board[row][col] != null) {
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

    Boolean secondCharIsDigit = Character.isDigit(userInput.charAt(1));

    if (!(firstCharIsAlphabet && secondCharIsDigit)) {
      return false;
    }

    Boolean firstCharIsInRange = (userInput.charAt(0) >= 'A')
        && (userInput.charAt(0) < 'A' + N);

    Boolean secondCharIsInRange = userInput.charAt(1) >= '1'
        && (userInput.charAt(1) < '1' + N);

    return firstCharIsInRange && secondCharIsInRange;
  }

  public int[] convertMoveToIndex(String userInput) {
    // NOTE: assume that it's of the correct format beforehand

    // so the first character is an alphabet
    int col = (int) userInput.charAt(0) - (int) 'A';

    int row = N - (int) (userInput.charAt(1) - '0');

    int arrayToReturn[] = {
        row, col
    };

    return arrayToReturn;
  }

  public Boolean getWinner() {
    // checks who won. If no one won, return null

    // first check horizontal and vertical
    // keep track of whether they won in both horizontal and vertical
    // assume that they've all won? lmao... well, but i need it to be so
    Boolean[] isWonHorizontal = { true, true };
    Boolean[] isWonVertical = { true, true };
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        // so we're scanning both rows, and columns
        if (board[i][j] == null) {
          // of course if it's empty, no one has won in that row or column
          isWonHorizontal[0] = false;
          isWonHorizontal[1] = false;
        } else {
          // remember the player 1 is true, and player 2 is false
          isWonHorizontal[0] &= board[i][j];
          isWonHorizontal[1] &= !board[i][j];
        }

        if (board[j][i] == null) {
          isWonVertical[0] = false;
          isWonVertical[1] = false;
        } else {
          isWonVertical[0] &= board[j][i];
          isWonVertical[1] &= !board[j][i];
        }
      }

      if (isWonHorizontal[0] || isWonVertical[0]) {
        return true;
      }

      if (isWonHorizontal[1] || isWonVertical[1]) {
        return false;
      }

      isWonHorizontal[0] = true;
      isWonHorizontal[1] = true;
      isWonVertical[0] = true;
      isWonVertical[1] = true;

    }

    // if still no winner has been found, check diagonal

    Boolean isWonDiagonalLeft[] = { true, true };
    Boolean isWonDiagonalRight[] = { true, true };

    for (int i = 0; i < N; i++) {
      if (board[i][i] == null) {
        isWonDiagonalLeft[0] = false;
        isWonDiagonalLeft[1] = false;
      } else {
        isWonDiagonalLeft[0] &= board[i][i];
        isWonDiagonalLeft[1] &= !board[i][i];
      }

      if (board[i][N - i - 1] == null) {
        isWonDiagonalRight[0] = false;
        isWonDiagonalRight[1] = false;
      } else {
        isWonDiagonalRight[0] &= board[i][N - i - 1];
        isWonDiagonalRight[1] &= !board[i][N - i - 1];
      }
    }

    if (isWonDiagonalLeft[0] || isWonDiagonalRight[0]) {
      return true;
    }
    if (isWonDiagonalLeft[1] || isWonDiagonalRight[1]) {
      return false;
    }
    return null;
  }

  public static void main(String[] args) {
    // ok we'll now implement the TicTacToe logic
    TicTacToe board = new TicTacToe('x', 'o');

    board.performMove(new int[] { 0, 0 });
    board.performMove(new int[] { 1, 1 });
    board.performMove(new int[] { 1, 1 });
    board.performMove(new int[] { 2, 2 });

    board.performMove(board.convertMoveToIndex("C2"));
    board.performMove(board.convertMoveToIndex("C2"));

    board.performMove(board.convertMoveToIndex("C3"));

    System.out.println(board.getFormattedBoard());

    System.out.println(board.getWinner());

    board.performMove(board.convertMoveToIndex("A2"));

    System.out.println(board.getFormattedBoard());

    System.out.println(board.getWinner());

    TicTacToe board2 = new TicTacToe('x', 'o');

    board2.performMove(board2.convertMoveToIndex("B2"));
    board2.performMove(board2.convertMoveToIndex("A1"));
    board2.performMove(board2.convertMoveToIndex("B1"));
    board2.performMove(board2.convertMoveToIndex("C2"));
    board2.performMove(board2.convertMoveToIndex("C1"));
    board2.performMove(board2.convertMoveToIndex("B3"));
    board2.performMove(board2.convertMoveToIndex("A3"));

    System.out.println(board2.getFormattedBoard());
    System.out.println(board2.getWinner());

    System.out.println(board2.isOfRightFormat("A4"));
  }
}
