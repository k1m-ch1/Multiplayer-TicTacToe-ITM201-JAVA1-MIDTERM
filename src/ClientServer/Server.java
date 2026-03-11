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
    System.out.println("Server is running and waiting for clients to connect.");

    // this looks like it's a blocking method
    Socket clientSocket = serverSocket.accept();

    System.out.println("Client connection!");

    // so we use the IO library for this...
    InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());

    BufferedReader in = new BufferedReader(inputStreamReader);

    // it seems like this outputStream and inputStream thing is almost like a
    // System.in stream
    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

    String message = in.readLine();

    System.out.println("Client says: " + message);

    // so this is how to talk back to the client
    out.println("Message received by the server.");

    clientSocket.close();
    serverSocket.close();

  }
}
