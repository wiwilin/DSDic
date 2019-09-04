package server;

//author:Wei LIN
//number:885536
//id:wlin8
//

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/*Server listening act as a thread manager*/
public class ServerListening {
    private static int port;
    private String path;
    public static int counter;
    private ExecutorService pool = null;
    public ServerGUI serverGUI;
    public static int numClient;
    public static int numWords = 0;
    public DicMap dicMap;

    public ServerListening(ServerGUI serverGUI) {
        this.port = port;
        serverGUI = serverGUI;
        listen();


    }

    public void listen() {

        ThreadExcutor excutor = new ThreadExcutor(10);


        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        try (ServerSocket serversocket = factory.createServerSocket(port);) {


            serverGUI.text_log.append("Server start\n");
            serverGUI.text_log.append("Listening for clients\n");


            while (true) {

                Socket socket = serversocket.accept();
                counter++;
                numClient++;
                serverGUI.text_client.setText("client: " + numClient);
                serverGUI.text_log.append("client" + counter + "reply\n");

                ConnectionThread thread = new ConnectionThread(socket, counter, serverGUI);
                excutor.exec(thread);


            }
        } catch (IOException e) {
            e.printStackTrace();
            pool.shutdown();
        }
    }
}
