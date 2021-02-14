package com.embl.peopleapi;

import com.embl.peopleapi.entities.Person;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
@JsonTest
public class SerializationTests {
  @Autowired
  private JacksonTester<Person> json;
  
  @Test
  public void testPersonSerialization() throws Exception{
    Person person = new Person("Lateef", "Jakande", 55, "GREEN");
    JsonContent<Person> result = this.json.write(person);
 
    assertThat(result).hasJsonPathStringValue("$.first_name");
    assertThat(result).extractingJsonPathStringValue("$.last_name").isEqualTo("Jakande");
    assertThat(result).extractingJsonPathNumberValue("$.age").isEqualTo(55);
    assertThat(result).extractingJsonPathStringValue("$.favourite_colour").isEqualTo("GREEN");
  }
}
