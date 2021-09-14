package playground;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;
import static org.junit.Assert.*;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.MongoClient;


public class MongoDBTest {
    public final String dbUrl = "mongodb://192.168.1.102";

    //@Test 
    public void insertAndFind() {
        MongoClient mongoClient = MongoClients.create(dbUrl);

        MongoDatabase db = mongoClient.getDatabase("testDB");
        MongoCollection<Document> userCollection = db.getCollection("user");

        Document inputDocument = new Document();
        inputDocument.put("username", "innfi");
        inputDocument.put("email", "innfi@test.com");

        InsertOneResult result = userCollection.insertOne(inputDocument);
        assertEquals(result.wasAcknowledged(), true);

        Bson findFilter = Filters.eq("username", "innfi");
        List<Document> findResult = 
            userCollection.find(findFilter).into(new ArrayList<Document>());
        assertEquals(findResult.get(0).get("username").toString(), "innfi");
        assertEquals(findResult.get(0).get("email").toString(), "innfi@test.com");

        //todo: teardown?
        userCollection.drop();
    }

    //@Test
    public void setIndex() {
        MongoClient mongoClient = MongoClients.create(dbUrl);

        MongoDatabase db = mongoClient.getDatabase("testDB");
        MongoCollection<Document> userCollection = db.getCollection("user");

        String key = "username";
        int order = 1;
        userCollection.createIndex(Filters.eq(key, order));

        dummyInsert(userCollection);

        String indexName = key + "_" + order;
        for (final Document doc: userCollection.listIndexes()) {
            System.out.println("name: " + doc.getString("name").toString());

            if(doc.getString("name").equals(indexName)) {
                //userCollection.drop();
                return;
            }
        }

        //userCollection.drop();
        fail();
    }

    protected void dummyInsert(MongoCollection<Document> userCollection) {
        Document inputDocument = new Document();
        inputDocument.put("username", "ennfi");
        inputDocument.put("email", "ennfi@test.com");

        userCollection.insertOne(inputDocument);
    }

    @Test
    public void readList() throws Exception {
        MongoClient mongoClient = MongoClients.create(dbUrl);

        MongoDatabase db = mongoClient.getDatabase("testDB");
        MongoCollection<Document> userCollection = db.getCollection("user");

        List<Document> customers = generateDummyList();

        Document inputDocument = new Document();
        inputDocument.put("username", "innfi");
        inputDocument.put("email", "innfi@test.com");
        inputDocument.put("customers", customers);

        userCollection.insertOne(inputDocument);

        Bson findFilter = Filters.eq("username", "innfi");
        Document findResult = userCollection.find(findFilter).first();

        Object customersObject = findResult.get("customers");
        System.out.println(customersObject);
    }

    protected List<Document> generateDummyList() {
        List<Document> customers = new ArrayList<Document>();

        Document doc1 = new Document();
        doc1.put("id", "ennfi");

        Document doc2 = new Document();
        doc2.put("id", "milli");

        Document doc3 = new Document();
        doc3.put("id", "elise");

        customers.add(doc1);
        customers.add(doc2);
        customers.add(doc3);

        return customers;
    }
}
