package com.SpringMvcLibrary.SpringMvcLibrary.controllers;

import com.SpringMvcLibrary.SpringMvcLibrary.models.Author;
import com.SpringMvcLibrary.SpringMvcLibrary.models.Book;
import com.SpringMvcLibrary.SpringMvcLibrary.repositories.AuthorsRepository;
import com.SpringMvcLibrary.SpringMvcLibrary.repositories.BooksRepository;
import com.SpringMvcLibrary.SpringMvcLibrary.services.AuthorsService;
import com.SpringMvcLibrary.SpringMvcLibrary.services.BooksService;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BooksControllerTest {

    @Mock
    private BooksService booksService;
    @Mock
    private BooksRepository booksRepository;
    @InjectMocks
    private BooksController booksController;

    @Test
    void findAll_ResultListBookHttpStatusOK() {
        Book book =new Book(1,"Первая",1987);
        Book book2 =new Book(2,"Вторая",1952);
        Book book3 =new Book(3,"Третья",1890);
        Book book4 =new Book(4,"Четвертая",1870);
        Book book5 =new Book(5,"Пятая",1980);
        List<Book> books=new ArrayList<>();
        books.add(book);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        List<Book> sortBook = books.stream().sorted(Comparator.comparingInt(Book::getYear)).toList();
        var responseEntity = this.booksController.findAll(1,2,true);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    @Test
    void findAll_ResultListBookSortedHttpStatusOK() {
        Book book =new Book(1,"Первая",1987);
        Book book2 =new Book(2,"Вторая",1952);
        Book book3 =new Book(3,"Третья",1890);
        Book book4 =new Book(4,"Четвертая",1870);
        Book book5 =new Book(5,"Пятая",1980);
        List<Book> books=new ArrayList<>();
        books.add(book);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        Mockito.when(booksService.findAll(true)).thenReturn(books);
        List<Book> sortBook = books.stream().sorted(Comparator.comparingInt(Book::getYear)).toList();
        var responseEntity = this.booksController.findAll();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void findOne_ReturnBook() {
        Author author = new Author(1,"Гaмалеева Варвара Денисовна",2023);
        Book book =new Book(1,"Первая",1987,author);
        Mockito.when(booksService.findOne(1)).thenReturn(book);
        var responseEntity = this.booksController.findOne(1);

        assertNotNull(responseEntity);
        assertEquals(book, responseEntity.getBody());
    }

    @Test
    void create_ReturnHttpStatusCREATED() {
        Author author = new Author(1, "Гaмалеева Варвара Денисовна", 2023);
        Book book = new Book(1, "Первая", 1987, author);
        BindingResult bindingResult = new BeanPropertyBindingResult(book, "book");

        var responseEntity = this.booksController.create(book, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
    @Test
    void update_ReturnHttpStatusOk() {
        Author author = new Author(1,"Гaмалеева Варвара Денисовна",2023);
        Book book =new Book(1,"Первая",1987,author);
        BindingResult bindingResult = new BeanPropertyBindingResult(book, "user");
        var responseEntity = this.booksController.update(1,new Book(2,"вторая",1200,author), bindingResult);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void delete_ReturnHttpStatusOk() {
        Author author = new Author(1,"Гaмалеева Варвара Денисовна",2023);
        Book book =new Book(1,"Первая",1987,author);
        var responseEntity = this.booksController.delete(1);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.LOOP_DETECTED, responseEntity.getStatusCode());
    }
    @Test
    void asAuthor() {
        Author author = new Author(1,"Гaмалеева Варвара Денисовна",2023);
        Book book =new Book(1,"Первая",1987);
        BindingResult bindingResult = new BeanPropertyBindingResult(book, "user");
        var responseEntity = this.booksController.asAuthor(1,author,bindingResult);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void makeSearch() {
        String q="Первая";
        Author author = new Author(1,"Гaмалеева Варвара Денисовна",2023);
        Book book =new Book(1,q,1987,author);
        List<Book>books=new ArrayList<>();
        Mockito.when(booksService.searchByTitleBook(q)).thenReturn(books);
        var responseEntity = booksController.makeSearch("Первая");
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books, responseEntity.getBody());
    }
}