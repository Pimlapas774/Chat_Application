import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class Server_GUI {
    public JTextArea field_area;
    public JFrame jFrame;
    public JButton server_header_bt;
    DefaultCaret caret;

    public Server_GUI() {
        field_area = new JTextArea();
        jFrame = new JFrame();

        JPanel panel_server = new JPanel(new BorderLayout());
        Label label = new Label("ChatServer");
        label.setFont(new Font("Tahoma",Font.BOLD,28));
        label.setForeground(Color.MAGENTA);
        label.setAlignment(Label.CENTER);
        panel_server.setBackground(Color.black);
        panel_server.add(label, BorderLayout.CENTER);
        server_header_bt = new JButton("header");
        JPanel pane_header = new JPanel(new BorderLayout());
        pane_header.add(server_header_bt,BorderLayout.EAST);
        panel_server.add(pane_header, BorderLayout.PAGE_END);
        jFrame.add(panel_server, BorderLayout.NORTH);

        Font font = new Font("Tahoma",Font.PLAIN,14);
        field_area.setFont(font);
        field_area.setEditable(false);
        field_area.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        jFrame.add(new JScrollPane(field_area), BorderLayout.CENTER);
        caret = (DefaultCaret) field_area.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
}
