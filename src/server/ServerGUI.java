package server;

//author:Wei LIN
//number:885536
//id:wlin8
//

import javax.net.ServerSocketFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class ServerGUI {
    public JFrame frame;
    public JTextField text_get;
    public JTextArea text_log;
    public JTextArea text_ter;
    public JTextArea text_file;
    public static int numClient;
    public static int numWords;
    public Label text_client;
    public Label text_word;
    private int port;
    private static String path;

    public JScrollPane scrol;

    private static int counter = 0;
    private ExecutorService pool = null;
    public DicMap dicMap;

    public ServerGUI(int port, String path) {
        this.port = port;
        this.path = path;
        dicMap = new DicMap();
        initialize();
    }

    public void initialize() {

        numClient = 0;
        numWords = 0;
        frame = new JFrame("Dictionary Server");
        frame.setSize(420, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // URL url = getClass().getResource("server.png");

        //ImageIcon icon = new ImageIcon(url);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 420, 400);
        frame.add(panel);

        placeComponents(panel);

        JScrollPane scrol = new JScrollPane();
        //scrol.setBounds(400, 0, 400, 400);
        //frame.add(scrol);
        placeCompo(scrol);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Sure to kill connection thread？", "exit", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {

                    System.exit(0);
                }
            }
        });


    }


    public void placeComponents(JPanel panel) {

        panel.setLayout(null);


        JLabel lab_port = new JLabel(String.valueOf("port: " + port));
        JLabel lab_path = new JLabel("path: " + path);

        text_client = new Label("clients count: " + 2);
        text_word = new Label("words count: " + 153);

        JButton btn_clr = new JButton("clear");
        JButton btn_save = new JButton("save");
        JButton btn_load = new JButton("load");

        text_get = new JTextField(path);
        text_log = new JTextArea();
        text_ter = new JTextArea();
        text_log.setLineWrap(true);
        text_ter.setLineWrap(true);


        lab_port.setBounds(35, 20, 100, 20);
        lab_path.setBounds(288, 20, 100, 20);
        text_get.setBounds(30, 120, 80, 20);
        text_log.setBounds(35, 60, 335, 105);
        text_ter.setBounds(35, 210, 335, 105);
        text_client.setBounds(276, 180, 120, 20);
        text_word.setBounds(280, 330, 120, 20);
        btn_clr.setBounds(40, 180, 60, 20);
        btn_save.setBounds(40, 330, 60, 20);
        btn_load.setBounds(140, 330, 60, 20);

        // panel.add(text_get);
        panel.add(text_log);
        panel.add(text_ter);
        panel.add(lab_port);
        panel.add(lab_path);
        panel.add(btn_clr);
        panel.add(btn_save);
        panel.add(btn_load);
        panel.add(text_word);
        panel.add(text_client);

        btn_clr.addActionListener(e -> {

            text_log.setText("");
            text_ter.setText("");

            printDicMap();
            Iterator iter = dicMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                text_file.append(key + ":" + value);
            }

            text_file.append("df");
        });


    }

    public void placeCompo(JScrollPane panel) {

        panel.setLayout(null);
        text_file = new JTextArea("o");
        panel.add(text_file);
        text_file.setBounds(450, 0, 300, 300);


    }

    public void printDicMap() {

        Iterator iter = dicMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            text_file.append(key + ":" + value);
            text_file.append("j");
        }

        /*
        for (TypeKey name: dicMap.keySet()){

            String key =name.toString();
            ArrayList value = dicMap.get(name).toString();
            System.out.println(key + " " + value);


        }*/
        /*
        Set<String> keys=dicMap.keySet();
        Iterator<String> iter=keys.iterator();
        while(iter.hasNext())
        {
            text_file.append(iter.next().getKey());
        }
         */

    }

    public void listen() {

        ThreadExcutor excutor = new ThreadExcutor(10);


        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        try (ServerSocket serversocket = factory.createServerSocket(port);) {


            text_log.append("Server start\n");
            text_log.append("Listening for clients\n");


            while (true) {

                Socket clientsocket = serversocket.accept();
                counter++;
                text_log.append("client" + counter + " connected\n");

                numClient++;
                text_client.setText("clients count: " + numClient);

                ConnectionThread thread = new ConnectionThread(clientsocket, counter, this);
                excutor.exec(thread);


            }


        } catch (IOException e) {
            e.printStackTrace();
            pool.shutdown();
        }
    }

}
