import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClientGUI {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private JFrame frame;
    private JTextField usernameField;
    private JTextField recipientField;
    private JTextField messageField;
    private JTextArea messageArea;
    private JButton sendButton;
    private JButton connectButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientGUI::new);
    }

    public ClientGUI() {
        frame = new JFrame("Client GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        usernameField = new JTextField("Enter username");
        recipientField = new JTextField("Enter recipient");
        messageField = new JTextField();
        messageArea = new JTextArea();
        messageArea.setEditable(false);

        sendButton = new JButton("Send");
        connectButton = new JButton("Connect");

        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        topPanel.add(new JLabel("Username:"));
        topPanel.add(usernameField);
        topPanel.add(new JLabel("Recipient:"));
        topPanel.add(recipientField);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(messageArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        bottomPanel.add(connectButton, BorderLayout.WEST);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        sendButton.addActionListener(e -> sendMessage());
        connectButton.addActionListener(e -> connectToServer());
    }

    private void connectToServer() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username cannot be empty.");
            return;
        }

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println(username);

            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        messageArea.append(serverMessage + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            connectButton.setEnabled(false);
            usernameField.setEditable(false);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Could not connect to server.");
        }
    }

    private void sendMessage() {
        String recipient = recipientField.getText().trim();
        String message = messageField.getText().trim();

        if (recipient.isEmpty() || message.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Recipient and message cannot be empty.");
            return;
        }

        out.println(recipient + ":" + message);
        messageField.setText("");
    }
}
