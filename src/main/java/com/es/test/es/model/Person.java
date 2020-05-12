package com.es.test.es.model;

import lombok.experimental.Accessors;

import java.util.Date;


@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@Accessors(chain=true)
public class Person {
    private String name;
    private String address;
    private Integer age;
    private Date date;
}
