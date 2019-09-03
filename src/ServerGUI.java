import javax.net.ServerSocketFactory;
import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerGUI {
    public JFrame frame;
    public JTextField text_get;
    public JTextArea text_log;
    public JTextArea text_ter;
    private int port;
    private static String path;


    private static int counter=0;
    private ExecutorService pool=null;
    public DicMap dicMap;

    public ServerGUI(int port, String path){
        this.port=port;
        this.path=path;
        dicMap=new DicMap();
        initialize();
    }
    public void initialize(){
        frame = new JFrame("Dictionary Server");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

    }
    public void placeComponents(JPanel panel) {

        panel.setLayout(null);


        JLabel lab_port=new JLabel(String.valueOf("port: "+port));
        JLabel lab_path=new JLabel("path: "+path);
        JButton btn_clr = new JButton("clear");

        text_get=new JTextField(path);
        text_log=new JTextArea();
        text_ter=new JTextArea();
        text_log.setLineWrap(true);
        text_ter.setLineWrap(true);


        lab_port.setBounds(40,20,100,20);
        lab_path.setBounds(260,20,100,20);
        text_get.setBounds(30, 120, 80, 20);
        text_log.setBounds(30, 60, 300, 140);
        text_ter.setBounds(30, 240, 300, 60);
        btn_clr.setBounds(30,320,60,20);

       // panel.add(text_get);
        panel.add(text_log);
        panel.add(text_ter);
        panel.add(lab_port);
        panel.add(lab_path);
        panel.add(btn_clr);

        btn_clr.addActionListener(e->{

            text_log.setText("");
            text_ter.setText("");


        });

    }

    public void listen() {

        ThreadExcutor excutor = new ThreadExcutor(10);


        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        try(ServerSocket serversocket = factory.createServerSocket(port);){


            System.out.println("Server start");
            System.out.println("Listening for clients");


            while (true) {

                Socket clientsocket = serversocket.accept();
                counter++;
                text_log.append("client"+counter+" connected\n");

                ConnectionThread thread=new ConnectionThread(clientsocket,counter,this,dicMap);
                excutor.exec(thread);


            }
        } catch (IOException e) {
            e.printStackTrace();
            pool.shutdown();
        }
    }

}
