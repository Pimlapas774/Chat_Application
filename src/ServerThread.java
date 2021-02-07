import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServerThread extends Thread {
    public PrintWriter output;
    public Socket socket;
    public Server server;
    public String user_name;
    private String outputstr;
    private InputStream input;
    public ServerThread(Socket socket,Server server){
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            input = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            output = new PrintWriter(socket.getOutputStream(),true);

            user_name = bufferedReader.readLine();
            server.addUsernames(user_name);
            server.now = LocalDateTime.now();
            server.serverGui.field_area.append("Connected user: "+"["+user_name+"]"+
                    " ("+ server.tf_server.format(server.now)+")"+"\n");

            server.printNoticeToClient("[Server]: "+"Connected Successfully");
            server.printMsgToAllClients("[Server]: " + "[" + user_name + "]"
                    + " has joined!");
            try {
                while (true) {
                    outputstr = bufferedReader.readLine();
                    server.printMsgToAllClients("[" + user_name + "]: " + outputstr);
                }
            }catch (Exception e){//when the user closed program, telling to other users.
                server.now = LocalDateTime.now();
                server.serverGui.field_area.append("Disconnected user: "+"["+user_name+"]"+
                        " ("+ server.tf_server.format(server.now)+")"+"\n");
                server.printMsgToAllClients("[Server]: " + "[" + user_name + "]" + " has left!");
            }
            socket.close();
            server.removeUsernames(user_name);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
