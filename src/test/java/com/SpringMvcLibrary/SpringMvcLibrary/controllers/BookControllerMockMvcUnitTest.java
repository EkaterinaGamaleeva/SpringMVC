package com.SpringMvcLibrary.SpringMvcLibrary.controllers;

import com.SpringMvcLibrary.SpringMvcLibrary.models.Book;
import com.SpringMvcLibrary.SpringMvcLibrary.repositories.BooksRepository;
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

@WebMvcTest(AuthorsController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = {AuthorsController.class})
public class BookControllerMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BooksRepository repository;

    @MockBean
    private BooksController controller;
    @MockBean
    private BooksService service;
    @Test
    public void getUser_ReturnsJsonNotInfoOrder()  throws Exception {
        Book book =new Book(1,"Первая",1987);
        List<Book> books=new ArrayList<>();
        books.add(book);
        Mockito.when(this.service.findAll(true)).thenReturn(books);
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.titleBook").value(book.getTitleBook()))
                .andExpect(jsonPath("$.year").value(book.getYear()))
                .andExpect( jsonPath("$.nameAuthor").value(book.getNameAuthor()))
                .andExpect( jsonPath("$.authorBooks").value(book.getAuthorBooks()))
                .andExpect( jsonPath("$.takenAt").value(book.getTakenAt()))
                .andReturn();
    }
}
