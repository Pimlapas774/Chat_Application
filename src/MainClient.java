import javax.swing.*;

public class MainClient {

    public MainClient(){
        Client client = new Client();
        client.clientGui.jFrame.setSize(500,600);
        client.clientGui.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.clientGui.jFrame.setResizable(false);
        client.clientGui.jFrame.setVisible(true);
        client.clientGui.jFrame.setLocationRelativeTo(null);

        client.clientGui.chat_field.requestFocus();
        client.clientGui.jFrame.setTitle("Client: "+client.getClient_name());
    }

    public static void main(String[] args) {
        new MainClient();
    }
}
