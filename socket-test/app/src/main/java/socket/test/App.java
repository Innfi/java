/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package socket.test;

import java.io.*;

public class App {
    public static void main(String[] args) throws Exception {
        GreetClient client = new GreetClient();
        try {
            client.startConnection("127.0.0.1", 1330);
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
        
            String readBuffer;
            while((readBuffer = br.readLine()) != null) {
                String response = client.sendMessage(readBuffer);
                System.out.println("response: " + response);
            }

        } catch (Exception e) {
            System.out.println("exception: " + e);
        }
    }
}