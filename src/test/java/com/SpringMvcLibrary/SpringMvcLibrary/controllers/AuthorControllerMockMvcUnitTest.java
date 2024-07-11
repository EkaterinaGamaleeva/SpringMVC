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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ActiveProfiles("test")
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
    public void getUser_ReturnsJsonNotInfoOrder()  throws Exception {
        Author author= new Author(1,"Гaмалеев Денис Олегович",2000);

        List<Author> authors = new ArrayList<>();
        authors.add(author);
        Mockito.when(service.findAll()).thenReturn(authors);
       var a= mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(author.getId()))
                .andExpect(jsonPath("$[0].fullName").value(author.getFullName()))
                .andExpect(jsonPath("$[0].yearOfBirth").value(author.getYearOfBirth()))
                .andExpect( jsonPath("$[0].books").value(author.getBooks()))
                .andReturn();
        System.out.println(a.getResponse().getContentAsString());
    }
    @Test
    public void getFindOne_ReturnsJsonNotInfoOrder()  throws Exception {
        Author author= new Author(1,"Гaмалеев Денис Олегович",2000);

        List<Author> authors = new ArrayList<>();
        authors.add(author);
        Mockito.when(service.findOne(1)).thenReturn(author);
        var a= mockMvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(author.getId()))
                .andExpect(jsonPath("$[0].fullName").value(author.getFullName()))
                .andExpect(jsonPath("$[0].yearOfBirth").value(author.getYearOfBirth()))
                .andExpect( jsonPath("$[0].books").value(author.getBooks()))
                .andReturn();
        System.out.println(a.getResponse().getContentAsString());
    }
}