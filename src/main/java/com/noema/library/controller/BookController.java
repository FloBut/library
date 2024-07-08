package com.noema.library.controller;


import com.noema.library.dto.BookDetailsDTO;
import com.noema.library.dto.BookRequestDTO;
import com.noema.library.dto.BookResponseDTO;
import com.noema.library.service.BookDetailsService;
import com.noema.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    private  BookDetailsService bookDetailsService;

    @Autowired
    public BookController(BookService bookService, BookDetailsService bookDetailsService) {
        this.bookService = bookService;
        this.bookDetailsService = bookDetailsService;
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> addBook(@RequestBody BookRequestDTO bookDTO) {
        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody BookRequestDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }


    @GetMapping("/details")
    public Mono<ResponseEntity<BookDetailsDTO>> getBookDetailsByISBN(@RequestParam String isbn) {
        return bookDetailsService.fetchBookDetailsByISBN(isbn)
                .map(bookDetails -> ResponseEntity.ok(bookDetails))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(null)));
    }
}
