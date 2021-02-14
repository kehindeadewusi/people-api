package com.embl.peopleapi.serializers;

import com.embl.peopleapi.entities.Person;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;

public class PersonSerializer extends StdSerializer<List<Person>> {

    private static final long serialVersionUID = 5518693142637315942L;

    public PersonSerializer() {
        this(null);
    }

    public PersonSerializer(JavaType type) {
        super(type);
    }

	@Override
    public void serialize(List<Person> people, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        
        jsonGenerator.writeStartObject();// {

        jsonGenerator.writeArrayFieldStart("person");//[
        for (Person person : people) {
            jsonGenerator.writeStartObject();// {

            jsonGenerator.writeStringField("first_name", person.getFirstName());
            jsonGenerator.writeStringField("last_name", person.getLastName());
            jsonGenerator.writeNumberField("age", person.getAge());
            jsonGenerator.writeStringField("favourite_colour", person.getFavouriteColour());

            jsonGenerator.writeEndObject();// }
        }
        jsonGenerator.writeEndArray();//]

        jsonGenerator.writeEndObject();// }
    }
    
}
