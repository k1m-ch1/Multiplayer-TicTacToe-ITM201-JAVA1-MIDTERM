import java.io.*;

public class usingStreamReader {
  public static void main(String[] args) throws IOException {
    // so we've established that System.in is an input stream

    InputStream input = System.in;

    // so we can wrapper it in a reader
    InputStreamReader inputReader = new InputStreamReader(input);

    // so
    // so now we got the readline functionality
  }
}
