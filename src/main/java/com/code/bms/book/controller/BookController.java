package com.code.bms.book.controller;

import com.code.bms.book.entity.Book;
import com.code.bms.book.service.BookService;
import com.code.bms.config.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Book API", description = "도서 관리 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "도서 생성", description = "새로운 도서를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "도서 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (잘못된 ISBN 형식 등)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "ISBN 중복 등",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody @Valid BookRequest request) {
        Book book = bookService.createBook(request);
        return ResponseEntity.ok(BookResponse.from(book));
    }

    @Operation(summary = "모든 도서 조회", description = "등록된 모든 도서를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<Book> result = bookService.getAllBooks();
        return ResponseEntity.ok(BookResponse.from(result));
    }

    @Operation(summary = "도서 상세 조회", description = "도서 ID로 도서를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "도서 조회 성공"),
            @ApiResponse(responseCode = "404", description = "도서를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(BookResponse.from(book));
    }

    @Operation(summary = "도서 정보 수정", description = "도서 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "도서 정보 수정 성공",
                    content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (잘못된 ISBN 형식 등)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "도서를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "ISBN 중복",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable("id") Long id, @RequestBody @Valid BookRequest request) {
        Book book = bookService.updateBook(id, request);
        return ResponseEntity.ok(BookResponse.from(book));
    }

    @Operation(summary = "도서 삭제", description = "도서를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "도서 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "도서를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
