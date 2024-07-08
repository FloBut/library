package com.noema.library.dto.mapper;



import com.noema.library.dto.AuthorRequestDTO;
import com.noema.library.dto.AuthorResponseDTO;
import com.noema.library.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorResponseDTO toDto(Author author) {
        if (author == null) {
            return null;
        }

        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO();
        authorResponseDTO.setId(author.getId());
        authorResponseDTO.setName(author.getName());
        authorResponseDTO.setBio(author.getBio());
        return authorResponseDTO;
    }

    public Author toEntity(AuthorRequestDTO authorRequestDTO) {
        if (authorRequestDTO == null) {
            return null;
        }

        Author author = new Author();
        author.setName(authorRequestDTO.getName());
        author.setBio(authorRequestDTO.getBio());
        return author;
    }
}
