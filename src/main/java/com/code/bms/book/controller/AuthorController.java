package com.code.bms.book.controller;

import com.code.bms.book.entity.Author;
import com.code.bms.book.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/Author")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody @Valid AuthorRequest request) {
        Author author = authorService.createAuthor(request);
        return ResponseEntity.ok(AuthorResponse.from(author));
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        List<Author> result = authorService.getAllAuthors();
        return ResponseEntity.ok(AuthorResponse.from(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable("id") Long id) {
        Author author = authorService.getAuthorById(id);
        return ResponseEntity.ok(AuthorResponse.from(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable("id") Long id, @RequestBody @Valid AuthorRequest request) {
        Author author = authorService.updateAuthor(id, request);
        return ResponseEntity.ok(AuthorResponse.from(author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}