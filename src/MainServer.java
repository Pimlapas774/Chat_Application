import javax.swing.*;

public class MainServer{

    public MainServer() {
        Server server = new Server();

        server.serverGui.jFrame.setTitle("ChatServer");
        server.serverGui.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        server.serverGui.jFrame.setResizable(false);
        server.serverGui.jFrame.setSize(400, 400);

        server.serverGui.jFrame.setVisible(true);
        server.serverGui.jFrame.setLocationRelativeTo(null);

    }
    public static void main(String[] args) {
        new MainServer();
    }
}
