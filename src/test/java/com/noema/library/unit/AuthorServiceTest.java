package com.noema.library.unit;


import com.noema.library.unit.builder.AuthorBuilder;
import com.noema.library.dto.AuthorRequestDTO;
import com.noema.library.dto.AuthorResponseDTO;
import com.noema.library.dto.mapper.AuthorMapper;
import com.noema.library.exception.ResourceNotFoundException;
import com.noema.library.model.Author;
import com.noema.library.repository.AuthorRepository;
import com.noema.library.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void testAddAuthor() {
        AuthorRequestDTO requestDTO = new AuthorRequestDTO();
        requestDTO.setName("Author Name");
        requestDTO.setBio("Author Bio");

        Author author = new AuthorBuilder()
                .withName(requestDTO.getName())
                .withBio(requestDTO.getBio())
                .build();

        Author savedAuthor = new AuthorBuilder()
                .withId(1L)
                .withName(requestDTO.getName())
                .withBio(requestDTO.getBio())
                .build();

        when(authorMapper.toEntity(requestDTO)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(savedAuthor);
        when(authorMapper.toDto(savedAuthor)).thenReturn(new AuthorResponseDTO(savedAuthor.getId(), savedAuthor.getName(), savedAuthor.getBio()));

        AuthorResponseDTO responseDTO = authorService.addAuthor(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Author Name", responseDTO.getName());
        assertEquals("Author Bio", responseDTO.getBio());
    }

    @Test
    void testGetAuthorById() {
        Author author = new AuthorBuilder()
                .withId(1L)
                .withName("Author Name")
                .withBio("Author Bio")
                .build();

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(new AuthorResponseDTO(author.getId(), author.getName(), author.getBio()));

        AuthorResponseDTO responseDTO = authorService.getAuthorById(1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Author Name", responseDTO.getName());
        assertEquals("Author Bio", responseDTO.getBio());
    }

    @Test
    void testGetAuthorById_NotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> authorService.getAuthorById(1L));
    }

    @Test
    void testUpdateAuthor() {
        Long authorId = 1L;
        AuthorRequestDTO requestDTO = new AuthorRequestDTO();
        requestDTO.setName("Updated Name");
        requestDTO.setBio("Updated Bio");

        Author author = new AuthorBuilder()
                .withId(authorId)
                .withName("Original Name")
                .withBio("Original Bio")
                .build();

        Author updatedAuthor = new AuthorBuilder()
                .withId(authorId)
                .withName(requestDTO.getName())
                .withBio(requestDTO.getBio())
                .build();

        when(authorRepository.existsById(authorId)).thenReturn(true);
        when(authorMapper.toEntity(requestDTO)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(updatedAuthor);
        when(authorMapper.toDto(updatedAuthor)).thenReturn(new AuthorResponseDTO(authorId, "Updated Name", "Updated Bio"));

        AuthorResponseDTO responseDTO = authorService.updateAuthor(authorId, requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Updated Name", responseDTO.getName());
        assertEquals("Updated Bio", responseDTO.getBio());
    }

    @Test
    void testDeleteAuthor() {
        Long authorId = 1L;

        when(authorRepository.existsById(authorId)).thenReturn(true);
        doNothing().when(authorRepository).deleteById(authorId);

        authorService.deleteAuthor(authorId);

        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    void testDeleteAuthor_NotFound() {
        Long authorId = 1L;

        when(authorRepository.existsById(authorId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> authorService.deleteAuthor(authorId));
    }


}
