package com.clean.architecture.tuto.repomongodb.repositories;

import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import com.clean.architecture.tuto.core.utils.Utils;
import com.clean.architecture.tuto.repomongodb.config.Config;
import com.mongodb.*;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class RepositoryTeamMongoDB extends AbstractRepositoryMongoDB implements RepositoryTeam {

    protected DBCollection collection = database.getCollection("team");

    @Override
    public Team create(Team team) {
        List<DBObject> list = new ArrayList<DBObject>();
        for(Person person : team.getList()) {
            list.add(new BasicDBObject("_id", person.getUuid())
                    .append("lastname", person.getLastName())
                    .append("firstname", person.getFirstName())
                    .append("age", person.getAge()));
        }
        DBObject doc = new BasicDBObject("_id", team.getUuid())
                .append("name", team.getName())
                .append("list", list);
        collection.save(doc);
        return team;
    }

    @Override
    public List<Team> getAll() {
        List<Team> allTeam = new ArrayList<>();
        DBCursor cursor = collection.find();
        try {
            while(cursor.hasNext()) {
                DBObject obj = cursor.next();
                Team team = Team.builder().uuid(Utils.getByteArrayFromGuid(String.valueOf(obj.get( "_id" ))))
                        .name(String.valueOf(obj.get("name")))
                        .build();
                List<Person> people = new ArrayList<>();
                for(DBObject person: (List<DBObject>) obj.get("list")) {
                    people.add(Person.builder().uuid(Utils.getByteArrayFromGuid(String.valueOf(person.get("_id"))))
                            .firstName(String.valueOf(person.get("firstname")))
                            .lastName(String.valueOf(person.get("lastname")))
                            .age(Integer.parseInt(String.valueOf(person.get("age"))))
                            .build());
                }
                team.setList(people);
                allTeam.add(team);
            }
        } finally {
            cursor.close();
        }
        return allTeam;
    }

    @Override
    public Optional<Team> findByUuid(byte[] uuid) throws TechnicalException {
        BasicDBObject query = new BasicDBObject();

        //check is objectid is valid in mongodb
        try{
            UUID.fromString(Utils.getGuidFromByteArray(uuid));
            //do something
        } catch (IllegalArgumentException exception){
            //handle the case where string is not valid UUID
            throw new TechnicalException("L'uuid n'est pas valide.");
        }

        query.put("_id", uuid);

        DBObject value = collection.findOne(query);

        Team team = Team.builder().uuid(uuid)
                .name(String.valueOf(value.get("name")))
                .build();
        List<Person> people = new ArrayList<>();
        for(DBObject person: (List<DBObject>) value.get("list")) {
            people.add(Person.builder().uuid(Utils.getByteArrayFromGuid(String.valueOf(person.get("_id"))))
                    .firstName(String.valueOf(person.get("firstname")))
                    .lastName(String.valueOf(person.get("lastname")))
                    .age(Integer.parseInt(String.valueOf(person.get("age"))))
                    .build());
        }
        team.setList(people);

        return Optional.of(team);
    }

    @Override
    public boolean existsByName(String name) {
        Cursor cursor = collection.find();
        boolean check = false;
        try {
            while(cursor.hasNext()) {
                DBObject obj = cursor.next();
                if(String.valueOf(obj.get("name")).equals(name)) {
                    check = true;
                }
            }
        } finally {
            cursor.close();
        }
        return check;
    }

    @Override
    public void deleteByUuid(byte[] uuid) {

    }

    @Override
    public Team update(Team team) throws UnknownHostException, SQLException {
        return null;
    }

}
