package com.SpringMvcLibrary.SpringMvcLibrary.services;



import com.SpringMvcLibrary.SpringMvcLibrary.models.Author;
import com.SpringMvcLibrary.SpringMvcLibrary.models.Book;
import com.SpringMvcLibrary.SpringMvcLibrary.repositories.AuthorsRepository;
import com.SpringMvcLibrary.SpringMvcLibrary.response.AuthorsNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthorsService {
    private final AuthorsRepository authorsRepository;
@Autowired
    public AuthorsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }


    public List<Author> findAll()
    {
        return authorsRepository.findAll();
    }

    public Author findOne(int id) {
        Optional<Author> foundPerson = authorsRepository.findById(id);
        return foundPerson.orElseThrow(AuthorsNotFoundException::new);
    }

    @Transactional
    public void save(Author author) {
    authorsRepository.save(author);
    }

    @Transactional
    public void update(int id, Author updatedAuthor) {
        updatedAuthor.setId(id);
        authorsRepository.save(updatedAuthor);
    }

    @Transactional
    public void delete(int id) {
        authorsRepository.deleteById(id);
    }

    public Optional<Author> getAuthorByFullName(String fullName) {
        return authorsRepository.findByFullName(fullName);
    }

    public List<Book> getBooksByPersonId(int id) {
        Optional<Author> author = authorsRepository.findById(id);

        if (author.isPresent()) {
            Hibernate.initialize(author.get().getBooks());
            return author.get().getBooks();
        }
        else {
            return Collections.emptyList();
        }
    }

}