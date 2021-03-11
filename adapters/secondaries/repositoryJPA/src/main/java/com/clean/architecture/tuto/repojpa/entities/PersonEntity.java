package com.clean.architecture.tuto.repojpa.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "person")
public class PersonEntity {

    @Id
    @Column(name = "uuid", columnDefinition="BINARY(16)")
    private byte[] uuid;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "age")
    private Integer age;

    //@ManyToMany(mappedBy = "personsList")
    @ManyToMany
    @JoinTable( name = "bepartof",
            joinColumns = @JoinColumn( name = "idPerson" ),
            inverseJoinColumns = @JoinColumn( name = "idTeam" ) )
    private List<TeamEntity> teamsList = new ArrayList<>();

}
