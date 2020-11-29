package com.kozlova.bookshop.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kozlova.bookshop.entity.Book;
import com.kozlova.bookshop.entity.Genre;

public class DataService {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private DataService() {
        
    }    

    public static List<Book> getBooksFrom(String source) throws IOException {
        JsonNode nodes = objectMapper.readTree(Paths.get(source).toFile());
        List<Book> books = new LinkedList<>();

        for (JsonNode jsonNode : nodes) {
            if (!jsonNode.isEmpty()) {
                books.add(fromJson(jsonNode));
            }
        }

        return books;
    }

    private static Book fromJson(JsonNode node) {
        String title = node.get("title").asText();
        double price = node.get("price").asDouble();
        int pages = node.get("pages").asInt();
        String isbn = node.get("isbn").asText();
        String genre = node.get("genre").asText();

        return Book.builder().withTitle(title)
                             .withIsbn(isbn)
                             .withPages(pages)
                             .withPrice(price)
                             .withGenre(Genre.valueOf(genre))
                             .build();
    }

}
