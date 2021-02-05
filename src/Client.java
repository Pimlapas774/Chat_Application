import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
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
    private JTextField chat_field;
    private JTextArea chat_area;
//    private JButton send_bt;
    private JPanel jPanel;
    private JPanel headPane;
    public JFrame jFrame;
    public JScrollPane jScrollPane;
    DefaultCaret caret;

    private String client_name;
    public Socket connect;
    String message;
    PrintWriter output;
    BufferedReader br;

    public Client(){
        jFrame = new JFrame();
        chat_field = new JTextField();
        chat_area = new JTextArea();

        Font font = new Font("Tahoma",Font.BOLD,14);
        jPanel = new JPanel(new BorderLayout());
        jPanel.setPreferredSize(new Dimension(500,25));
        jFrame.setLayout(new BorderLayout());
        jFrame.setContentPane(new JLabel(new ImageIcon("sunset_bg.png")));
        jFrame.setLayout(new BorderLayout());
        chat_area.setFont(font);
        chat_area.setEditable(false);
        chat_area.setOpaque(false);
        chat_area.setBackground(new Color(1,1,1,100));
        jScrollPane = new JScrollPane(chat_area);
        jScrollPane.getViewport().setOpaque(false);
        jScrollPane.setOpaque(false);
        jFrame.add(jScrollPane, BorderLayout.CENTER);

        headPane = new JPanel(new BorderLayout());
        Label headlabel = new Label("ChatRoom");
        headlabel.setFont(new Font("Tahoma",Font.BOLD,28));
        headlabel.setForeground(Color.WHITE);
        headPane.setBackground(Color.PINK);
        headlabel.setAlignment(Label.CENTER);
        headPane.add(headlabel,BorderLayout.CENTER);
        jFrame.add(headPane,BorderLayout.NORTH);

        chat_field.setFont(font);

        JLabel jLabel = new JLabel("ข้อความ: ");
        jLabel.setFont(new Font("Tahoma",Font.BOLD,14));
        jPanel.add(jLabel, BorderLayout.WEST);
        jPanel.add(chat_field, BorderLayout.CENTER);
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
        if(connect == null){
            showConnectFailed();
            do {
                ConnectToServer();
            }while (connect ==null);
        }
        Recieve_message(connect);
        jFrame.addWindowListener(new WindowAdapter() {
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
            chat_area.append("\n"+" Connect failed. " +
                    "Please restart program!\n");
            e.printStackTrace();
        }
    }

    public JTextField getChat_field() {
        return chat_field;
    }

    public String getClient_name() {
        return client_name;
    }

    public void showConnectFailed(){
        chat_area.append(" !!Connect failed!!");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()instanceof JTextField) {
            message = chat_field.getText();
            if (!message.isEmpty()) {
                chat_field.setText("");
                chat_area.append("\n"+"[\" "+client_name+" \"]: "+message+"\n");
                Send_message();
            }
        }
    }
}
