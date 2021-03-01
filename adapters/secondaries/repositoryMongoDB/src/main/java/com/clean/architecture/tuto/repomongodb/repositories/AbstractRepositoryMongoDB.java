package com.clean.architecture.tuto.repomongodb.repositories;

import com.clean.architecture.tuto.repomongodb.config.Config;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import lombok.Getter;
import lombok.Setter;
import java.net.UnknownHostException;

@Getter
@Setter
public abstract class AbstractRepositoryMongoDB {

    protected DB database;

    public AbstractRepositoryMongoDB() {
        try {
            this.database = Config.SingletonMongoDB.getDatabase("local");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
