package com.SpringMvcLibrary.SpringMvcLibrary.controllers;

import com.SpringMvcLibrary.SpringMvcLibrary.models.Author;
import com.SpringMvcLibrary.SpringMvcLibrary.models.Book;
import com.SpringMvcLibrary.SpringMvcLibrary.repositories.BooksRepository;
import com.SpringMvcLibrary.SpringMvcLibrary.services.BooksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BooksController.class)
public class BookControllerMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BooksRepository repository;
    @MockBean
    private BooksService service;
    @Test
    public void givenBooks_whenAdd_thenStatus201andPersonReturned() throws Exception {
        Book book =new Book(1,"Первая",1987);
        Mockito.when(repository.save(Mockito.any())).thenReturn(book);
        mockMvc.perform(post("/books")
                                .content(objectMapper.writeValueAsString(book))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getFindAll_ReturnsJsonNotSum()  throws Exception {
        Book book =new Book(1,"Первая",1987);
        List<Book> books=new ArrayList<>();
        books.add(book);
        Mockito.when(service.findAll(true)).thenReturn(books);
     var a=   mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(book.getId()))
                .andExpect(jsonPath("$[0].titleBook").value(book.getTitleBook()))
                .andExpect(jsonPath("$[0].year").value(book.getYear()))
                .andExpect( jsonPath("$[0].nameAuthor").value(book.getNameAuthor()))
                .andExpect( jsonPath("$[0].authorBooks").value(book.getAuthorBooks()))
                .andExpect( jsonPath("$[0].takenAt").value(book.getTakenAt()))
                .
             andReturn();
    }
    @Test
    public void getFindOne_ReturnsJsonYesSum()  throws Exception {
        Book book =new Book(1,"Первая",1987);
        List<Book> books=new ArrayList<>();
        Mockito.when(service.findOne(1)).thenReturn(book);
        var a=   mockMvc.perform(get("/books/1"))
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
