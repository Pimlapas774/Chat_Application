import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;

public class ServerThread extends Thread {
    public PrintWriter output;
    public Socket socket;
    public Server server;
    public ServerThread(Socket socket,Server server){
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(),true);

            while (true){
                String outputstr = bufferedReader.readLine();
                server.printToAllClients(outputstr, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
