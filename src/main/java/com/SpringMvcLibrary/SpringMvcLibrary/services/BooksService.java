package com.SpringMvcLibrary.SpringMvcLibrary.services;


import com.SpringMvcLibrary.SpringMvcLibrary.models.Author;
import com.SpringMvcLibrary.SpringMvcLibrary.models.Book;
import com.SpringMvcLibrary.SpringMvcLibrary.repositories.BooksRepository;
import com.SpringMvcLibrary.SpringMvcLibrary.response.AuthorsCreateException;
import com.SpringMvcLibrary.SpringMvcLibrary.response.BooksCreateException;
import com.SpringMvcLibrary.SpringMvcLibrary.response.BooksNotFoundExcention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return booksRepository.findAll(Sort.by("year"));
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear == true) {
            PageRequest pageRequest = PageRequest.of(page, booksPerPage, Sort.by("year"));
            return booksRepository.findAll(pageRequest).getContent();
        } else {
            PageRequest pageRequest2 = PageRequest.of(page, booksPerPage);
            return booksRepository.findAll(pageRequest2).getContent();
        }
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
        booksRepository.findById(book.getId()).orElseThrow(BooksCreateException::new);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = booksRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setAuthorBooks(bookToBeUpdated.getAuthorBooks()); // чтобы не терялась связь при обновлении

        booksRepository.save(updatedBook);
        booksRepository.findById(updatedBook.getId()).orElseThrow(BooksNotFoundExcention::new);
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
    if (booksRepository.findById(id).get().getAuthorBooks()==null){
        new BooksNotFoundExcention();
    }
    }


}
