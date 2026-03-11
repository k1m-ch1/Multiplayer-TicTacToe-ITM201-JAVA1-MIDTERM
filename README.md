# Compiling the code

If the directory look something like this:

```
├── LICENSE
├── README.md
├── out
├── src
   ├── ClientServer
   │   ├── Client.java
   │   └── Server.java
   └── TicTacToe
       └── TicTacToe.java
```

And you want to compile it to `./out`, just do something like:

```
java -d out/ src/ClientServer/*.java src/TicTacToe/*.java
```

And then you can run the server by doing:

```
javac -cp out ClientServer.Server
```

The `c` is probably for class, the `p` is probably for path.


# Connecting to the server

So this TCP server is hosted over at cloudflare. So as a client, we'd need to talk to it through websockets or something.

I'm just taking most of this from the [official cloudflare docs](https://developers.cloudflare.com/cloudflare-one/access-controls/applications/non-http/cloudflared-authentication/arbitrary-tcp/)

Setting this up requires `cloudflared` on the client's side.

On Linux:

```
sudo pacman -Sy cloudflared
```

or sth like that.

So right now, the server might be hosted on `tictactoe.k1mch1.space`.

On the client's side:

```
cloudflared access tcp --hostname tictactoe.k1mch1.space --url localhost:9210
```

This acts somewhat like a reverse proxy, where it just forwards the traffic from `tictactoe.k1mch1.space` into our own localhost port at some arbirary port (we've chosen `9210`, somewhat arbitrarily).

From there, you can connect to it using `netcat` or like whatever standard TCP client you want

```
nc localhost 9210
```

# Setting up the server

So the setting up of the server is pretty simple if you're hosting it on a LAN. Just run the `server.java` file, or like compile it or something.

If you want to host it on cloudflare, I won't go over it since it's mostly on the web, but basically, you need to first get a domain name and setup all the tunnel stuff. 

Anyway, assuming that you've alredy done all that, just go to the published application page and add a subdomain and link that to port `2345` or whatever port you're hosting it in localhost in your server.


# Some sources of References

- https://www.geeksforgeeks.org/java/how-to-create-a-simple-tcp-client-server-connection-in-java/

# TODO

## Client and Server

- ~~[] Create a test simple TCP client~~
- [x] Create a test simple TCP server

## Server

- [] Create a flow for users to connect, and so on.
- [] If have time, implement multi-threading

## TicTacToe UI

- [x] If we have 2D array represening the TicTacToe board, render it in ascii to print out to the terminal
- [x] Design the prompt, parsing user input, handling errors

## TicTacToe Logic

- [x] create a function to check whether it's valid to place stuff in the cell
- [x] function to check who has won (scan horizontal, vertical, diagonal)

# Notes

So a socket is quite self-explanatory since we need it to invoke like straight TCP socket syscalls to the kernel, but like, what about this InputStreamReader thing? What exactly is a stream when you do `clientSocket.getInputStream()`? What's a buffered reader?

So first of all, we need to understand what an input stream is.

```java
InputStream raw = clientSocket.getInputStream();
```

this represents an input stream that's coming from the socket.

And actually, the type of `System.in` and also `System.out` is exactly that `InputStream` class.

From there, you actually can read one byte at a time.

```java
raw.read();
```

But wait, let's just go back to `System.in`.

When we do a `System.in.read()`, the program halts, and pretty much the process goes into waiting mode, and get pushed into the waiting queue. But wait, even if you enter something into the terminal, the program doesn't resume until we press enter.

This is because the terminal is in canonical mode, which is also called line mode.

So this terminal mode is purely a result of the terminal or terminal emulator? is it a result of `bash`? None, it comes from the kernel itself, or the kernel's terminal driver.

Ok why does the kernel has a driver for a terminal? Isn't the terminal an abstract concept? Is it historical?

Ok so back in the days, people actually used physical terminals, which is probably like a keyboard and screen, but no GUI. TTY actually stands for teletype.

And built into the kernel, there are these features included:

- line editing mode (so that backspaces work)
- canonical mode (what the terminal uses, enter means send command)
- signal handling mode (ctrl + c)
- job control (ctrl + z)

Ok so when I switch to a TTY, the kernel creates a new virtual device called `/dev/ttyX`. Now the kernel has a TTY driver that has all these features mentioned above. 

So basically, to implment a terminal emulator, you'd need to talk to the virutal terminal `/dev/ttyX` which the kernel has already implemented a lot of the features, such as that canonical mode and like that line editing mode, and even `echo` so that we can see what we've entered, and maybe even edit it? And so, the terminal emulator simple receive what that virtual terminal of the kernel's doing and relay it by rendering it or something? So here's what the terminal emulator does:

- read bytes from that `/dev/ttyX` or something like `/dev/pts/0`
- renders that on the screen
- read keys from user
- relay that key to that `/dev/ttyX`

So when I do `input = inputBuffer.read()`, the terminal goes into canonical mode and so, it will write the user input into some buffer until I press enter. Once I press enter, it will send the whole buffer to the program I guess...

But acutally, `input = inputBuffer.read()` will only go to waiting mode if there isn't already stuff in the buffer. If there's stuff in the buffer, it will just pop off the next byte.

So to read one line, we can just continuously do `input = inputBuffer.read()` until we encounter a `\n`, represented by the number `10` in the ASCII table.

So we've established that `InputStream` is pretty primitive. Now, we pass it into an `InputStreamReader`. What functionality does `InputStreamReader` give us?

So `InputStreamReader` knows how to decode utf-8, utf-16, and more, which is a pretty big deal, which means that it can decode it into ASCII too (well, ASCII is a subset of utf-8 anyway, so it's kinda given...)

Now in `java`, it seems like most often, user input is handled using the `Scanner` function. In fact, this `Scanner` function is pretty high-level, as in it can parse doubles, ints, and automatically convert it to the right type.

Similarly, `System.out` is actually a `PrintWriter` object, but luckily, we don't have to wrap that in any more classes.
