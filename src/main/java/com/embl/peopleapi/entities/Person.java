package com.embl.peopleapi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonProperty("first_name")
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @JsonProperty("last_name")
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @Range(min = 0, max = 150, message = "The age '${validatedValue}' must be between {min} and {max} " )
    private int age;
    @JsonProperty("favourite_colour")
    @NotBlank(message = "Favourite colour is mandatory")
    private String favouriteColour;

    public Person(){}

    public Person(String firstName, String lastName, int age, String favouriteColour){
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.favouriteColour = favouriteColour;
    }
}
