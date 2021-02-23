package com.clean.architecture.tuto.reposql.repositories;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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

}
