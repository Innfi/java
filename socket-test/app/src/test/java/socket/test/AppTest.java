package socket.test;

import org.junit.Test;
import static org.junit.Assert.*;


public class AppTest {
    @Test 
    public void appHasAGreeting() throws Exception {
        GreetClient client = new GreetClient();

        client.startConnection("127.0.0.1", 1330);

        String response = client.sendMessage("hello");
        assertEquals(response, "hello");
    }
}
