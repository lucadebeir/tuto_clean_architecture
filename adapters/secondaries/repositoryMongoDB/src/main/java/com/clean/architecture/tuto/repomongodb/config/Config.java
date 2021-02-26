package com.clean.architecture.tuto.repomongodb.config;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import lombok.*;

import java.net.UnknownHostException;
import java.util.Objects;

@Getter
@Setter
@Builder
public class Config {

    public static class SingletonMongoDB {
        /** Instance unique non préinitialisée */
        private static MongoClient instance;
        private static DB database;

        public static MongoClient getInstance() throws UnknownHostException {
            if(Objects.isNull(instance)) {
                instance = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            }
            return instance;
        }

        public static DB getDatabase(String dbName) throws UnknownHostException {
            if(Objects.isNull(database)) {
                database = getInstance().getDB(dbName);
            }
            return database;
        }
    }
}
