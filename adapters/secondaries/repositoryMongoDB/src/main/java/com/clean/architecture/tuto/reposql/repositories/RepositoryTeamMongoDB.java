package com.clean.architecture.tuto.reposql.repositories;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryTeamMongoDB implements RepositoryTeam {

    @Override
    public Team create(Team team) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB("local");
        DBCollection collection = database.getCollection("team");
        List<DBObject> list = new ArrayList<DBObject>();
        for(Person person : team.getList()) {
            list.add(new BasicDBObject("lastname", person.getLastName())
                    .append("firstname", person.getFirstName())
                    .append("age", person.getAge()));
        }
        DBObject doc = new BasicDBObject("name", team.getName())
                .append("list", list);
        collection.save(doc);
        team.setId(String.valueOf(doc.get( "_id" )));
        mongoClient.close();
        return team;
    }

    @Override
    public List<Team> getAll() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB("local");
        DBCollection collection = database.getCollection("team");
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
                    people.add(Person.builder().firstName(String.valueOf(person.get("firstname")))
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
    public Optional<Team> findById(String id) throws UnknownHostException {

        return Optional.empty();
    }

    @Override
    public boolean existsByName(String name) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB("local");
        DBCollection collection = database.getCollection("team");
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
