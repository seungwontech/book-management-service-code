package com.code.bms.book.controller;

import com.code.bms.book.entity.Book;
import com.code.bms.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    // 도서 생성
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody @Valid BookRequest request) {
        Book book = bookService.createBook(request);
        return ResponseEntity.ok(BookResponse.from(book));
    }

    // 도서 조회
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<Book> result = bookService.getAllBooks();
        return ResponseEntity.ok(BookResponse.from(result));
    }

    // 도서 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(BookResponse.from(book));
    }

    // 도서 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable("id") Long id, @RequestBody @Valid BookRequest request) {
        Book book = bookService.updateBook(id, request);
        return ResponseEntity.ok(BookResponse.from(book));
    }

    // 도서 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
