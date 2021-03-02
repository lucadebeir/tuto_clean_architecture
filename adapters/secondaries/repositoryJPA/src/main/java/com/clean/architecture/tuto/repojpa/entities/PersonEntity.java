package com.clean.architecture.tuto.repojpa.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "person")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name = "lastname")
    private String lastname;
    private String firstname;
    private Integer age;

    @ManyToMany(mappedBy = "personsList")
    private List<TeamEntity> teamsList;

}
