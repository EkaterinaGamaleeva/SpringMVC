package com.SpringMvcLibrary.SpringMvcLibrary.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.ToString;

import java.util.Date;
@Entity
@Table(name = "books")
@ToString
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название книги должно быть от 2 до 100 символов длиной")
    @Column(name = "title_book")
    private String titleBook;


    @Min(value = 1500, message = "Год должен быть больше, чем 1500")
    @Column(name = "year")
    private int year;

    @NotNull(message = "Автор не должен быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора должно быть от 2 до 100 символов длиной")
    @Column(name = "name_author")
    private String nameAuthor;

    @ManyToOne(cascade = CascadeType.ALL)
  //  @NotNull(message = "Автор не должен быть пустым")
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author authorBooks;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

//  По-умолчанию false.
    public Book() {

    }

    public Book(int id, String titleBook, int year, String nameAuthor) {
        this.id = id;
        this.titleBook = titleBook;
        this.year = year;
        this.nameAuthor = nameAuthor;
    }

    public Book(int id, String titleBook, int year, String nameAuthor, Author authorBooks) {
        this.id = id;
        this.titleBook = titleBook;
        this.year = year;
        this.nameAuthor = nameAuthor;
        this.authorBooks = authorBooks;
    }
    public Book(int id, String titleBook, int year, Author authorBooks) {
        this.id = id;
        this.titleBook = titleBook;
        this.year = year;
        this.nameAuthor = nameAuthor;
        this.authorBooks = authorBooks;
    }

    public Book(int id, String titleBook, int year) {
        this.id = id;
        this.titleBook = titleBook;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleBook() {
        return titleBook;
    }

    public void setTitleBook(String titleBook) {
        this.titleBook = titleBook;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public Author getAuthorBooks() {
        return authorBooks;
    }

    public void setAuthorBooks(Author authorBooks) {
        this.authorBooks = authorBooks;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

}