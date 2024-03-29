package com.clean.architecture.tuto.repomongodb.repositories;

import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import com.clean.architecture.tuto.repomongodb.config.Config;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryPersonMongoDB extends AbstractRepositoryMongoDB implements RepositoryPerson {

    protected DBCollection collection = database.getCollection("person");

    @Override
    public Person create(Person person) {
        DBObject doc = new BasicDBObject("lastname", person.getLastName())
                .append("firstname", person.getFirstName())
                .append("age", person.getAge());
        collection.save(doc);
        person.setId(String.valueOf(doc.get( "_id" )));
        return person;
    }

    @Override
    public List<Person> getAll() {
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
    public Optional<Person> findById(String id) throws TechnicalException {
        BasicDBObject query = new BasicDBObject();

        //check is objectid is valid in mongodb
        if(!ObjectId.isValid(id)) {
            //bonne méthode ? à voir avec Yassine
            throw new TechnicalException("L'id n'est pas valide.");
        }

        query.put("_id", new ObjectId(id));

        DBObject value = collection.findOne(query);

        return Optional.of(new Person(String.valueOf(value.get("_id")),
                String.valueOf(value.get("firstname")),
                String.valueOf(value.get("lastname")),
                Integer.parseInt(String.valueOf(value.get("age")))));
    }

    @Override
    public Person update(Person personToUpdate) throws UnknownHostException, SQLException {
        return null;
    }

    @Override
    public void deleteById(String id) throws SQLException {

    }

    @Override
    public boolean existsByIdPerson(String s) {
        return false;
    }


}
