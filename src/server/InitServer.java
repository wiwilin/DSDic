package server;

//author:Wei LIN
//number:885536
//id:wlin8
//

public class InitServer {

    public static void main(String[] args) {

        try {
            int port;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            } else
                port = 1050;
            String path;
            if(args.length>1)
                path = args[1];
            else
                path = "Dic.json";
            if (args.length == 2) {
                System.out.println("port changed to " + port + ", path changed to " + path);
                port = Integer.parseInt(args[0]);
                path = args[1];
            }

            ServerGUI serverGUI = new ServerGUI(port, path);
            serverGUI.frame.setVisible(true);


            serverGUI.listen();


        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

}

