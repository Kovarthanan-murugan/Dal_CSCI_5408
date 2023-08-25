package org.example.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

public class MongoDbSendData {
    /** This method connect to the mongoDb database and update the documents in it based on the records in the file
     * @param content Values of record tittle in first index and content in second index
     * @param name of the record fetched from the api
     */
public static void updateDataBase(String[] content,String name) {

    String uri = "mongodb+srv://kovarthananMurugan:kovarthan@assignment2.l9bo3hs.mongodb.net/?retryWrites=true&w=majority";
    try (MongoClient mongoClient = MongoClients.create(uri)) {
        MongoDatabase database = mongoClient.getDatabase("newsAPI");
        if (!database.listCollectionNames().into(new ArrayList<>()).contains(name)) {
            database.createCollection(name);
            System.out.println("Created collection");
        } else {
            System.out.println("Collection already exists");
        }
        MongoCollection<Document> collection = database.getCollection(name);
        Document document = new Document("tittle",content[0])
                .append("content", content[1]);

        collection.insertOne(document);
    }
}
}
