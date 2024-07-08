package com.noema.library.service;



import com.noema.library.dto.AuthorRequestDTO;
import com.noema.library.dto.AuthorResponseDTO;
import com.noema.library.dto.mapper.AuthorMapper;
import com.noema.library.exception.ResourceNotFoundException;
import com.noema.library.model.Author;
import com.noema.library.repository.AuthorRepository;
import com.noema.library.repository.AuthorSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public AuthorResponseDTO addAuthor(AuthorRequestDTO authorRequestDTO) {
        Author author = authorMapper.toEntity(authorRequestDTO);
        return authorMapper.toDto(authorRepository.save(author));
    }

    public AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO authorRequestDTO) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found with id " + id);
        }
        Author author = authorMapper.toEntity(authorRequestDTO);
        author.setId(id);
        return authorMapper.toDto(authorRepository.save(author));
    }

    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found with id " + id);
        }
        authorRepository.deleteById(id);
    }

    public AuthorResponseDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + id));
        return authorMapper.toDto(author);
    }

    public List<AuthorResponseDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AuthorResponseDTO> searchAuthors(String name, String bioKeyword) {
        Specification<Author> spec = Specification.where(null);

        if (name != null) {
            spec = spec.and(AuthorSpecifications.hasName(name));
        }
        if (bioKeyword != null) {
            spec = spec.and(AuthorSpecifications.hasBioKeyword(bioKeyword));
        }


        List<Author> authors = authorRepository.findAll(spec);
        return authors.stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }
}