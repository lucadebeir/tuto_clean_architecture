package com.clean.architecture.tuto.repomongodb.repositories;

import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import com.clean.architecture.tuto.core.utils.Utils;
import com.clean.architecture.tuto.repomongodb.config.Config;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryPersonMongoDB extends AbstractRepositoryMongoDB implements RepositoryPerson {

    protected DBCollection collection = database.getCollection("person");

    @Override
    public Person create(Person person) {
        DBObject doc = new BasicDBObject("_id", person.getUuid())
                .append("lastname", person.getLastName())
                .append("firstname", person.getFirstName())
                .append("age", person.getAge());
        collection.save(doc);
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
    public Optional<Person> findByUuid(String uuid) throws TechnicalException {
        BasicDBObject query = new BasicDBObject();

        //check is objectid is valid in mongodb
        try{
            UUID.fromString(uuid);
            //do something
        } catch (IllegalArgumentException exception){
            //handle the case where string is not valid UUID
            throw new TechnicalException("L'uuid n'est pas valide.");
        }

        query.put("_id", uuid);

        DBObject value = collection.findOne(query);

        return Optional.of(Person.builder().uuid(uuid)
                .firstName(String.valueOf(value.get("firstname")))
                .lastName(String.valueOf(value.get("lastname")))
                .age(Integer.parseInt(String.valueOf(value.get("age")))).build());
    }

    @Override
    public Person update(Person personToUpdate) throws UnknownHostException, SQLException {
        return null;
    }

    @Override
    public void deleteByUuid(String uuid) throws SQLException {

    }

    @Override
    public boolean existsByUuidPerson(String s) {
        return false;
    }


}
