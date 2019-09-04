package client;

public class InitClient {
    public static void main(String[] args) {

        try {

            ClientConnection client = new ClientConnection();


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

}