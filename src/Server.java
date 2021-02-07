import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Set;

public class Server{
    private static final int port_number = 9080;
    Server_GUI serverGui;
    public Socket connect;
    ServerThread serverThread;
    Set<ServerThread> threadlist;
    Set<String> usernames = new HashSet<>();
    DateTimeFormatter tf_msg;
    DateTimeFormatter tf_server;
    DateTimeFormatter df_msg;
    LocalDateTime now;


    public Server(){
        serverGui = new Server_GUI();
        tf_server = DateTimeFormatter.ofPattern("HH:mm:ss");
        tf_msg = DateTimeFormatter.ofPattern("HH:mm");
        df_msg = DateTimeFormatter.ofPattern("yyyy/MM/dd");

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

    public void printMsgToAllClients(String msg){
        now = LocalDateTime.now();
        for(ServerThread ls: threadlist){
            ls.output.println(msg + " {" + tf_msg.format(now) + "}");
        }
    }

    public void printNoticeToClient(String notice){
        serverThread.output.println(notice);
    }


    public Set<String> getUsernames() {
        return usernames;
    }

    public void addUsernames(String user){
        usernames.add(user);
    }

    public void removeUsernames(String user){
        usernames.remove(user);
    }

    public boolean isUsername(){
        return !usernames.isEmpty();
    }
}
