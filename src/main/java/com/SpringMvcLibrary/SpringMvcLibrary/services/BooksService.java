package com.SpringMvcLibrary.SpringMvcLibrary.services;


import com.SpringMvcLibrary.SpringMvcLibrary.models.Author;
import com.SpringMvcLibrary.SpringMvcLibrary.models.Book;
import com.SpringMvcLibrary.SpringMvcLibrary.repositories.BooksRepository;
import com.SpringMvcLibrary.SpringMvcLibrary.response.AuthorsNotFoundException;
import com.SpringMvcLibrary.SpringMvcLibrary.response.BooksNotFoundExcention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear==true)
            return booksRepository.findAll(Sort.by("year"));
        else
            return booksRepository.findAll();
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear==true)
            return booksRepository.findAll(PageRequest.of(page, booksPerPage,Sort.by("year"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElseThrow(BooksNotFoundExcention::new);
    }

    public List<Book> searchByTitleBook(String query) {
        return booksRepository.findByTitleBookStartingWith(query);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = booksRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setAuthorBooks(bookToBeUpdated.getAuthorBooks()); // чтобы не терялась связь при обновлении

        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }


    public Author getBookAuthor(int id) {
        // Здесь Hibernate.initialize() не нужен, так как владелец (сторона One) загружается не лениво
        return booksRepository.findById(id).get().getAuthorBooks();
    }


    @Transactional
    public void asAuthor(int id, Author author
    ) {
       if (booksRepository.findById(id).isPresent()){
           booksRepository.findById(id).get().setAuthorBooks(author);
       }
    }


}
