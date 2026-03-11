# Multiplayer-TicTacToe-ITM201-JAVA1-MIDTERM

Simple multiplayer, terminal based TicTacToe over TCP

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
