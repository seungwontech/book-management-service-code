package com.code.bms.book.repository;

import com.code.bms.book.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByEmail(String email);
}
