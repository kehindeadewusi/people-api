package com.embl.peopleapi.services;

import static org.mockito.ArgumentMatchers.contains;

import java.util.Arrays;
import java.util.List;

import com.embl.peopleapi.entities.Person;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SerializerTests {

	@Test
	void contextLoads() throws JsonProcessingException {
		Person person1 = new Person("Joy", "Burgundy", 25, "RED");
        Person person2 = new Person("James", "Joyce", 29, "GREEN");
        Person person3 = new Person("Bruno", "Fernandez", 27, "RED");

        List<Person> persons = Arrays.asList(person1, person2, person3);
		String output = JsonUtil.PersonsToJsonString(persons);
		assertThat(output).contains("person");
	}

}
