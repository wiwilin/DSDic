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


    public ConnectionThread(Socket socket, int counter, ServerGUI serverGUI, DicMap dicMap) {
        this.socket = socket;
        this.counter = counter;
        this.serverGUI = serverGUI;
        this.dicMap = dicMap;

    }

    @Override
    public void run() {
        boolean k = true;
        boolean sent = true;
        String str = "";
        try {
            input = new DataInputStream(socket.getInputStream());

            output = new DataOutputStream(socket.getOutputStream());


            while (true) {
                serverGUI.text_ter.append("88");
                while (k == true && (str = input.readUTF()) != "") {
                    serverGUI.text_log.append(str + "\n");
                    k = false;
                    serverGUI.text_ter.append("k=false");
                }
                while (k == false) {
                    serverGUI.text_ter.append("out");
                    //executeCommand(str);
                    output.writeUTF("Hi" + counter+ str + "!\n");
                    output.flush();
                    k=true;
                    serverGUI.text_ter.append("down");
                    // System.out.println(dicMap.searchWord("a"));}

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public synchronized void executeCommand(String com){
        /*
            String[] star = com.split(",");
            String command = star[0];
            String word = star[1];
            String meaning = star[2];
            System.out.println("command:" + command + "word:" + word + "meaning" + meaning);
            switch (command) {
                case "search":
                    writeArrayList(dicMap.searchWord(word));
                    break;
                case "add":
                    dicMap.addWord(word, meaning);
                    break;
                case "delete":
                    dicMap.deleteWord(word);
                    break;
                case "kill":
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + star[0]);
            }
         */
        }
        public void writeArrayList (ArrayList list)  {
        /*for(int i=0; i<list.size(); i++){
            String str= (String) list.get(i);
            output.writeUTF(str);
        }*/
        }

}

