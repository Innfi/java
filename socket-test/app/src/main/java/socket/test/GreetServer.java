package socket.test;

import java.net.*;
import java.io.*;


public class GreetServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public GreetServer() {
        System.out.println("GreetServer] constructor");
    }

    public void start(int port) throws Exception {
        System.out.println("before start started");
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        System.out.println("server started");

        String inputBuffer;
        while( (inputBuffer = in.readLine()) != null) {
            System.out.println("greeting: " + inputBuffer);
            out.println(inputBuffer);
        }  
    }

    public void stop() throws Exception {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
