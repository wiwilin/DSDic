import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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


    public ConnectionThread(Socket socket, int counter, ServerGUI serverGUI,DicMap dicMap) {
        this.socket = socket;
        this.counter = counter;
        this.serverGUI = serverGUI;
        this.dicMap=dicMap;

    }

    @Override
    public void run() {

        try {
            input = new DataInputStream(socket.getInputStream());

            output = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String str=null;
                while ((str=input.readUTF())!= null) {
                    serverGUI.text_log.append(str+"\n");
                }

                executeCommand(str);
                output.writeUTF("reply by server:Hi Client"+counter+"!\n");
                output.flush();
                System.out.println(dicMap.searchWord("a"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public synchronized void executeCommand(String com) throws Exception {
        String[] str = com.split(",");
        String command = str[0];
        String word=str[1];
        String meaning=str[2];
        System.out.println("command:"+command+"word:"+word+"meaning"+meaning);
        switch (command) {
            case "search":
                writeArrayList(dicMap.searchWord(word));
                break;
            case "add":
                dicMap.addWord(word,meaning);
                break;
            case "delete":
                dicMap.deleteWord(word);
                break;
            case "kill":
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + str[0]);
        }
    }
    public void writeArrayList(ArrayList list) throws IOException {
        /*for(int i=0; i<list.size(); i++){
            String str= (String) list.get(i);
            output.writeUTF(str);
        }*/
        output.writeUTF("an explan");
    }


}