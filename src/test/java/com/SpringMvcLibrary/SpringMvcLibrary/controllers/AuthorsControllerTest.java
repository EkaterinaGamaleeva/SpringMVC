package com.SpringMvcLibrary.SpringMvcLibrary.controllers;

import com.SpringMvcLibrary.SpringMvcLibrary.models.Author;
import com.SpringMvcLibrary.SpringMvcLibrary.models.Book;
import com.SpringMvcLibrary.SpringMvcLibrary.repositories.AuthorsRepository;
import com.SpringMvcLibrary.SpringMvcLibrary.services.AuthorsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class AuthorsControllerTest {
    @Mock
    private AuthorsService authorsService;
    @Mock
    private AuthorsRepository authorsRepository;
    @InjectMocks
    private AuthorsController authorsController;

    @Test
    void getFindAll_ResultListAuthor() {
        Author author = new Author(1,"Гaмалеева Варвара Денисовна",2023);
        Author author2 = new Author(2,"Гaмалеева Екатерина Ивановна",2000);
        Author author3 = new Author(3,"Гaмалеев Денис Олегович",2000);

        List<Author> authors = new ArrayList<>();
        authors.add(author);
        authors.add(author2);
        authors.add(author3);
        Mockito.when(authorsService.findAll()).thenReturn(authors);

        var responseEntity = this.authorsController.getFindAll();

        assertNotNull(responseEntity);
        assertEquals(authors, responseEntity.getBody());
    }

    @Test
    void getBooksAll_ReturnListBooksHttpStatusOk() {
        int id=1;
        Book book =new Book(1,"Первая",1987);
        Book book2 =new Book(2,"Вторая",1952);
        Book book3 =new Book(3,"Третья",1800);
        List<Book> books=new ArrayList<>();
        books.add(book);
        books.add(book2);
        books.add(book3);
        Author author = new Author(id,"Гaмалеева Варвара Денисовна",2023,books);
        Mockito.when(authorsService.getBooksByPersonId(id)).thenReturn(books);

        var responseEntity = this.authorsController.getBooksAll(id);

        assertNotNull(responseEntity);
        assertEquals(books, responseEntity.getBody());

    }

    @Test
    void findOne_ReturnAuthor() {
        int id=1;
        Author author = new Author(id,"Гaмалеева Варвара Денисовна",2023);
        Mockito.when(authorsService.findOne(id)).thenReturn(author);
        var responseEntity = this.authorsController.findOne(id);

        assertNotNull(responseEntity);
        assertEquals(author, responseEntity.getBody());
    }

    @Test
    void create_ReturnHttpStatusCREATED() {
        int id=1;
        Author author = new Author(id,"Гaмалеева Варвара Денисовна",2023);

        BindingResult bindingResult = new BeanPropertyBindingResult(author, "author");

        var responseEntity = this.authorsController.create(author, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void update_ReturnHttpStatusOk() {
        Author author = new Author(1,"Гaмалеева Варвара Денисовна",2023);

        BindingResult bindingResult = new BeanPropertyBindingResult(author, "user");
        var responseEntity = this.authorsController.update(new Author(2,"Гaмалеева Екатерина Ивановна",2000), bindingResult, 1);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void delete_ReturnHttpStatusOk() {
        Author author = new Author(1,"Гaмалеева Варвара Денисовна",2023);
        var responseEntity = this.authorsController.delete(1);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.LOOP_DETECTED, responseEntity.getStatusCode());
    }
}