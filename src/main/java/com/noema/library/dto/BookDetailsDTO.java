package com.noema.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsDTO {
    private String title;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private String isbn10;
    private String isbn13;

}
