package com.code.bms.unit;

import com.code.bms.book.controller.AuthorRequest;
import com.code.bms.book.entity.Author;
import com.code.bms.book.repository.AuthorRepository;
import com.code.bms.book.repository.BookRepository;
import com.code.bms.book.service.AuthorService;
import com.code.bms.config.exception.CoreException;
import com.code.bms.config.exception.ErrorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;
    private AuthorRequest authorRequest;

    @BeforeEach
    void setUp() {
        author = Author.update(1L, "John Doe", "john@example.com");
        authorRequest = new AuthorRequest("John Doe", "john@example.com");
    }

    @Test
    void 저자등록_성공() {
        // given
        when(authorRepository.findByEmail(authorRequest.email())).thenReturn(Optional.empty());
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        // when
        Author result = authorService.createAuthor(authorRequest);

        // then
        assertThat(result).isEqualTo(author);
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void 책등록_중복된이메일_예외발생() {
        // given
        when(authorRepository.findByEmail(authorRequest.email())).thenReturn(Optional.of(author));

        // when & then
        assertThatThrownBy(() -> authorService.createAuthor(authorRequest))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.EMAIL_ALREADY_EXISTS.getMessage());
    }

    @Test
    void 모든저자조회_성공() {
        // given
        when(authorRepository.findAll()).thenReturn(List.of(author));

        // when
        List<Author> authors = authorService.getAllAuthors();

        // then
        assertThat(authors).hasSize(1);
        assertThat(authors.get(0)).isEqualTo(author);
    }

    @Test
    void 저자상세조회_성공() {
        // given
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        // when
        Author result = authorService.getAuthorById(1L);

        // then
        assertThat(result).isEqualTo(author);
    }

    @Test
    void 저자상세조회_id없어서예외발생() {
        // given
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> authorService.getAuthorById(1L))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.AUTHOR_NOT_FOUND.getMessage());
    }

    @Test
    void 저자수정_성공() {
        // given
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        // when
        Author updatedAuthor = authorService.updateAuthor(1L, authorRequest);

        // then
        assertThat(updatedAuthor).isEqualTo(author);
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void 저자수정_id없어서예외발생() {
        // given
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> authorService.updateAuthor(1L, authorRequest))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.AUTHOR_NOT_FOUND.getMessage());
    }

    @Test
    void 저자삭제_성공() {
        // given
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.existsByAuthorId(1L)).thenReturn(false);

        // when
        authorService.deleteAuthor(1L);

        // then
        verify(authorRepository, times(1)).delete(author);
    }

    @Test
    void 저자삭제_연관된도서로인한예외발생() {
        // given
        when(bookRepository.existsByAuthorId(1L)).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> authorService.deleteAuthor(1L))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.AUTHOR_DELETE_CONFLICT.getMessage());
    }
}
