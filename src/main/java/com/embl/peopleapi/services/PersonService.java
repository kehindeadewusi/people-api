package com.embl.peopleapi.services;

import java.util.Optional;

import com.embl.peopleapi.entities.Person;
import com.embl.peopleapi.models.SimplePersonList;

import org.springframework.stereotype.Service;

@Service
public interface PersonService {
    String getPersons();

	Person addPerson(Person person);

	Optional<Person> updatePerson(int id, Person person);

	Optional<Person> getPerson(int id);

	void deletePerson(int id);

	SimplePersonList getPersons2();
}
