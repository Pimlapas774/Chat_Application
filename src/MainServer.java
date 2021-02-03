import javax.swing.*;

public class MainServer{

    public MainServer() {
        Server server = new Server();

        server.jFrame.setTitle("ChatServer");
        server.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        server.jFrame.setResizable(false);
        server.jFrame.setSize(400, 400);

        server.jFrame.setVisible(true);
        server.jFrame.setLocationRelativeTo(null);

    }
    public static void main(String[] args) {
        new MainServer();
    }
}
