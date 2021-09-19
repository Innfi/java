package playground;


public class App {
    public static void main(String[] args) throws Exception {
        SocketServer socketServer = new SocketServer();

        socketServer.initNetty();
    }
}
