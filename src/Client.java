import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread implements ActionListener {
    private static final int port_number = 9080;

    Client_GUI clientGui;
    private String client_name;
    public Socket connect;
    String message;
    PrintWriter output;
    BufferedReader br;

    public Client() {
        clientGui = new Client_GUI();

        clientGui.chat_field.addActionListener(this);
        client_name = JOptionPane.showInputDialog(clientGui.jFrame, "Please,Enter your name", "Welcome to " +
                "Chatroom", JOptionPane.PLAIN_MESSAGE);
        start();


    }

    @Override
    public void run() {
        ConnectToServer();
        if (connect == null) {
            showConnectFailed();
            do {
                ConnectToServer();
            } while (connect == null);
        }
        Recieve_message(connect);
        clientGui.jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    connect.close();
                    br.close();
                    output.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }


    public void ConnectToServer() {
        try {
            connect = new Socket(InetAddress.getLocalHost(), port_number);
            output = new PrintWriter(connect.getOutputStream(), true);
            output.println(client_name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Send_message() {
        try {
            output = new PrintWriter(connect.getOutputStream(), true);
            output.println("[" + client_name + "]: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Recieve_message(Socket socket) {
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String response = br.readLine();
                clientGui.chat_area.append("\n" + " " + response + "\n");
            }
        } catch (IOException e) {
            clientGui.chat_area.append("\n" + " Connect failed. " +
                    "Please restart program!\n");
            e.printStackTrace();
        }
    }


    public String getClient_name() {
        return client_name;
    }

    public void showConnectFailed() {
        clientGui.chat_area.append(" !!Connect failed!!");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JTextField) {
            message = clientGui.chat_field.getText();
            if (!message.isEmpty()) {
                clientGui.chat_field.setText("");
                clientGui.chat_area.append("\n" + "[\" " + client_name + " \"]: " + message + "\n");
                Send_message();
            }
        }
    }
}
