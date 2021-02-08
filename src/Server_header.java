import javax.swing.*;
import java.awt.*;

public class Server_header {
    JFrame jFrame_header;
    public static JTextArea server_headerArea;
    public Server_header(Server server) {
        jFrame_header = new JFrame();
        server_headerArea = new JTextArea();

        server_headerArea.setFont(new Font("Tahoma",Font.BOLD,14));
        jFrame_header.setLayout(new BorderLayout());
        jFrame_header.add(new JScrollPane(server_headerArea),BorderLayout.CENTER);

        jFrame_header.setTitle("BoxChatP header");
        jFrame_header.setSize(400,350);
        jFrame_header.setResizable(false);
        jFrame_header.setVisible(true);
        jFrame_header.setLocationRelativeTo(server.serverGui.jFrame);

        String request_msgs = server.boxChatP.printMessageRequest(server.serverThread.meth
                ,server.server_massage);
        server_headerArea.append(request_msgs);
        String response_msgs = server.boxChatP.printMessageResponse(server.server_recieve);
        server_headerArea.append(response_msgs);

    }
}
