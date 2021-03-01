package com.clean.architecture.tuto.repomongodb.repositories;

import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import com.clean.architecture.tuto.repomongodb.config.Config;
import com.mongodb.*;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class RepositoryTeamMongoDB extends AbstractRepositoryMongoDB implements RepositoryTeam {

    protected DBCollection collection = database.getCollection("team");

    @Override
    public Team create(Team team) {
        List<DBObject> list = new ArrayList<DBObject>();
        for(Person person : team.getList()) {
            list.add(new BasicDBObject("_id", person.getId())
                    .append("lastname", person.getLastName())
                    .append("firstname", person.getFirstName())
                    .append("age", person.getAge()));
        }
        DBObject doc = new BasicDBObject("name", team.getName())
                .append("list", list);
        collection.save(doc);
        team.setId(String.valueOf(doc.get( "_id" )));
        return team;
    }

    @Override
    public List<Team> getAll() {
        List<Team> allTeam = new ArrayList<>();
        DBCursor cursor = collection.find();
        try {
            while(cursor.hasNext()) {
                DBObject obj = cursor.next();
                Team team = Team.builder().id(String.valueOf(obj.get( "_id" )))
                        .name(String.valueOf(obj.get("name")))
                        .build();
                List<Person> people = new ArrayList<>();
                for(DBObject person: (List<DBObject>) obj.get("list")) {
                    people.add(Person.builder().id(String.valueOf(person.get("_id")))
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
    public Optional<Team> findById(String id) throws TechnicalException {
        BasicDBObject query = new BasicDBObject();

        //check is objectid is valid in mongodb
        if(!ObjectId.isValid(id)) {
            //bonne méthode ? à voir avec Yassine
            throw new TechnicalException("L'id n'est pas valide.");
        }

        query.put("_id", new ObjectId(id));

        DBObject value = collection.findOne(query);

        Team team = Team.builder().id(String.valueOf(value.get( "_id" )))
                .name(String.valueOf(value.get("name")))
                .build();
        List<Person> people = new ArrayList<>();
        for(DBObject person: (List<DBObject>) value.get("list")) {
            people.add(Person.builder().id(String.valueOf(person.get("_id")))
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

}
