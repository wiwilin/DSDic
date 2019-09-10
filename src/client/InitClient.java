package client;

//author:Wei LIN
//number:885536
//id:wlin8
//

public class InitClient {
    public static void main(String[] args) {
        String ip;
        int port;

        try {
            if (args.length > 1) {
                port = Integer.parseInt(args[1]);
            } else
                port = 1050;

            if(args.length>0)
                ip = args[0];
            else
                ip="localhost";
            ClientConnection client = new ClientConnection(ip,port);


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

}