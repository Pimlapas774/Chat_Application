import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.Set;

public class Server{
    private static final int port_number = 9080;
    private JTextArea field_area = new JTextArea();
    public JFrame jFrame;
    public Socket connect;
    DefaultCaret caret;
    ServerThread serverThread;
    Set<ServerThread> threadlist;
    Set<String> usernames = new HashSet<>();


    public Server(){
        jFrame = new JFrame();
        Font font = new Font("Tahoma",Font.PLAIN,14);
        jFrame.setLayout(new BorderLayout());
        field_area.setFont(font);
        field_area.setEditable(false);
        field_area.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        jFrame.add(new JScrollPane(field_area), BorderLayout.CENTER);
        caret = (DefaultCaret) field_area.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitingClient();
            }
        });
        th1.start();
    }

    public JTextArea getField_area() {
        return field_area;
    }

    public void setField_area(JTextArea field_area) {
        this.field_area = field_area;
    }

    public void waitingClient(){
        threadlist = new HashSet<>();
        try {
            field_area.append("Server running ...\n");
            field_area.append("Waiting for clients\n");
            ServerSocket server = new ServerSocket(port_number,10);
            while (true) {
                connect = server.accept();
                serverThread = new ServerThread(connect, this);
                threadlist.add(serverThread);
                serverThread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printToAllClients(String msg, ServerThread excluded_user){
        for(ServerThread ls: threadlist){
            if(ls != excluded_user) {
                ls.output.println(msg);
            }
        }
    }

    public void printToClient(String notice){
        serverThread.output.println(notice);
    }


    public Set<String> getUsernames() {
        return usernames;
    }

    public void addUsernames(String user){
        usernames.add(user);
    }

    public boolean isUsername(){
        return !usernames.isEmpty();
    }
}
