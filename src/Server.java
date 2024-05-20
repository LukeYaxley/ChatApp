import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static HashMap<String, ClientHandler> clients = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void registerClient(String username, ClientHandler clientHandler) {
        clients.put(username, clientHandler);
    }

    public static synchronized ClientHandler getClient(String username) {
        return clients.get(username);
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private String username;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Enter your username: ");
                username = in.readLine();
                registerClient(username, this);

                String received;
                while ((received = in.readLine()) != null) {
                    String[] parts = received.split(":", 2);
                    if (parts.length == 2) {
                        String recipient = parts[0];
                        String message = parts[1];
                        ClientHandler recipientHandler = getClient(recipient);
                        if (recipientHandler != null) {
                            recipientHandler.sendMessage(username + ": " + message);
                        } else {
                            out.println("User " + recipient + " not found.");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }
}
