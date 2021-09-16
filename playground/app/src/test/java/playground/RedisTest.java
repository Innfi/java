package playground;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void hashes() throws Exception {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379, 1000);
        Jedis client = pool.getResource();

        Map<String, String> hashValue = new HashMap<String, String>();
        hashValue.put("name", "innfi");
        hashValue.put("email", "innfi@test.com");
        hashValue.put("message", "hello");
        
        client.hset("userProfile", hashValue);


        String name = client.hget("userProfile", "name");
        assertEquals(name, "innfi");
    }

    @Test 
    public void pubsub() throws Exception {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379, 1000);
        Jedis client = pool.getResource();

        JedisPubSub pubsubClient = new JedisPubSub() {
            @Override 
            public void onMessage(String channel, String message) {
                System.out.println("message: " + message);
            }

            @Override 
            public void onSubscribe(String channel, int subscribedChannels) {

            }

            @Override 
            public void onUnsubscribe(String channel, int subscribedChannels) {

            }
        };

        String channel = "event";
        client.subscribe(pubsubClient, channel);

        client.publish(channel, "hello");
    }
}
