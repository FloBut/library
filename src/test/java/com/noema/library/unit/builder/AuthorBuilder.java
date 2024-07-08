package com.noema.library.unit.builder;

import com.noema.library.model.Author;

public class AuthorBuilder {
    private Long id;
    private String name = "Default Name";
    private String bio = "Default Bio";

    public AuthorBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AuthorBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public AuthorBuilder withBio(String bio) {
        this.bio = bio;
        return this;
    }

    public Author build() {
        Author author = new Author();
        author.setId(id);
        author.setName(name);
        author.setBio(bio);
        return author;
    }
}
