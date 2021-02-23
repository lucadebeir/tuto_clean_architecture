package com.clean.architecture.tuto.reposql.repositories;

import com.mongodb.*;

import java.net.UnknownHostException;

public class Test {

    public static void main(String[] args) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        mongoClient.getDatabaseNames().forEach(System.out::println);
        DB database = mongoClient.getDB("local");
        DBCollection collectionExample = database.getCollection("example");
        DBObject doc = new BasicDBObject("nom", "Toto").append("prenom", "Yaya");
        collectionExample.save(doc);
        mongoClient.close();
    }
}
