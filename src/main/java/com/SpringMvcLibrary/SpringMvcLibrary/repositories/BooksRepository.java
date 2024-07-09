package com.SpringMvcLibrary.SpringMvcLibrary.repositories;


import com.SpringMvcLibrary.SpringMvcLibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer>  {
    List<Book> findByTitleBookStartingWith(String title);
}
