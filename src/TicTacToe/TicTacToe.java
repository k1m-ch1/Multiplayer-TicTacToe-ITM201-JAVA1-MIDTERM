package TicTacToe;

import java.util.HashMap;

public class TicTacToe {
  private Boolean board[][];
  private HashMap<Character, Boolean> playerToBool = new HashMap<>();
  private char[] players;
  final int N = 3;

  public TicTacToe(char firstPlayer, char secondPlayer) {
    board = new Boolean[N][N];
    players = new char[] { firstPlayer, secondPlayer };
    playerToBool.put(firstPlayer, true);
    playerToBool.put(secondPlayer, false);
  }

  public String getFormattedBoard() {
    String formattedBoard;
    String rowEntry = " %s |".repeat(N - 1) + " %s \n";
    String rowBorder = "---+".repeat(N - 1) + "---\n";
    String rowEntryAndBorder = rowEntry + rowBorder;
    formattedBoard = rowEntryAndBorder.repeat(N - 1) + rowEntry;
    return formattedBoard;
  }

  public Boolean[][] getBoard() {
    return board;
  }

  public static void main(String[] args) {
    System.out.println("Hello TicTacToe");
  }
}
