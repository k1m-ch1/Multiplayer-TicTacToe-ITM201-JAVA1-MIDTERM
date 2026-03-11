import java.io.*;

class readingStream {

  public static void main(String[] args) throws IOException {
    // so System.in.read actually reads one raw byte.
    int input;
    InputStream inputBuffer = System.in;
    do {
      input = inputBuffer.read();
      System.out.printf("%d ", input);
    } while (input != 10);
    System.out.println("");
  }
}
