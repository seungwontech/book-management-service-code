package com.code.bms.book.entity;

import com.code.bms.config.exception.CoreException;
import com.code.bms.config.exception.ErrorType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    public static void validateIsbn(String isbn) {
        if (isbn == null || isbn.length() != 10 || !isbn.endsWith("0") || !isbn.matches("\\d{10}")) {
            throw new CoreException(ErrorType.INVALID_ISBN_FORMAT, isbn);
        }

        int countryCode = Integer.parseInt(isbn.substring(0, 2));
        if (countryCode < 10 || countryCode > 90) {
            throw new CoreException(ErrorType.INVALID_ISBN_FORMAT, isbn);
        }
    }

    public static Book create(String title, String description, String isbn, LocalDate publicationDate, Long authorId) {
        validateIsbn(isbn);
        return new Book(null, title, description, isbn, publicationDate, authorId);
    }

    public static Book update(Long id, String title, String description, String isbn, LocalDate publicationDate, Long authorId) {
        validateIsbn(isbn);
        return new Book(id, title, description, isbn, publicationDate, authorId);
    }
}
