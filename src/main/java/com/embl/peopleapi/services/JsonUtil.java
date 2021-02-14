package com.embl.peopleapi.services;

import java.util.List;

import com.embl.peopleapi.entities.Person;
import com.embl.peopleapi.serializers.PersonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionLikeType;

public class JsonUtil {
    public static String PersonsToJsonString(List<Person> persons) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CollectionLikeType type = mapper.getTypeFactory().constructCollectionLikeType(List.class, Person.class);

        SimpleModule module = new SimpleModule();
        module.addSerializer(new PersonSerializer(type));
        mapper.registerModule(module);

        String payload = mapper.writeValueAsString(persons);
        System.out.println(payload);
        return payload;
    }
}
