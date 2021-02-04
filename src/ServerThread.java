import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;

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
            server.getField_area().append("Connected user: "+"["+user_name+"]\n");

            server.printToClient("[Server]: "+"Connected Successfully");
            server.printToAllClients("[Server]: " + "[" + user_name + "]"
                    + " has joined!",this);
            try {
                while (true) {
                    outputstr = bufferedReader.readLine();
                    server.printToAllClients(outputstr, this);
                }
            }catch (Exception e){//when the user closed program, telling to other users.
                server.getField_area().append("Disconnected user: "+"["+user_name+"]\n");
                server.printToAllClients("[Server]: " + "[" + user_name + "]" + " has left!",this);
            }
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
