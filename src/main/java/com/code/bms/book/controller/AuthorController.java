package com.code.bms.book.controller;

import com.code.bms.book.entity.Author;
import com.code.bms.book.service.AuthorService;
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

@Tag(name = "Author API", description = "저자 관리 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/Author")
public class AuthorController {

    private final AuthorService authorService;

    @Operation(summary = "저자 생성", description = "새로운 저자를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저자 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (이메일 형식 오류 등)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "저자 등록 충돌 (이메일 중복 등)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody @Valid AuthorRequest request) {
        Author author = authorService.createAuthor(request);
        return ResponseEntity.ok(AuthorResponse.from(author));
    }

    @Operation(summary = "모든 저자 조회", description = "등록된 모든 저자를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        List<Author> result = authorService.getAllAuthors();
        return ResponseEntity.ok(AuthorResponse.from(result));
    }

    @Operation(summary = "저자 상세 조회", description = "저자 ID로 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저자 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = AuthorResponse.class))),
            @ApiResponse(responseCode = "404", description = "저자를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable("id") Long id) {
        Author author = authorService.getAuthorById(id);
        return ResponseEntity.ok(AuthorResponse.from(author));
    }

    @Operation(summary = "저자 정보 수정", description = "저자 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저자 정보 수정 성공",
                    content = @Content(schema = @Schema(implementation = AuthorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (잘못된 이메일 형식 등)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "저자를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "이메일 중복",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable("id") Long id, @RequestBody @Valid AuthorRequest request) {
        Author author = authorService.updateAuthor(id, request);
        return ResponseEntity.ok(AuthorResponse.from(author));
    }

    @Operation(summary = "저자 삭제", description = "저자를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "저자 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "저자를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "저자 삭제 불가 (연관된 도서 존재)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}