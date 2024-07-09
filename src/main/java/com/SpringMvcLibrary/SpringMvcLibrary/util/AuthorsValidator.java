package com.SpringMvcLibrary.SpringMvcLibrary.util;


import com.SpringMvcLibrary.SpringMvcLibrary.models.Author;
import com.SpringMvcLibrary.SpringMvcLibrary.services.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AuthorsValidator implements Validator {


    private final AuthorsService authorsService;
@Autowired
    public AuthorsValidator(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return  Author.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Author author = (Author) o;
        if (authorsService.getAuthorByFullName(author.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");
        }
}
}