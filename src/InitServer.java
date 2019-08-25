import java.awt.*;

public class InitServer {

    public static void main(String[] args) {

            try {
                int port=1050;
                String path="a.txt";
                if(args.length==2) {
                    System.out.println("port changed to "+port+", path changed to "+path);
                    port = Integer.parseInt(args[0]);
                    path = args[1];
                }

                ServerGUI serverGUI = new ServerGUI(port,path);
                serverGUI.frame.setVisible(true);
               // ServerListening serverListening=new ServerListening(serverGUI);
                serverGUI.listen();



            } catch (Exception e) {

                e.printStackTrace();

            }
        }

}

