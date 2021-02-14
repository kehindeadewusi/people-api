package com.embl.peopleapi.models;

import java.util.List;

import com.embl.peopleapi.entities.Person;

import lombok.Getter;
import lombok.Setter;

/**
 * A model providing a simple cheap way to quickly serialize a person list as a
 * json object in the test required format.
 */
@Getter
@Setter
public class SimplePersonList {
    private List<Person> person;
}
