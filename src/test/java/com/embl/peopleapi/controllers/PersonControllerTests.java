package com.embl.peopleapi.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import com.embl.peopleapi.entities.*;
import com.embl.peopleapi.models.SimplePersonList;
import com.embl.peopleapi.services.JsonUtil;
import com.embl.peopleapi.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.FieldDescriptor;
//import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(PersonController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
@TestInstance(Lifecycle.PER_CLASS)
public class PersonControllerTests{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    RestDocumentationContextProvider restDocumentation;

    @MockBean
    private PersonService service;

    FieldDescriptor[] batchDescriptor = new FieldDescriptor[]{
        fieldWithPath("first_name").description("The first name of a person."),
        fieldWithPath("last_name").description("The last name of a person."),
        fieldWithPath("age").description("The age of a person"),
        fieldWithPath("favourite_colour").description("The person's favourite colour.")
    };

    private String getPersonAsJsonString() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(person0);
    }
    Person person0 = new Person("Harry", "Mcguire", 25, "BLUE");
    Person person1 = new Person("Joy", "Burgundy", 25, "RED");
    Person person2 = new Person("James", "Joyce", 29, "GREEN");
    Person person3 = new Person("Bruno", "Fernandez", 27, "GREEN");

    List<Person> persons = Arrays.asList(person1, person2, person3);
    Optional<Person> personOptional = Optional.of(person1);

    @BeforeAll
    public void mockup() throws Exception{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(documentationConfiguration(restDocumentation).operationPreprocessors()
                .withRequestDefaults(prettyPrint()) 
                .withResponseDefaults(prettyPrint())) 
            .build();
    }

    private void initIds(){
        person1.setId(1); person2.setId(2); person3.setId(3);
    }

    @Test
    //@WithMockUser(username = "test", password = "test", roles = "USER")
    public void canListPeople() throws Exception{
        //
        initIds();
        //
        SimplePersonList spl = new SimplePersonList();
        spl.setPerson(persons);
        when(service.getPersons2()).thenReturn(spl);

        this.mockMvc.perform(get("/persons/"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("person")))
            .andDo(document("person-getall"));
    }

    @Test
    public void canListPeopleVersion0() throws Exception{
        initIds();
        String jsonString = JsonUtil.PersonsToJsonString(persons);
        when(service.getPersons()).thenReturn(jsonString);

        this.mockMvc.perform(get("/persons/v0"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("person")))
            .andDo(document("person-getall-orig"));
    }

    @Test
    // @WithMockUser(username = "test", password = "test", roles = "USER")
    public void canGetPersonDetails() throws Exception{
        person2.setId(2);
        when(service.getPerson(any(Integer.class))).thenReturn(Optional.of(person2));

        this.mockMvc
            .perform(get("/persons/2/"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("person-getone"));
    }

    @Test
    // @WithMockUser(username = "test", password = "test", roles = "USER")
    public void canCreatePerson() throws Exception{
        person0.setId(5);
        when(service.addPerson(any(Person.class))).thenReturn(person0);

        //String request = getPersonAsJsonString();
        String request = "{\"age\" : 25, \"first_name\" : \"Harry\", \"last_name\" : \"Mcguire\", \"favourite_colour\" : \"BLUE\"}";
        
        this.mockMvc
            .perform(post("/persons/")//.with(csrf())
                .contentType("application/json").content(request))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("person-create"));
    }

    @Test
    // @WithMockUser(username = "test", password = "test", roles = "USER")
    public void canEditPerson() throws Exception{
        person3.setFavouriteColour("RED");
        person3.setId(3);
        when(service.updatePerson(any(Integer.class), any(Person.class))).thenReturn(Optional.of(person3));

        String request = "{\"age\" : 27, \"first_name\" : \"Bruno\", \"last_name\" : \"Fernandez\", \"favourite_colour\" : \"RED\"}";

        this.mockMvc
            .perform(put("/persons/3/").contentType("application/json").content(request))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("person-update"));
    }

    @Test
    // @WithMockUser(username = "test", password = "test", roles = "USER")
    public void canDeletePerson() throws Exception{
        Mockito.when(service.getPerson(2)).thenReturn(Optional.of(person2));
        Mockito.doNothing().when(service).deletePerson(any(Integer.class));
        // Mockito.doAnswer(invocation -> {
        //         Object arg0 = invocation.getArgument(0);
        //     }
        // ).when(service).deletePerson(any(Integer.class));

        this.mockMvc
            .perform(delete("/persons/2"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("person-delete"));
    }

    @Test
    // @WithMockUser(username = "test", password = "test", roles = "USER")
    public void cannotDeleteNoneExitingPerson() throws Exception{
        Mockito.when(service.getPerson(5)).thenReturn(Optional.empty());
        Mockito.doNothing().when(service).deletePerson(any(Integer.class));

        this.mockMvc
            .perform(delete("/persons/15"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andDo(document("person-delete-noone"));
    }
}