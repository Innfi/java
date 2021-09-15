package playground;

import org.junit.Test;
import static org.junit.Assert.*;

import redis.clients.jedis.*;


public class RedisTest {
    @Test 
    public void redisConnect() throws Exception {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379, 1000);

        assertEquals(pool != null, true);
        Jedis client = pool.getResource();

        assertEquals(client.isConnected(), true);
    }

    @Test
    public void simpleIO() throws Exception {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379, 1000);
        Jedis client = pool.getResource();

        String testValue = "innfi";
        client.set("id", testValue);

        String result = client.get("id").toString();
        assertEquals(result, testValue);
    }
}
