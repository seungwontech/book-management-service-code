package com.code.bms.book.service;

import com.code.bms.book.controller.AuthorRequest;
import com.code.bms.book.entity.Author;
import com.code.bms.book.entity.Book;
import com.code.bms.book.repository.AuthorRepository;
import com.code.bms.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    public Author createAuthor(AuthorRequest request) {
        Optional<Author> optionalAuthor = authorRepository.findByEmail(request.email());
        if (optionalAuthor.isPresent()) {
            throw new IllegalArgumentException("email already exists");
        }
        Author author = Author.create(request.name(), request.email());
        author = authorRepository.save(author);

        return author;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("author not found"));
    }

    public Author updateAuthor(Long id, AuthorRequest request) {
        authorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("author not found"));

        Author author = Author.update(id, request.name(), request.email());
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        // 연관된 도서가 있는지 확인하고 삭제를 막는 로직
        if (bookRepository.existsByAuthorId(id)) {
            throw new IllegalStateException("해당 저자와 연관된 도서가 있어 삭제할 수 없습니다.");
        }
        authorRepository.deleteById(id);
    }
}
