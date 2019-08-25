import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionThread implements Runnable {
    private Socket clientsocket = null;
    private DataInputStream input;
    private DataOutputStream output;
    private int counter;
    public ServerGUI serverGUI;


    public ConnectionThread(Socket clientsocket,int counter,ServerGUI serverGUI) {
        this.clientsocket = clientsocket;
        this.counter=counter;
        this.serverGUI=serverGUI;
    }

    @Override
    public void run() {

        try{
            input = new DataInputStream(clientsocket.getInputStream());

            output = new DataOutputStream(clientsocket.getOutputStream());

            while(true) {
                serverGUI.text_log.append(input.readUTF());
                output.writeUTF("reply by server:Hi Client"+counter+"!\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}