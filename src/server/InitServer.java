package server;


public class InitServer {

    public static void main(String[] args) {

        try {
            int port;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            } else
                port = 1050;
            String path = "Dic.json";
            if (args.length == 2) {
                System.out.println("port changed to " + port + ", path changed to " + path);
                port = Integer.parseInt(args[0]);
                path = args[1];
            }

            ServerGUI serverGUI = new ServerGUI(port, path);
            serverGUI.frame.setVisible(true);


            serverGUI.listen();


        } catch (Exception e) {

            e.printStackTrace();

        }
    }

}

