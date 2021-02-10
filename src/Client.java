import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread implements ActionListener {
    private static final int port_number = 12221;
    private String server_name;//ipv4 address ,VPN ku

    Client_GUI clientGui;
    private String client_name;
    public Socket connect;
    String message;
    String recieve[];
    String meth = "GET_MSG";
    PrintWriter output;
    BufferedReader br;

    BoxChatP boxChatP;

    public Client(){
        clientGui = new Client_GUI();

        clientGui.chat_field.addActionListener(this);
        clientGui.client_header_bt.addActionListener(this);
        client_name = JOptionPane.showInputDialog(clientGui.jFrame,"Please,Enter your name","Welcome to " +
                "Chatroom",JOptionPane.PLAIN_MESSAGE);
        if(client_name == null){
            client_name = "unknown";
        }
        if(client_name.isEmpty()){
            client_name = "unknown";
        }

        message = client_name;
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
        clientGui.jFrame.setTitle("Client: "+getClient_name());
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


    public void ConnectToServer() {//136
        try {
            connect = new Socket(InetAddress.getLocalHost(), port_number);

            boxChatP = new BoxChatP();
            output = new PrintWriter(connect.getOutputStream(), true);
            output.println(boxChatP.BoxChatPMsgFormatter(message,meth));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Send_message(){
        try {
            output = new PrintWriter(connect.getOutputStream(), true);
            output.println(boxChatP.BoxChatPMsgFormatter(message,meth));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Recieve_message(Socket socket){
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            EditedClientTitleName(br);
            while (true){
                recieve = br.readLine().split("฿");
                clientGui.chat_area.append("\n"+" "+recieve[1] + "\n");
            }
        } catch (IOException e) {
            clientGui.chat_area.append("\n"+" Connect failed. " +
                    "Please restart program!\n");
            e.printStackTrace();
        }
    }


    public String getClient_name() {
        return client_name;
    }

    public void EditedClientTitleName(BufferedReader reader) throws IOException {
        recieve = reader.readLine().split("฿");
        String[] edit_name = recieve[1].split(",");
        client_name = edit_name[1];
        clientGui.jFrame.setTitle("Client: "+client_name);
        clientGui.chat_area.append("\n"+" "+recieve[1] + "\n");
    }

    public void showConnectFailed(){
        clientGui.chat_area.append(" !!Connect failed!!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()instanceof JTextField) {
            message = clientGui.chat_field.getText();
            if (!message.isEmpty()) {
                clientGui.chat_field.setText("");
                Send_message();
            }
        }
        if(e.getSource()instanceof JButton){
            JButton button = (JButton) e.getSource();
            if(button.getText().equals("header")){
                new Client_header(this);
            }
        }
    }
}
