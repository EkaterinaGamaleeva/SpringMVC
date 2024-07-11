package com.SpringMvcLibrary.SpringMvcLibrary.controllers;

import com.SpringMvcLibrary.SpringMvcLibrary.models.Author;
import com.SpringMvcLibrary.SpringMvcLibrary.models.Book;
import com.SpringMvcLibrary.SpringMvcLibrary.repositories.AuthorsRepository;
import com.SpringMvcLibrary.SpringMvcLibrary.repositories.BooksRepository;
import com.SpringMvcLibrary.SpringMvcLibrary.services.AuthorsService;
import com.SpringMvcLibrary.SpringMvcLibrary.services.BooksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorsController.class)
public class AuthorControllerMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthorsRepository repository;

    @MockBean
    private AuthorsController controller;
    @MockBean
    private AuthorsService service;
    @Test
    public void givenAuthors_whenAdd_thenStatus201andPersonReturned() throws Exception {
        Author author= new Author(1,"Гaмалеев Денис Олегович",2000, List.of(new Book()));
        Mockito.when(repository.save(Mockito.any())).thenReturn(author);
        mockMvc.perform(
                        post("/authors")
                                .content(objectMapper.writeValueAsString(author))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }


    @Test
    public void getUser_ReturnsJsonNotInfoOrder()  throws Exception {
        Author author= new Author(1,"Гaмалеев Денис Олегович",2000);
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        Mockito.when(service.findAll()).thenReturn(authors);
       var a= mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value(author.getId()))
                .andExpect(jsonPath("[0].fullName").value(author.getFullName()))
                .andExpect(jsonPath("[0].yearOfBirth").value(author.getYearOfBirth()))
                .andExpect( jsonPath("[0].books").value(author.getBooks()))

                .andReturn();
        System.out.println(a.getResponse().getContentAsString());
    }
    @Test
    public void getFindOne_ReturnsJsonNotInfoOrder()  throws Exception {
        Author author= new Author(1,"Гaмалеев Денис Олегович",2000);
        Mockito.when(repository.save(Mockito.any())).thenReturn(author);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(author));
        Mockito.when(service.findOne(1)).thenReturn(author);
        mockMvc.perform(
                        get("/authors/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.fullName)").value("Гaмалеев Денис Олегович"))
                .andExpect(jsonPath("$.yearOfBirth").value(2000))
                .andExpect( jsonPath("$.books").value(null));
    }
}