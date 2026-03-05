package ClientServer;

import TicTacToe.TicTacToe;

public class Server {
  public static void main(String[] args) {
    TicTacToe game = new TicTacToe('x', 'o');
    System.out.println(game.getFormattedBoard());
  }
}
