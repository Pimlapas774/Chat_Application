import javax.swing.*;
import java.awt.*;

public class Client_header {
    JFrame jFrame_header;
    public static JTextArea client_headerArea;
    public Client_header(Client client) {
        jFrame_header = new JFrame();
        client_headerArea = new JTextArea();

        client_headerArea.setFont(new Font("Tahoma",Font.BOLD,14));
        jFrame_header.setLayout(new BorderLayout());
        jFrame_header.add(new JScrollPane(client_headerArea),BorderLayout.CENTER);

        jFrame_header.setTitle("BoxChatP header");
        jFrame_header.setSize(400,350);
        jFrame_header.setResizable(false);
        jFrame_header.setVisible(true);
        jFrame_header.setLocationRelativeTo(client.clientGui.jFrame);

        String request_msgs = client.boxChatP.printMessageRequest(client.meth,client.message);
        client_headerArea.append(request_msgs);
        String response_msgs = client.boxChatP.printMessageResponse(client.recieve[1]);
        client_headerArea.append(response_msgs);
    }

}