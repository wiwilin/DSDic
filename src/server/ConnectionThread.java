package server;

//author:Wei LIN
//number:885536
//id:wlin8
//

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionThread implements Runnable {
    private Socket socket = null;
    private DataInputStream input;
    private DataOutputStream output;
    private int counter;
    public ServerGUI serverGUI;
    public DicMap dicMap;


    public ConnectionThread(Socket socket, int counter, ServerGUI serverGUI) {
        this.socket = socket;
        this.counter = counter;
        this.serverGUI = serverGUI;
        this.dicMap = serverGUI.dicMap;

    }

    @Override
    public void run() {
        boolean k = true;
        boolean sent = true;
        String str = "";
        try {
            input = new DataInputStream(socket.getInputStream());

            output = new DataOutputStream(socket.getOutputStream());


            while (socket.isConnected()==true) {

                while (k == true && (str = input.readUTF()) != "") {

                    //serverGUI.text_log.append(str + "\n");

                    k = false;
                    //serverGUI.text_ter.append("k=false");
                }
                while (k == false) {
                    //serverGUI.text_ter.append("out");

                    String info = executeCommand(str);
                    output.writeUTF(info);
                    output.flush();
                    k = true;
                    //serverGUI.text_ter.append("down");
                    // System.out.println(dicMap.searchWord("a"));}
                }

            }

                serverGUI.text_log.append("Connection"+ counter +"killed");
                serverGUI.numClient--;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized String executeCommand(String com) {

        String[] star = com.split(",");
        String command = star[0];
        String word = star[1];
        String meaning;
        try {
            meaning = star[2];
        } catch (Exception e) {
            meaning = "";
        }
        //System.out.println("command:" + command + "word:" + word + "meaning" + meaning);
        String msg = command + " " + meaning + " to " + word;
        serverGUI.text_ter.append("Request from Client " + counter + " : " + msg + "\n");
        //int size=dicMap.values().size();
        //serverGUI.text_word.setText("words: "+String.valueOf(size+2));
        try {
            switch (command) {
                case "search":
                    String meanings = writeArrayList(dicMap.searchWord(word));
                    serverGUI.numWords=dicMap.dicMap.size();
                    serverGUI.text_word.setText("words count: " + serverGUI.numWords);
                    return meanings;
                case "add":
                    dicMap.addMeaning(word, meaning);
                    dicMap.printfile();
                    serverGUI.numWords=dicMap.dicMap.size();
                    serverGUI.text_word.setText("words count: " + serverGUI.numWords);
                    return "added";
                case "delete":
                    dicMap.deleteWord(word);
                    serverGUI.numWords=dicMap.dicMap.size();
                    serverGUI.text_word.setText("words count: " + serverGUI.numWords);
                    return "deleted";
                case "kill":
                    serverGUI.text_log.append("Connection"+ counter +"killed");
                    return "kill";
                default:
                    throw new IllegalStateException("Unexpected value: " + star[0]);
            }
        } catch (Exception e) {
            return "exception"+"*"+e.getMessage();
        }

    }

    public boolean isConnected() {
        try {
            socket.sendUrgentData(0xFF);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public String writeArrayList(ArrayList list) {

        String str = "";
        serverGUI.text_ter.append("Finding explanation\n");
        for (int i = 0; i < list.size(); i++) {

            {
                str += "&";
                str += list.get(i);

            }

        }
        return str;

    }
}

