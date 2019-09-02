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
    public DicOperate dicOperate;


    public ConnectionThread(Socket clientsocket, int counter, ServerGUI serverGUI) {
        this.clientsocket = clientsocket;
        this.counter = counter;
        this.serverGUI = serverGUI;

    }

    @Override
    public void run() {

        try {
            input = new DataInputStream(clientsocket.getInputStream());

            output = new DataOutputStream(clientsocket.getOutputStream());

            while (true) {
                String str = input.readUTF();
                serverGUI.text_log.append(str);
                executeCommand(str);
                // output.writeUTF("reply by server:Hi Client"+counter+"!\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void executeCommand(String command) throws IOException {
        String[] str = command.split(",");
        switch (str[0]) {
            case "search":
                output.writeUTF(dicOperate.searchDic(str[1]));
                break;
            case "add":
                dicOperate.addDic(str[1], str[2]);
                break;
            case "delete":
                dicOperate.deleteDic(str[1]);
                break;
            case "kill":
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + str[0]);
        }
    }


}