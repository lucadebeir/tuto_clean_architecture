package com.clean.architecture.tuto.repojpa.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "team")
public class TeamEntity {

    @Id
    @Column(name = "uuid", columnDefinition="BINARY(16)")
    private byte[] uuid;
    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "bepartof",
            joinColumns = @JoinColumn(name = "idTeam"),
            inverseJoinColumns = @JoinColumn(name = "idPerson")
    )
    private List<PersonEntity> personsList = new ArrayList<>();

    //Getters and setters ommitted for brevity

    public void addPerson(PersonEntity person) {
        personsList.add(person);
        person.getTeamsList().add(this);
    }

    public void removePerson(PersonEntity person) {
        personsList.remove(person);
        person.getTeamsList().remove(this);
    }

}
