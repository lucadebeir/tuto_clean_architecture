package com.clean.architecture.tuto.reposql.repositories;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryPersonMongoDB implements RepositoryPerson {

    @Override
    public Person create(Person person) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB("local");
        DBCollection collection = database.getCollection("person");
        DBObject doc = new BasicDBObject("lastname", person.getLastName())
                .append("firstname", person.getFirstName())
                .append("age", person.getAge());
        collection.save(doc);
        person.setId(String.valueOf(doc.get( "_id" )));
        mongoClient.close();
        return person;
    }

    @Override
    public List<Person> getAll() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB("local");
        DBCollection collection = database.getCollection("person");
        List<Person> list = new ArrayList<>();
        Cursor cursor = collection.find();
        while(cursor.hasNext()) {
            DBObject value = cursor.next();
            list.add(new Person(String.valueOf(value.get("_id")),
                    String.valueOf(value.get("firstname")),
                    String.valueOf(value.get("lastname")),
                    Integer.parseInt(String.valueOf(value.get("age")))));
        }
        return list;
    }

    @Override
    public Person display(Person personToDisplay) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB("local");
        DBCollection collection = database.getCollection("person");
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(personToDisplay.getId()));

        DBObject value = collection.findOne(query);
        return new Person(String.valueOf(value.get("_id")),
                String.valueOf(value.get("firstname")),
                String.valueOf(value.get("lastname")),
                Integer.parseInt(String.valueOf(value.get("age"))));
    }


}
