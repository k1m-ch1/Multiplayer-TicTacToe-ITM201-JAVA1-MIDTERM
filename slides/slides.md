---
title: JAVA 1 Midterm: Multiplayer TicTacToe
date: 2026-03-12 01:39
theme: uncover
class: invert
---

<h1>
  JAVA 1 Midterm: Multiplayer TicTacToe
</h1>


- Kimchour Luy (ID: 2025136)
- Hozany Mosa (ID: 2025364)
- Ehav Hour (ID: 2025137)

---

# Project directory

```
$ tree .
.
├── LICENSE
├── README.md
├── out
└─── src
    ├── ClientServer
    │   └── Server.java
    └── TicTacToe
        └── TicTacToe.java
```

---

# What magic are we using?

- Transmission Control Protocol (TCP)
- Plain ol' `java`

---

# TCP model

TCP is usually implemented with a client-server model.

- server is always online
- client sends request to server
- server accepts and respond back
- $\vdots$
- client disconnects or server dies
- the end

---

# How to use TCP in `java`?

```java
int PORT_NUMBER = 2345;
ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
System.out.println("Server is running and waiting for clients to connect.");

Socket clientSocket = serverSocket.accept();
System.out.println("Client connected!");

Scanner clientScanner = new Scanner(clientSocket.getInputStream());

String message = clientScanner.nextLine();
System.out.println("Client says: " + message);
```

---

# Server Flowchart

![](./images/tictactoe_server.drawio.svg)


---

# Client

What about the client?

* the kernel is the one truly implementing the TCP client
* can be exposed as some wrapper in `java`, `python`, `c`, ...

---

We'll use a ready built TCP client called `netcat`

```
nc localhost 2345
```

---

# Game logic

I'm sure y'all know how TicTacToe works...

![](./images/tictactoe_game_logic.svg)

---

# More details

So we have a board like this:

```
3:    |   | x 
   ---+---+---
2:    | x |   
   ---+---+---
1:  x | o | o 
    A   B   C 
```

---

# Board representation expectations

How do we represent this board?

No brainer. 2d `char` array right?

```java
char[] board = {
  {' ', ' ', 'x'},
  {' ', 'x', ' '},
  {'x', 'o', 'o'}
}

```

---

# Board representation reality

Since we're psychopaths:

```java
Boolean[] board = {
  {null, null, true},
  {null, true, null},
  {true, false, false}
}
```

---

# Why?

- We consider `true` to be the first player (player who goes first)
- and `false` to be the second player.
- also, it makes searching for winners a bit easier (still very messy tho)

---

# How to check who won?

We can easily by doing:

- To check whether `true` has won 
```java
board[0][0] && board[0][1] && board[0][2]
```
- To check whether `false` has won
```java
!board[0][0] && !board[0][1] && !board[0][2]
```
- $\vdots$

Note that we have to handle `null` too

---

Just do that for 

- rows
- columns
- diagonals

---

<h1> Demo time! </h1>

---

<h1>
Thanks for listening!
</h1>


