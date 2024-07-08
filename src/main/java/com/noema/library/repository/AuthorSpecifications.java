package com.noema.library.repository;

import com.noema.library.model.Author;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class AuthorSpecifications {
    public static Specification<Author> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Author> hasBioKeyword(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("bio")), "%" + keyword.toLowerCase() + "%");
    }

}
