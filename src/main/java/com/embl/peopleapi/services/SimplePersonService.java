package com.embl.peopleapi.services;

import java.util.List;
import java.util.Optional;

import com.embl.peopleapi.entities.Person;
import com.embl.peopleapi.models.SimplePersonList;
import com.embl.peopleapi.repos.PersonRepo;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SimplePersonService implements PersonService {
    private PersonRepo personRepo;

    public SimplePersonService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public String getPersons() {
        List<Person> persons = personRepo.findAll();

        try {
            return JsonUtil.PersonsToJsonString(persons);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public SimplePersonList getPersons2() {
        List<Person> persons = personRepo.findAll();
        SimplePersonList spl = new SimplePersonList();
        spl.setPerson(persons);
        return spl;
    }

    @Override
    public Person addPerson(Person person) {
        return personRepo.save(person);
    }

    @Override
    public Optional<Person> updatePerson(int id, Person person) {
        Optional<Person> existing = personRepo.findById(id);
        if (!existing.isPresent())
            return Optional.empty();
        BeanUtils.copyProperties(person, existing, "id");
        personRepo.save(person);
        return Optional.of(person);
    }

    @Override
    public Optional<Person> getPerson(int id) {
        return personRepo.findById(id);
    }

    @Override
    public void deletePerson(int id) {
        personRepo.deleteById(id);
    }
}
