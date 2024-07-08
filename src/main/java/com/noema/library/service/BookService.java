package com.noema.library.service;


import com.noema.library.dto.BookRequestDTO;
import com.noema.library.dto.BookResponseDTO;
import com.noema.library.dto.mapper.AuthorMapper;
import com.noema.library.dto.mapper.BookMapper;
import com.noema.library.exception.ResourceNotFoundException;
import com.noema.library.model.Author;
import com.noema.library.model.Book;
import com.noema.library.repository.AuthorRepository;
import com.noema.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    public BookResponseDTO addBook(BookRequestDTO bookRequestDTO) {
        Author author = authorRepository.findById(bookRequestDTO.getAuthorId()).orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + bookRequestDTO.getAuthorId()));
        Book book = bookMapper.toEntity(bookRequestDTO, author);
        return bookMapper.toDto(bookRepository.save(book));
    }

    public BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id " + id);
        }
        Author author = authorRepository.findById(bookRequestDTO.getAuthorId()).orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + bookRequestDTO.getAuthorId()));
        Book book = bookMapper.toEntity(bookRequestDTO, author);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id " + id);
        }
        bookRepository.deleteById(id);
    }

    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
        return bookMapper.toDto(book);
    }

    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}