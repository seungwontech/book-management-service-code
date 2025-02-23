package com.code.bms.book.repository;

import com.code.bms.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    Optional<Book> findByIsbn(String isbn);
}
