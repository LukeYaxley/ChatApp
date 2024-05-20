# Java Chat Application with GUI

## Overview

This project is a simple chat application built in Java, allowing clients to connect to a server and send messages to each other. The application includes a GUI client for user-friendly interaction.

## Features

- Server handles multiple clients simultaneously.
- Clients can send messages to other connected clients.
- GUI for the client application, built using Java Swing.
- Real-time message display.

## Requirements

- Java Development Kit (JDK) 8 or higher.
- A network environment where clients can connect to the server (localhost or a local network).

## Setup and Usage

### Server Setup

1. **Compile the Server:**

   ```sh
   javac Server.java
2. **Run the Server:**
   ```sh
   java Server
The server will start listening for client connections on port 12345.

## Client Setup
1. **Compile the Client:**
   ```sh
    javac ClientGUI.java
2. **Run the Client:**
   ```sh
     java ClientGUI
The client GUI will open, allowing you to enter a username, connect to the server, specify a recipient, and send messages

## Detailed Classes
### Server.java
The Server class creates a server socket to listen for incoming client connections. It uses a ClientHandler inner class to handle each client in a separate thread. Clients are registered with their usernames, and messages are forwarded to the intended recipients.

### ClientGUI.java
The ClientGUI class creates a graphical user interface for the client. It allows users to enter their username, connect to the server, and send messages to other clients. It handles the connection, input/output streams, and user interactions.

### Message.java
The Message class is designed to represent messages being sent between clients. Although not used directly in this implementation, it is useful for potential future extensions where message objects might be passed around instead of plain strings.

## Usage Instructions
### Start the Server:

- Open a terminal and navigate to the directory containing Server.java.
- Compile and run the server.
### Start the Client:

Open another terminal and navigate to the directory containing ClientGUI.java.
Compile and run the client.
Enter a username and click "Connect".
Enter the recipient's username and the message to send.
Click "Send" to send the message.
## Multiple Clients:

You can start multiple instances of ClientGUI to simulate multiple clients connecting to the server.

