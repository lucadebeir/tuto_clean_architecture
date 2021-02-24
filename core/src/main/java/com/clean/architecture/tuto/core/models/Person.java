package com.clean.architecture.tuto.core.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {

    private String id;
    private String firstName;
    private String lastName;
    private Integer age;

}
