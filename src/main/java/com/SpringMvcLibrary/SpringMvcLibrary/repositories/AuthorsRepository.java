package com.SpringMvcLibrary.SpringMvcLibrary.repositories;


import com.SpringMvcLibrary.SpringMvcLibrary.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByFullName(String fullName);
}
