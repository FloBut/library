package com.noema.library.unit;

import com.noema.library.unit.builder.AuthorBuilder;
import com.noema.library.unit.builder.BookBuilder;
import com.noema.library.dto.BookRequestDTO;
import com.noema.library.dto.BookResponseDTO;
import com.noema.library.dto.mapper.BookMapper;
import com.noema.library.exception.ResourceNotFoundException;
import com.noema.library.model.Author;
import com.noema.library.model.Book;
import com.noema.library.repository.AuthorRepository;
import com.noema.library.repository.BookRepository;
import com.noema.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void testAddBook() {
        BookRequestDTO requestDTO = new BookRequestDTO();
        requestDTO.setTitle("Book Title");
        requestDTO.setDescription("Book Description");
        requestDTO.setIsbn("1234567890");
        requestDTO.setAuthorId(1L);

        Author author = new AuthorBuilder()
                .withId(1L)
                .build();

        Book book = new BookBuilder()
                .withTitle(requestDTO.getTitle())
                .withDescription(requestDTO.getDescription())
                .withIsbn(requestDTO.getIsbn())
                .withAuthor(author)
                .build();

        Book savedBook = new BookBuilder()
                .withId(1L)
                .withTitle(requestDTO.getTitle())
                .withDescription(requestDTO.getDescription())
                .withIsbn(requestDTO.getIsbn())
                .withAuthor(author)
                .build();

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookMapper.toEntity(requestDTO, author)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.toDto(savedBook)).thenReturn(new BookResponseDTO(savedBook.getId(), savedBook.getTitle(), savedBook.getDescription(), savedBook.getIsbn(), author.getId()));

        BookResponseDTO responseDTO = bookService.addBook(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Book Title", responseDTO.getTitle());
        assertEquals("Book Description", responseDTO.getDescription());
        assertEquals("1234567890", responseDTO.getIsbn());
        assertEquals(1L, responseDTO.getAuthorId());
    }

    @Test
    void testGetBookById() {
        Author author = new AuthorBuilder()
                .withId(1L)
                .build();

        Book book = new BookBuilder()
                .withId(1L)
                .withTitle("Book Title")
                .withDescription("Book Description")
                .withIsbn("1234567890")
                .withAuthor(author)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(new BookResponseDTO(book.getId(), book.getTitle(), book.getDescription(), book.getIsbn(), author.getId()));

        BookResponseDTO responseDTO = bookService.getBookById(1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Book Title", responseDTO.getTitle());
        assertEquals("Book Description", responseDTO.getDescription());
        assertEquals("1234567890", responseDTO.getIsbn());
        assertEquals(1L, responseDTO.getAuthorId());
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));
    }

    @Test
    void testUpdateBook() {
        Long bookId = 1L;
        BookRequestDTO requestDTO = new BookRequestDTO();
        requestDTO.setTitle("Updated Title");
        requestDTO.setDescription("Updated Description");
        requestDTO.setIsbn("Updated ISBN");
        requestDTO.setAuthorId(1L);

        Author author = new AuthorBuilder().withId(1L).build();
        Book originalBook = new BookBuilder().withId(bookId).withAuthor(author).build();
        Book updatedBook = new BookBuilder()
                .withId(bookId)
                .withTitle(requestDTO.getTitle())
                .withDescription(requestDTO.getDescription())
                .withIsbn(requestDTO.getIsbn())
                .withAuthor(author)
                .build();

        when(bookRepository.existsById(bookId)).thenReturn(true);
        when(authorRepository.findById(requestDTO.getAuthorId())).thenReturn(Optional.of(author));
        when(bookMapper.toEntity(requestDTO, author)).thenReturn(originalBook);
        when(bookRepository.save(originalBook)).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(new BookResponseDTO(bookId, "Updated Title", "Updated Description", "Updated ISBN", author.getId()));

        BookResponseDTO responseDTO = bookService.updateBook(bookId, requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Updated Title", responseDTO.getTitle());
        assertEquals("Updated Description", responseDTO.getDescription());
        assertEquals("Updated ISBN", responseDTO.getIsbn());
    }

    @Test
    void testUpdateBook_NotFound() {
        Long bookId = 1L;
        BookRequestDTO requestDTO = new BookRequestDTO();
        requestDTO.setAuthorId(1L);

        when(bookRepository.existsById(bookId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook(bookId, requestDTO));
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;

        when(bookRepository.existsById(bookId)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(bookId);

        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void testDeleteBook_NotFound() {
        Long bookId = 1L;

        when(bookRepository.existsById(bookId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(bookId));
    }

    @Test
    void testGetAllBooks() {
        Author author1 = new AuthorBuilder().withId(1L).withName("Author One").build();
        Author author2 = new AuthorBuilder().withId(2L).withName("Author Two").build();

        Book book1 = new BookBuilder().withId(1L).withTitle("Book One").withAuthor(author1).build();
        Book book2 = new BookBuilder().withId(2L).withTitle("Book Two").withAuthor(author2).build();

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        when(bookMapper.toDto(any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            return new BookResponseDTO(book.getId(), book.getTitle(), book.getDescription(), book.getIsbn(), book.getAuthor().getId());
        });

        List<BookResponseDTO> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Book One", result.get(0).getTitle());
        assertEquals(1L, result.get(0).getAuthorId()); // Verify author IDs are correct
        assertEquals("Book Two", result.get(1).getTitle());
        assertEquals(2L, result.get(1).getAuthorId());
    }

}
