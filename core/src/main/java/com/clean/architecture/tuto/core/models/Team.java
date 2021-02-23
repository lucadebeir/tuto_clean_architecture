package com.clean.architecture.tuto.core.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team {

    private String id;
    private String name;
    private List<Person> list;

}
