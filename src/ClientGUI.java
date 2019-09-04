import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class ClientGUI{
    public static JLabel lab_ip;
    public static JLabel lab_port;
    public static JTextField text_voca;
    public static JTextField text_explan;
    public JTextArea text_info;
    public JTextArea con_info;
    public JTextArea text_de;
    public static JFrame frame;
    public String command;
    public String vocabulary;
    public String explanation;
    public boolean send;
    public static int numClient;
    public static int numWords;
    private JScrollPane sp;
    public ClientGUI() {
        initialize();
    }

    public void initialize() {
        frame = new JFrame("Dictionary Client");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 400, 400);
        frame.add(panel);
        placeComponents(panel);



        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int i=JOptionPane.showConfirmDialog(null, "Sure to kill connection thread？", "exit", JOptionPane.YES_NO_OPTION);
                if(i==JOptionPane.YES_OPTION){

                    System.exit(0);
                }}});
        frame.setVisible(true);
    }


    public void placeComponents(JPanel panel) {

         panel.setLayout(null);

         JLabel lab_voca = new JLabel("vocabulary");
         JLabel lab_explan = new JLabel("explanation");
         lab_ip = new JLabel();
         lab_port = new JLabel();

        text_voca = new JTextField("");
        text_explan = new JTextField("");
        text_de = new JTextArea("");

        String[] commandList = new String[]{"search", "delete", "add"};
        JComboBox box_comm = new JComboBox<String>(commandList);

        JButton btn_clear = new JButton("clear");
        JButton btn_send = new JButton("send");
        send=false;
        btn_send.addActionListener(e->{

            command=(String) box_comm.getSelectedItem();
            vocabulary=text_voca.getText();
            explanation=text_explan.getText();
            send=true;
        });
        btn_clear.addActionListener(e->{

            text_voca.setText("");
            text_explan.setText("");
            text_info.setText("");
            con_info.setText("");

        });

        text_info = new JTextArea();
        con_info = new JTextArea();
        text_info.setEditable(false);
        text_info.setLineWrap(true);
        con_info.setLineWrap(true);
        text_info.setWrapStyleWord(true);


        lab_ip.setBounds(270, 20, 80, 20);
        lab_port.setBounds(40, 20, 80, 20);
        lab_voca.setBounds(40, 50, 80, 20);
        lab_explan.setBounds(40, 90, 80, 20);
        text_voca.setBounds(120, 50, 120, 20);
        text_explan.setBounds(120, 90, 120, 20);
        text_de.setBounds(120, 90, 120, 20);
        box_comm.setBounds(260, 90, 100, 20);
        btn_send.setBounds(266, 180, 80, 19);
        btn_clear.setBounds(266, 227, 80, 19);
        text_info.setBounds(40, 150, 200, 100);
        con_info.setBounds(40, 290, 300, 40);

        panel.add(lab_ip);
        panel.add(lab_port);
        panel.add(lab_voca);
        panel.add(lab_explan);
        panel.add(text_voca);
        panel.add(text_explan);
        panel.add(box_comm);
        panel.add(btn_send);
        panel.add(btn_clear);
        panel.add(text_info);
        panel.add(con_info);


    }

    public String getText(){
        String str=text_voca.getText();
        return text_voca.getText();
    }
    private JScrollPane getJTextArea(){
        if(con_info==null){
            con_info=new JTextArea();
            //jtextarea.setBounds(5, 45, 650, 400);
        }
        //jtextarea.setLineWrap(true);
        sp=new JScrollPane(con_info);
        sp.setBounds(40, 400, 200, 100);
        //sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return sp;
    }

}
