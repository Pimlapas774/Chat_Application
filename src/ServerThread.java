import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServerThread extends Thread {
    public PrintWriter output;
    public Socket socket;
    public Server server;
    public String[] user_name;
    public String[] outputstr;
    private InputStream input;
    String annouce;
    public BoxChatP boxChatP;
    String meth = "GET_MSG";
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

            user_name = bufferedReader.readLine().split("฿");
            server.isUsername(user_name[1]);
            server.addUsernames(user_name[1]);
            server.now = LocalDateTime.now();
            boxChatP = new BoxChatP();
            server.serverGui.field_area.append("Connected user: "+"["+user_name[1]+"]"+
                    " ("+ server.tf_server.format(server.now)+")"+"\n");

            annouce = "[Server]: "+"Connected Successfully,"+user_name[1];
            server.printNoticeToClient(boxChatP.BoxChatPMsgFormatter(annouce,meth));

            annouce = "[Server]: " + "[" + user_name[1] + "]" + " has joined!";
            server.printMsgToAllClients(annouce);
            try {
                while (true) {
                    outputstr = bufferedReader.readLine().split("฿");
                    server.printMsgToAllClients("[" + user_name[1] + "]: " + outputstr[1]);
                }
            }catch (Exception e){//when the user closed program, telling to other users.
                server.now = LocalDateTime.now();
                server.serverGui.field_area.append("Disconnected user: "+"["+user_name[1]+"]"+
                        " ("+ server.tf_server.format(server.now)+")"+"\n");
                annouce = "[Server]: " + "[" + user_name[1] + "]" + " has left!";
                server.printMsgToAllClients(annouce);

            }
            socket.close();
            server.removeUsernames(user_name[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
