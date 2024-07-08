package com.noema.library.unit.builder;

import com.noema.library.model.Author;
import com.noema.library.model.Book;

public class BookBuilder {
    private Long id;
    private String title = "Default Title";
    private String description = "Default Description";
    private String isbn = "1234567890";
    private Author author;

    public BookBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public BookBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public BookBuilder withIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public BookBuilder withAuthor(Author author) {
        this.author = author;
        return this;
    }

    public Book build() {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setDescription(description);
        book.setIsbn(isbn);
        book.setAuthor(author);
        return book;
    }
}
