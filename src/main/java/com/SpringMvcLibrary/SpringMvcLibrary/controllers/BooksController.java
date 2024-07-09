package com.SpringMvcLibrary.SpringMvcLibrary.controllers;


import com.SpringMvcLibrary.SpringMvcLibrary.models.Author;
import com.SpringMvcLibrary.SpringMvcLibrary.models.Book;
import com.SpringMvcLibrary.SpringMvcLibrary.response.BooksCreateException;
import com.SpringMvcLibrary.SpringMvcLibrary.response.BooksErrorResponse;
import com.SpringMvcLibrary.SpringMvcLibrary.response.BooksNotFoundExcention;
import com.SpringMvcLibrary.SpringMvcLibrary.services.BooksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;


    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
        }

    @GetMapping("")
    public ResponseEntity findAll() {
            return new ResponseEntity<>(this.booksService.findAll(true),HttpStatus.OK);
    }
    @GetMapping("/{page}/{books_per_page}/{sort_by_year}")
    public ResponseEntity findAll(@RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                                  @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {

        if (page == null || booksPerPage == null) {
            return new ResponseEntity<>(booksService.findAll(sortByYear), HttpStatus.OK);// выдача всех книг
        }
        else

            return new ResponseEntity<>( booksService.findWithPagination(page, booksPerPage, sortByYear),HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable("id") int id) {

        return new ResponseEntity<>( booksService.findOne(id),HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity create( @RequestBody @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            List<FieldError> errors =  bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                builder.append(error.getField()).append("-").append(error.getDefaultMessage()).append(";");
            }
            throw new BooksCreateException(errors.toString());
        }
        booksService.save(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                builder.append(error.getField()).append("-").append(error.getDefaultMessage()).append(";");
                throw new BooksNotFoundExcention(errors.toString());
            }
        }
        booksService.update(id, book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return new ResponseEntity<>(HttpStatus.LOOP_DETECTED);
    }

    @PatchMapping("/{id}/asaAuthor")
    public ResponseEntity asAuthor(@PathVariable("id") int id, @RequestBody @Valid Author author,BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                builder.append(error.getField()).append("-").append(error.getDefaultMessage()).append(";");
                throw new BooksNotFoundExcention(errors.toString());
            }
        }
        Author authorBook = booksService.getBookAuthor(id);
        if (authorBook != null) {
        } else
            booksService.asAuthor(id, author);
        return new ResponseEntity<>(booksService.findOne(id),HttpStatus.OK);
    }

    @GetMapping("/{query}/search")
    public ResponseEntity makeSearch(@PathVariable("query")  String query) {
        return new ResponseEntity<>(booksService.searchByTitleBook(query),HttpStatus.OK);
    }

     @ExceptionHandler
     private ResponseEntity<BooksErrorResponse> handlerException(BooksNotFoundExcention e) {
           BooksErrorResponse response = new BooksErrorResponse("Заказ с таким id не найдем", System.currentTimeMillis());

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<BooksErrorResponse> handlerException(BooksCreateException e) {
       BooksErrorResponse response = new BooksErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}