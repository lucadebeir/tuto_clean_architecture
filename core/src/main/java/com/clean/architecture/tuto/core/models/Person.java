package com.clean.architecture.tuto.core.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Person {

    private byte[] uuid;
    private String lastName;
    private String firstName;
    private Integer age;

}
