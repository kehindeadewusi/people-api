package com.embl.peopleapi.repos;

import java.util.Optional;

import com.embl.peopleapi.entities.Person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class JpaTests{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepo personRepo;

    @Test
    public void testFindPerson() {
        // initialize
        Person joe = new Person("Joe", "Biden", 77, "BLUE");
        entityManager.persist(joe);
        entityManager.flush();

        // test retrieval
        Optional<Person> found = personRepo.findById(joe.getId());

        // asserts
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getFirstName()).isEqualTo(joe.getFirstName());
        assertThat(found.get().getLastName()).isEqualTo(joe.getLastName());
    }

    @Test
    public void testEditPerson() {
        // initialize
        Person joe = new Person("Joe", "Bideen", 77, "BLUE");
        entityManager.persist(joe);
        entityManager.flush();

        joe.setLastName("Biden");
        personRepo.save(joe);
        Optional<Person> found = personRepo.findById(joe.getId());

        // asserts
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getLastName()).isEqualTo("Biden");
    }

    @Test
    public void testDeletePerson() {
        // initialize
        Person joe = new Person("Joe", "Biden", 77, "BLUE");
        entityManager.persist(joe);
        entityManager.flush();

        personRepo.deleteById(joe.getId());
        // test retrieval
        Optional<Person> found = personRepo.findById(joe.getId());

        // asserts
        assertThat(found.isPresent()).isFalse();
    }
}