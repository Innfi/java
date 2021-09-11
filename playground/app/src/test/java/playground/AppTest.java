package playground;

import org.bson.Document;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;


public class AppTest {
    @Test 
    public void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.name());
    }

    @Test 
    public void mongodbConnect() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost");

        MongoDatabase db = mongoClient.getDatabase("testDB");
        MongoCollection<Document> userCollection = db.getCollection("user");

        List<Document> findResult = 
            userCollection.find().into(new ArrayList<Document>());

        assertEquals(findResult != null, true);
    }
}
