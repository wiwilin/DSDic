
import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*Server listening act as a thread manager*/
public class ServerListening {
    private static int port;
    private String path;
    private static int counter=0;
    private ExecutorService pool=null;
    public ServerGUI serverGUI;

    public ServerListening(ServerGUI serverGUI){
       this.port=port;
       serverGUI=serverGUI;
       listen();


    }

    public void listen() {

        pool= Executors.newFixedThreadPool(10);


        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        try(ServerSocket serversocket = factory.createServerSocket(port);){


            serverGUI.text_log.append("Server start\n");
            serverGUI.text_log.append("Listening for clients");


            while (true) {

                Socket clientsocket = serversocket.accept();
                counter++;
                serverGUI.text_log.append("client"+counter+"reply\n");

                ConnectionThread thread=new ConnectionThread(clientsocket,counter,serverGUI);
                pool.execute(thread);


            }
        } catch (IOException e) {
            e.printStackTrace();
            pool.shutdown();
        }
    }
}
