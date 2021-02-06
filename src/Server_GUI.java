import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class Server_GUI {
    public JTextArea field_area;
    public JFrame jFrame;
    DefaultCaret caret;

    public Server_GUI() {
        field_area = new JTextArea();
        jFrame = new JFrame();
        Font font = new Font("Tahoma",Font.PLAIN,14);
        jFrame.setLayout(new BorderLayout());
        field_area.setFont(font);
        field_area.setEditable(false);
        field_area.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        jFrame.add(new JScrollPane(field_area), BorderLayout.CENTER);
        caret = (DefaultCaret) field_area.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
}
