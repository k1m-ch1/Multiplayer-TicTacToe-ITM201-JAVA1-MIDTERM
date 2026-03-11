package ClientServer;

import java.io.*;
import java.net.*;

import TicTacToe.TicTacToe;

public class Server {
  public static void main(String[] args) throws IOException {
    /*
     * TicTacToe game = new TicTacToe('x', 'o');
     * System.out.println(game.getFormattedBoard());
     */

    final int PORT_NUMBER = 2345;

    ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);

  }
}
