import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientConnection {
    private static String ip = "localhost";
    private static int port = 1050;
    private  ClientGUI clientGUI;
    public  DataInputStream input;
    public DataOutputStream output;

    public ClientConnection(){
        connect();
    }
    public void connect(){
        try {
               clientGUI = new ClientGUI();
               clientGUI.frame.setVisible(true);
               clientGUI.lab_ip.setText(ip);
               clientGUI.lab_port.setText(String.valueOf(port));


        }
        catch (Exception e){}

        try(Socket socket = new Socket(ip, port);)
        {
            // Output and Input Stream
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            while(true) {
                while (clientGUI.send==true) {
                    sendData();
                    clientGUI.send=false;
                }


                while (true && input.available() > 0) {
                    String message = input.readUTF();
                    clientGUI.text_info.setText(message);
                }
            }

        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void sendData() throws IOException {

        String sendData =clientGUI.command+","+clientGUI.vocabulary+","+clientGUI.explanation;
        output.writeUTF(sendData);
        clientGUI.text_info.setText("Data sent to Server--> " + sendData+"\n");
        //output.flush();

    }
}
