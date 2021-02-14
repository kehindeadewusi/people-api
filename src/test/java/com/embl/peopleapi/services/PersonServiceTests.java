package com.embl.peopleapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.embl.peopleapi.entities.Person;
import com.embl.peopleapi.models.SimplePersonList;
import com.embl.peopleapi.repos.PersonRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
public class PersonServiceTests {
    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepo personRepo;
    Person person1 = new Person("Jamie", "Lannister", 26, "GOLD");
    Person person2 = new Person("Ron", "Stack", 10, "SILVER");
    Person person3 = new Person("Jon", "Snow", 19, "BLACK");
    List<Person> people = Arrays.asList(person1, person2, person3);
    
    @Test
    public void canGetPersons(){
        Mockito.when(personRepo.findAll()).thenReturn(people);
        SimplePersonList spl = personService.getPersons2();
        assertEquals(spl.getPerson().size(), 3);
    }

    @Test
    public void canGetPerson(){
        person1.setId(1);
        Mockito.when(personRepo.findById(1)).thenReturn(Optional.of(person1));
        
        Optional<Person> result = personService.getPerson(1);
        assertEquals(result.isEmpty(), false);
        assertEquals(result.get().getAge(), person1.getAge());
    }

    @Test
    public void canSavePerson(){
        Mockito.when(personRepo.save(person2)).thenReturn(person2);
        Person p = personService.addPerson(person2);
        assertEquals(p.getFirstName(), person2.getFirstName());
    }
}
