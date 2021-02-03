import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread implements ActionListener {
    private static final int port_number = 9080;
    private JTextField chat_field;
    private JTextArea chat_area;
//    private JButton send_bt;
    private JPanel jPanel;
    public JFrame jFrame;
    DefaultCaret caret;

    private String client_name;
    private Socket connect;
    String message;
    PrintWriter output;
    BufferedReader br;

    public Client(){
        jFrame = new JFrame();
        chat_field = new JTextField();
        chat_area = new JTextArea();
//        send_bt = new JButton("Send");


        Font font = new Font("Tahoma",Font.PLAIN,14);
        jPanel = new JPanel(new BorderLayout());
        jPanel.setPreferredSize(new Dimension(500,25));
        jFrame.setLayout(new BorderLayout());
        chat_area.setFont(font);
        chat_area.setEditable(false);
//        chat_area.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        jFrame.add(new JScrollPane(chat_area), BorderLayout.CENTER);
        chat_field.setFont(font);

        JLabel jLabel = new JLabel("สนทนา");
        jLabel.setFont(new Font("Tahoma",Font.BOLD,14));
        jPanel.add(jLabel, BorderLayout.WEST);
        jPanel.add(chat_field, BorderLayout.CENTER);
//        jPanel.add(send_bt,BorderLayout.EAST);
        jFrame.add(jPanel,BorderLayout.SOUTH);
        caret = (DefaultCaret) chat_area.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        chat_field.addActionListener(this);
        client_name = JOptionPane.showInputDialog(jFrame,"Please,Enter your name","Welcome to Chatroom",JOptionPane.PLAIN_MESSAGE);
        start();


    }

    @Override
    public void run() {
        ConnectToServer();
        Recieve_message(connect);
    }

    public void ConnectToServer() {
        try {
            connect = new Socket(InetAddress.getLocalHost(),port_number);
            output = new PrintWriter(connect.getOutputStream(),true);
            output.println(client_name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Send_message(){
        try {
            output = new PrintWriter(connect.getOutputStream(), true);
            output.println("["+client_name+"]: "+message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Recieve_message(Socket socket){
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true){
                String response = br.readLine();
                chat_area.append("\n"+" "+response + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public JTextField getChat_field() {
        return chat_field;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()instanceof JTextField) {
            message = chat_field.getText();
            if (!message.isEmpty()) {
                chat_field.setText("");
                Send_message();
            }
        }
    }
}
