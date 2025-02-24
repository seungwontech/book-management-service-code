package com.code.bms.book.controller;

import com.code.bms.book.entity.Author;

import java.util.List;

public record AuthorResponse(Long Id, String name, String email) {

    public static AuthorResponse from(Author author) {
        return new AuthorResponse(author.getId(), author.getName(), author.getEmail());
    }

    public static List<AuthorResponse> from(List<Author> books) {
        return books.stream().map(AuthorResponse::from).toList();
    }
}
