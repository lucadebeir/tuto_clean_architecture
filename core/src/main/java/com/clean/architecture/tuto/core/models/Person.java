package com.clean.architecture.tuto.core.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String id;
    private String firstName;
    private String lastName;
    private Integer age;

}
