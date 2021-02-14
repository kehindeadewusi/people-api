package com.embl.peopleapi.controllers;

import java.util.Optional;

import javax.validation.Valid;

import com.embl.peopleapi.entities.Person;
import com.embl.peopleapi.models.SimplePersonList;
import com.embl.peopleapi.services.PersonService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController {
    
    private PersonService personService;

    public PersonController(PersonService personService){
        this.personService = personService;
    }
    
    @GetMapping("/v0")
    public String getPeople0() throws Exception {
        return personService.getPersons();
    }

    @GetMapping()
    public SimplePersonList getPeople() throws Exception {
        return personService.getPersons2();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") int id) throws Exception {
        return personService.getPerson(id)
            .map(i->ResponseEntity.ok(i))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Person addPerson(@Valid @RequestBody Person person) throws Exception {
        return personService.addPerson(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") int id, @Valid @RequestBody Person person) throws Exception {
        //return personService.updatePerson(id, person);
        Optional<Person> optPerson = personService.updatePerson(id, person);
        if(optPerson.isPresent()){
            return ResponseEntity.ok(optPerson.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDefinition(@PathVariable("id") int id) {
        Optional<Person> optPerson = personService.getPerson(id);
        if(optPerson.isPresent()){
            return ResponseEntity.ok().<Void>build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
