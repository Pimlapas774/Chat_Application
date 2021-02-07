import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class Client_GUI {
    public JTextField chat_field;
    public JTextArea chat_area;
    public JPanel jPanel;
    public JPanel headPane;
    public JFrame jFrame;
    public JScrollPane jScrollPane;
//    public JButton  history_bt;
    DefaultCaret caret;

    public Client_GUI() {
        jFrame = new JFrame();
        chat_field = new JTextField();
        chat_area = new JTextArea();
//        history_bt = new JButton("History");

        Font font = new Font("Tahoma",Font.BOLD,14);
        jPanel = new JPanel(new BorderLayout());
        jPanel.setPreferredSize(new Dimension(500,25));
        jFrame.setLayout(new BorderLayout());
        jFrame.setContentPane(new JLabel(new ImageIcon("sunset_bg.png")));
        jFrame.setLayout(new BorderLayout());
        chat_area.setFont(font);
        chat_area.setEditable(false);
        chat_area.setOpaque(false);
        chat_area.setBackground(new Color(1,1,1,100));
        jScrollPane = new JScrollPane(chat_area);
        jScrollPane.getViewport().setOpaque(false);
        jScrollPane.setOpaque(false);
        jFrame.add(jScrollPane, BorderLayout.CENTER);

        headPane = new JPanel(new BorderLayout());
        Label headlabel = new Label("ChatRoom");
        headlabel.setFont(new Font("Tahoma",Font.BOLD,28));
        headlabel.setForeground(Color.WHITE);
        headPane.setBackground(Color.PINK);
        headlabel.setAlignment(Label.CENTER);
        headPane.add(headlabel,BorderLayout.CENTER);
        jFrame.add(headPane,BorderLayout.NORTH);

        chat_field.setFont(font);

        JLabel jLabel = new JLabel("ข้อความ: ");
        jLabel.setFont(new Font("Tahoma",Font.BOLD,14));
        jPanel.add(jLabel, BorderLayout.WEST);
        jPanel.add(chat_field, BorderLayout.CENTER);
//        jPanel.add(history_bt, BorderLayout.EAST);
        jFrame.add(jPanel,BorderLayout.SOUTH);
        caret = (DefaultCaret) chat_area.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
}
