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
    private int port;
    private static String path;


    private static int counter=0;
    private ExecutorService pool=null;

    public ServerGUI(int port, String path){
        this.port=port;
        this.path=path;
        initialize();
    }
    public void initialize(){
        frame = new JFrame("Dictionary Server");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

    }
    public void placeComponents(JPanel panel) {

        panel.setLayout(null);


        JLabel lab_port=new JLabel(String.valueOf("port: "+port));
        JLabel lab_path=new JLabel("path: "+path);

        text_get=new JTextField(path);
        text_log=new JTextArea();


        lab_port.setBounds(40,20,100,20);
        lab_path.setBounds(260,20,100,20);
        text_get.setBounds(30, 120, 80, 20);
        text_log.setBounds(30, 60, 300, 160);

       // panel.add(text_get);
        panel.add(text_log);
        panel.add(lab_port);
        panel.add(lab_path);



    }

    public void listen() {

        pool= Executors.newFixedThreadPool(10);


        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        try(ServerSocket serversocket = factory.createServerSocket(port);){


            System.out.println("Server start");
            System.out.println("Listening for clients");


            while (true) {

                Socket clientsocket = serversocket.accept();
                counter++;
                text_log.append("client"+counter+" connected\n");

                ConnectionThread thread=new ConnectionThread(clientsocket,counter,this);
                pool.execute(thread);


            }
        } catch (IOException e) {
            e.printStackTrace();
            pool.shutdown();
        }
    }

}
