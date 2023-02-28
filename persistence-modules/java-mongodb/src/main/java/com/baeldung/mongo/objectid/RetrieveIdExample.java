package com.baeldung.mongo.objectid;

import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class RetrieveIdExample {

    public static void main(String[] args) {
        
        try ( MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017") ) {
            
            MongoDatabase database = mongoClient.getDatabase("myMongoDb");        
            MongoCollection<Document> collection = database.getCollection("example");
 
//          Create document with user-generated ID
            ObjectId generatedId = new ObjectId();
            
            System.out.println(generatedId.toString());                    
            
            Document document = new Document();
            document.put("_id", generatedId);
            document.put("name", "Shubham");
            document.put("company", "Baeldung");
            collection.insertOne(document);
            
//          Check that the ID of the document is still the one we set 
            System.out.println(document.getObjectId("_id").equals(generatedId));
            
            
//          Create a second document by injecting the ID in the constructor
            
            ObjectId generatedId2 = ObjectId.get();
            
            Document document2 = new Document("_id", generatedId2);
            document2.put("name", "Shubham");
            document2.put("company", "Baeldung");
            collection.insertOne(document2);
                        
            Date creationDate = generatedId.getDate();
            System.out.println(creationDate);
            
            int timestamp = generatedId.getTimestamp();            
            
        } catch (Exception e) {
            
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
