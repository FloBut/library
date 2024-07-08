package com.noema.library.dto.mapper;


import com.noema.library.dto.BookRequestDTO;
import com.noema.library.dto.BookResponseDTO;
import com.noema.library.model.Author;
import com.noema.library.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookResponseDTO toDto(Book book) {
        if (book == null) {
            return null;
        }

        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setId(book.getId());
        bookResponseDTO.setTitle(book.getTitle());
        bookResponseDTO.setDescription(book.getDescription());
        bookResponseDTO.setIsbn(book.getIsbn());
        bookResponseDTO.setAuthorId(book.getAuthor().getId());
        return bookResponseDTO;
    }

    public Book toEntity(BookRequestDTO bookRequestDTO, Author author) {
        if (bookRequestDTO == null) {
            return null;
        }

        Book book = new Book();
        book.setTitle(bookRequestDTO.getTitle());
        book.setDescription(bookRequestDTO.getDescription());
        book.setIsbn(bookRequestDTO.getIsbn());
        book.setAuthor(author);
        return book;
    }
}

