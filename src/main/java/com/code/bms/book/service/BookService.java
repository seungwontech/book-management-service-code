package com.code.bms.book.service;

import com.code.bms.book.controller.BookRequest;
import com.code.bms.book.entity.Book;
import com.code.bms.book.repository.BookRepository;
import com.code.bms.config.exception.CoreException;
import com.code.bms.config.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    public Book createBook(BookRequest request) {
        Optional<Book> optionalBook = bookRepository.findByIsbn(request.isbn());
        if (optionalBook.isPresent()) {
            throw new CoreException(ErrorType.ISBN_ALREADY_EXISTS, request.isbn());
        }

        Book book = Book.create(request.title(), request.description(), request.isbn(), request.publicationDate(), request.authorId());
        book = bookRepository.save(book);
        return book;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.BOOK_NOT_FOUND, id));
    }

    public Book updateBook(Long id, BookRequest request) {
        bookRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.BOOK_NOT_FOUND, id));

        Book book = Book.update(id, request.title(), request.description(), request.isbn(), request.publicationDate(), request.authorId());
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.delete(bookRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.BOOK_NOT_FOUND, id)));
    }
}
