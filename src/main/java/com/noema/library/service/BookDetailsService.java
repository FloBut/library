package com.noema.library.service;

import com.noema.library.dto.BookDetailsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BookDetailsService {

    private final WebClient webClient;

    @Value("${books.api.key}")
    private String apiKey;

    public BookDetailsService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://www.googleapis.com/books/v1/volumes").build();
    }

    public Mono<BookDetailsDTO> fetchBookDetailsByISBN(String isbn) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/volumes")
                        .queryParam("q", "isbn:" + isbn)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Failed to fetch data from Google Books API")))
                .bodyToMono(BookDetailsDTO.class);
    }
}
