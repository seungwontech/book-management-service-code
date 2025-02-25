package com.code.bms.book.controller;

import com.code.bms.book.entity.Book;

import java.time.LocalDate;
import java.util.List;

public record BookResponse(
        Long id,
        String title,
        String description,
        String isbn,
        LocalDate publicationDate,
        Long authorId
) {

    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId()
                , book.getTitle()
                , book.getDescription()
                , book.getIsbn()
                , book.getPublicationDate()
                , book.getAuthorId()
        );
    }

    public static List<BookResponse> from(List<Book> books) {
        return books.stream()
                .map(BookResponse::from)
                .toList();
    }
}
