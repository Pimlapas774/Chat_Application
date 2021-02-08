import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Set;

public class Server implements ActionListener {
    private static final int port_number = 12221;
    Server_GUI serverGui;
    public Socket connect;
    ServerThread serverThread;
    Set<ServerThread> threadlist;
    Set<String> usernames = new HashSet<>();
    DateTimeFormatter tf_msg;
    DateTimeFormatter tf_server;
    DateTimeFormatter df_msg;
    LocalDateTime now;

    String server_massage;
    String server_recieve;
    LogChat logChat;
    BoxChatP boxChatP;


    public Server(){
        serverGui = new Server_GUI();
        boxChatP = new BoxChatP();
        logChat = new LogChat();
        logChat.CreateTextfile();
        tf_server = DateTimeFormatter.ofPattern("HH:mm:ss");
        tf_msg = DateTimeFormatter.ofPattern("HH:mm");
        df_msg = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        serverGui.server_header_bt.addActionListener(this);
        serverGui.server_header_bt.setVisible(false);

        logChat.writeLog_File(df_msg.format(LocalDateTime.now())
                +", "+tf_server.format(LocalDateTime.now()));

        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitingClient();
            }
        });
        th1.start();
    }

    public void waitingClient(){
        threadlist = new HashSet<>();
        try {

            serverGui.field_area.append("Server running ...\n");
            serverGui.field_area.append("Waiting for clients\n");
            ServerSocket server = new ServerSocket(port_number);
            while (true) {
                connect = server.accept();
                serverThread = new ServerThread(connect, this);
                threadlist.add(serverThread);
                serverGui.server_header_bt.setVisible(true);
                serverThread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printMsgToAllClients(String msg) throws UnknownHostException {
        now = LocalDateTime.now();
        server_recieve = msg;
        server_massage = msg+ " {" + tf_msg.format(now) + "}";
        for(ServerThread ls: threadlist){
            ls.output.println(boxChatP.BoxChatPMsgFormatter(server_massage,"GET_MSG"));
        }
        logChat.writeLog_File(server_massage);
    }

    public void printNoticeToClient(String notice){
        server_massage = notice;
        serverThread.output.println(server_massage);
    }

    public void isUsername(String name){
        if(usernames.contains(name)){
            int random = (int)(Math.random()*1000);
            serverThread.user_name[1] = name+""+random;
        }
    }

    public void addUsernames(String user){
            usernames.add(user);
    }

    public void removeUsernames(String user){
        usernames.remove(user);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        new Server_header(this);
    }
}
