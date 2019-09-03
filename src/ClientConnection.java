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
    String message="";

    public ClientConnection() throws IOException {
        connect();
    }
    public void connect() throws IOException {
        try {
               clientGUI = new ClientGUI();
               clientGUI.frame.setVisible(true);
               clientGUI.lab_ip.setText("ip: "+ip);
               clientGUI.lab_port.setText("port: "+String.valueOf(port));



        }
        catch (Exception e){}

        try
        {
            Socket socket = new Socket(ip, port);
            // Output and Input Stream
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            boolean k=false;
            while(true) {
                clientGUI.con_info.append("1");
                while (clientGUI.send==true&&k==false&&sendData()) {
                    clientGUI.text_info.append("client sent");
                    System.out.println("clent send");
                    //sendData();
                    clientGUI.send=false;
                    k=true;
                }
                clientGUI.con_info.append(String.valueOf(k));
                //clientGUI.con_info.append("cUI"+String.valueOf(clientGUI.send));
               // clientGUI.con_info.append("2");

                while (clientGUI.send==false&&k==true&&(message = input.readUTF())!="") {
                    //clientGUI.text_info.append("client listening");
                    handleOutput(message);
                    clientGUI.text_info.append("received:"+message);
                    k=false;
                }

            }



        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public boolean sendData() throws IOException {

        String sendData =clientGUI.command+","+clientGUI.vocabulary+","+clientGUI.explanation;
        output.writeUTF(sendData);
        clientGUI.text_info.append("Data sent to Server--> " + sendData+"\n");
        output.flush();
        return true;

    }
    public void handleOutput(String meassage){
        switch (clientGUI.command){
        case "search":
            String[] str=message.split("&");
            for(int i=0;i<str.length;i++)
            {
                clientGUI.text_info.append("explanation"+Integer.toString(i)+": "+str[i]+"\n");
            }
            break;
        default:
            ;
        }


    }

}
