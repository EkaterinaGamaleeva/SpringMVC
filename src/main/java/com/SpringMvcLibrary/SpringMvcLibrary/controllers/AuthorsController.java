package com.SpringMvcLibrary.SpringMvcLibrary.controllers;

import com.SpringMvcLibrary.SpringMvcLibrary.models.Author;
import com.SpringMvcLibrary.SpringMvcLibrary.response.AuthorsCreateException;
import com.SpringMvcLibrary.SpringMvcLibrary.response.AuthorsErrorResponse;
import com.SpringMvcLibrary.SpringMvcLibrary.response.AuthorsNotFoundException;
import com.SpringMvcLibrary.SpringMvcLibrary.services.AuthorsService;

import com.SpringMvcLibrary.SpringMvcLibrary.util.AuthorsValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/authors")
public class AuthorsController {
private final AuthorsValidator authorsValidator;
private final AuthorsService authorsService;
    @Autowired
    public AuthorsController(AuthorsValidator authorsValidator, AuthorsService authorsService) {
        this.authorsValidator = authorsValidator;
        this.authorsService = authorsService;
    }


    @GetMapping
   //  List<Author>
    public ResponseEntity getFindAll() {
        return new ResponseEntity<>(authorsService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity getBooksAll(@PathVariable("id") int id) {
        return new ResponseEntity<>(authorsService.getBooksByPersonId(id),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity  findOne(@PathVariable("id") int id) {
        return new ResponseEntity<>(authorsService.findOne(id),HttpStatus.OK) ;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Author author, BindingResult bindingResult) {
    authorsValidator.validate(author,bindingResult);
        authorsService.save(author);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid Author author, BindingResult bindingResult,
                                             @PathVariable("id") int id) {
        authorsService.update(id, author);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        authorsService.delete(id);
        return new ResponseEntity(HttpStatus.LOOP_DETECTED);
    }

}