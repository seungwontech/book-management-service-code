package com.code.bms.unit;

import com.code.bms.book.controller.BookRequest;
import com.code.bms.book.entity.Book;
import com.code.bms.book.repository.BookRepository;
import com.code.bms.book.service.BookService;
import com.code.bms.config.exception.CoreException;
import com.code.bms.config.exception.ErrorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private BookRequest validBookRequest;
    private Book book;

    @BeforeEach
    void setUp() {
        validBookRequest = new BookRequest("Title", "Description", "1001234560", LocalDate.of(2022, 12, 15), 1L);
        book = Book.update(1L, validBookRequest.title(), validBookRequest.description(), validBookRequest.isbn(), validBookRequest.publicationDate(), validBookRequest.authorId());
    }

    @Test
    void 책등록_성공() {
        // Given
        when(bookRepository.findByIsbn(validBookRequest.isbn())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        Book createdBook = bookService.createBook(validBookRequest);

        // Then
        assertNotNull(createdBook);
        assertEquals(validBookRequest.title(), createdBook.getTitle());
        assertEquals(validBookRequest.isbn(), createdBook.getIsbn());
        assertEquals(validBookRequest.publicationDate(), createdBook.getPublicationDate());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void 책등록_ISBN존재_예외발생() {
        // Given
        when(bookRepository.findByIsbn(validBookRequest.isbn())).thenReturn(Optional.of(book));

        // When & Then
        CoreException exception = assertThrows(CoreException.class, () -> bookService.createBook(validBookRequest));
        assertEquals(ErrorType.ISBN_ALREADY_EXISTS, exception.getErrorType());
    }

    @Test
    void 책상세조회_성공() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // When
        Book foundBook = bookService.getBookById(1L);

        // Then
        assertNotNull(foundBook);
        assertEquals(1L, foundBook.getId());
        assertEquals(book.getTitle(), foundBook.getTitle());
    }

    @Test
    void 책상세조회_없어서예외발생() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        CoreException exception = assertThrows(CoreException.class, () -> bookService.getBookById(1L));
        assertEquals(ErrorType.BOOK_NOT_FOUND, exception.getErrorType());
    }

    @Test
    void 책수정_성공() {
        // Given
        BookRequest updatedRequest = new BookRequest("Updated Title", "Updated Description", "9007654320", LocalDate.of(2023, 1, 1), 1L);
        Book updatedBook = Book.update(1L, updatedRequest.title(), updatedRequest.description(), updatedRequest.isbn(), updatedRequest.publicationDate(), updatedRequest.authorId());

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // When
        Book result = bookService.updateBook(1L, updatedRequest);

        // Then
        assertNotNull(result);
        assertEquals(updatedRequest.title(), result.getTitle());
        assertEquals(updatedRequest.publicationDate(), result.getPublicationDate());
    }

    @Test
    void 책수정_id없어서예외발생() {
        // Given
        BookRequest updatedRequest = new BookRequest("Updated Title", "Updated Description", "9781234567890", LocalDate.of(2023, 1, 1), 1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        CoreException exception = assertThrows(CoreException.class, () -> bookService.updateBook(1L, updatedRequest));
        assertEquals(ErrorType.BOOK_NOT_FOUND, exception.getErrorType());
    }

    @Test
    void 책삭제_성공() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // When
        bookService.deleteBook(1L);

        // Then
        verify(bookRepository).delete(any(Book.class));
    }

    @Test
    void 책삭제_id없어서예외발생() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        CoreException exception = assertThrows(CoreException.class, () -> bookService.deleteBook(1L));
        assertEquals(ErrorType.BOOK_NOT_FOUND, exception.getErrorType());
    }

    @Test
    void 유효한ISBN_성공() {
        // Given
        String validIsbn = "1234567890";

        // When & Then
        assertDoesNotThrow(() -> Book.validateIsbn(validIsbn));
    }
}
