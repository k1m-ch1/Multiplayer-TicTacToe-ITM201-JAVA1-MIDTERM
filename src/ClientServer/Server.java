package ClientServer;

import java.io.*;
import java.net.*;
import java.util.*;

import TicTacToe.TicTacToe;

public class Server {
  public static void main(String[] args) throws IOException {

    final int PORT_NUMBER = 2345;
    // this will never change for all of eternity. What even is a tictactoe with 3
    // players?
    final int PLAYERS_PER_GAME = 2;

    ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);

    Socket[] players = new Socket[PLAYERS_PER_GAME];

    Scanner[] playersIn = new Scanner[PLAYERS_PER_GAME];

    PrintWriter[] playersOut = new PrintWriter[PLAYERS_PER_GAME];

    TicTacToe gameBoard = new TicTacToe('x', 'o');

    for (int i = 0; i < PLAYERS_PER_GAME; i++) {
      players[i] = serverSocket.accept();
      playersIn[i] = new Scanner(players[i].getInputStream());
      playersOut[i] = new PrintWriter(players[i].getOutputStream(), true);
      playersOut[i].println("You're playing online TicTacToe over TCP.");
      playersOut[i].println("You're " + gameBoard.getPlayers()[i]);
      if (i < PLAYERS_PER_GAME - 1) {
        playersOut[i].println("Waiting for more players...");
      }
    }

    for (int i = 0; i < PLAYERS_PER_GAME; i++) {
      playersOut[i].println("Alright, let's get this party started!");
    }

    boolean nextPlayer;
    int nextPlayerAsInt;
    String userInput = "";
    while (gameBoard.getWinner() == null && !gameBoard.isBoardFilled()) {

      nextPlayer = gameBoard.getNextPlayer();
      nextPlayerAsInt = nextPlayer ? 0 : 1;

      for (int i = 0; i < PLAYERS_PER_GAME; i++) {
        playersOut[i].println(gameBoard.getFormattedBoard());
      }
      playersOut[nextPlayerAsInt].printf("Your turn (You're %c): ", gameBoard.getPlayers()[nextPlayerAsInt]);

      userInput = playersIn[nextPlayerAsInt].nextLine();
      userInput = userInput.strip().toUpperCase();
      System.out.println(userInput);
      if (!gameBoard.isOfRightFormat(userInput)) {
        continue;
      }
      gameBoard.performMove(gameBoard.convertMoveToIndex(userInput));
    }

    for (int i = 0; i < PLAYERS_PER_GAME; i++) {
      playersOut[i].println(gameBoard.getFormattedBoard());
    }

    // message the winners and losers independently
    Boolean winner = gameBoard.getWinner();
    if (winner == null) {
      for (int i = 0; i < PLAYERS_PER_GAME; i++) {
        playersOut[i].println("You tied!");
      }
    } else {
      playersOut[winner ? 0 : 1].println("You won!");
      playersOut[!winner ? 0 : 1].println("You lost!");
    }

    for (int i = 0; i < PLAYERS_PER_GAME; i++) {
      playersOut[i].println("Thanks for playing!");
      players[i].close();
    }

    serverSocket.close();
  }
}
