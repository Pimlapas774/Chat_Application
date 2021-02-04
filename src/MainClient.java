import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainClient {

    public MainClient(){
        Client client = new Client();
        client.jFrame.setSize(500,600);
        client.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.jFrame.setResizable(false);
        client.jFrame.setVisible(true);
        client.jFrame.setLocationRelativeTo(null);

        client.getChat_field().requestFocus();
        client.jFrame.setTitle("Client: "+client.getClient_name());
    }

    public static void main(String[] args) {
        new MainClient();
    }
}
