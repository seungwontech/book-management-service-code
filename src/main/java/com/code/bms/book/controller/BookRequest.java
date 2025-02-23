package com.code.bms.book.controller;

import java.time.LocalDate;

public record BookRequest(String title, String description, String isbn, LocalDate publicationDate, Long authorId) {
}
