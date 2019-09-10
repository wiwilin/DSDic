package client;

//author:Wei LIN
//number:885536
//id:wlin8
//

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection {
    private static String ip ;
    private static int port;
    private ClientGUI clientGUI;
    public DataInputStream input;
    public DataOutputStream output;
    String message = "";

    public ClientConnection(String ip,int port) throws IOException {
        this.ip= ip;
        this.port=port;
        connect();
    }

    public void connect() throws IOException {
        try {
            clientGUI = new ClientGUI();
            clientGUI.frame.setVisible(true);
            clientGUI.lab_ip.setText("ip: " + ip);
            clientGUI.lab_port.setText("port: " + String.valueOf(port));


        } catch (Exception e) {
        }

        try {
            Socket socket = new Socket(ip, port);
            // Output and Input Stream
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            boolean k = false;
            boolean n=false;
            while (true) {
                if(clientGUI.close==true)
                    n=true;
                //clientGUI.con_info.append("1");
                while (clientGUI.send == true && k == false && sendData()) {
                    clientGUI.con_info.setText("Request sent");
                   // System.out.println("clent send");
                    //sendData();
                    clientGUI.send = false;
                    k = true;
                }

                 clientGUI.text_de.append("jaja");
                 //clientGUI.con_info.setText("connecting");
                 //clientGUI.con_info.setText("");
                //clientGUI.con_info.append("cUI"+String.valueOf(clientGUI.send));


                while (clientGUI.send == false && k == true && (message = input.readUTF()) != "") {
                    //clientGUI.text_info.append("client listening");
                    handleOutput(message);
                    // clientGUI.text_info.append("received:"+message);
                    k = false;
            }

                if(n==true)
                    break;

            }
            socket.close();
            clientGUI.exit();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean sendData() throws IOException {

        String sendData = clientGUI.command + "," + clientGUI.vocabulary + "," + clientGUI.explanation;
        output.writeUTF(sendData);
        clientGUI.con_info.setText("sent to Server-->" + sendData + "\n");
        output.flush();
        return true;

    }

    public void handleOutput(String meassage) {
        switch (clientGUI.command) {
            case "search":
                String[] str = message.split("&");
                clientGUI.text_info.setText("");
                for (int i = 1; i < str.length; i++) {
                    clientGUI.text_info.append("explanation" + Integer.toString(i) + ": " + str[i] + "\n");
                }
                break;
            case "kill":
                clientGUI.exit();
            default:
                ;
        }


    }

}
