package com.code.bms.unit;

import com.code.bms.config.exception.CoreException;
import com.code.bms.config.exception.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IsbnValidatorTest {

    @Test
    @DisplayName("유효한 ISBN-10 - 정상 케이스")
    void validIsbn10() {
        assertDoesNotThrow(() -> validateIsbn("1001234560"));
        assertDoesNotThrow(() -> validateIsbn("1234567890"));
        assertDoesNotThrow(() -> validateIsbn("9007654320"));
    }

    @Test
    @DisplayName("유효하지 않은 ISBN-10 - 100~900 범위 벗어남")
    void invalidIsbn10_outOfRange() {
        assertThrows(CoreException.class, () -> validateIsbn("0991234560"));
        assertThrows(CoreException.class, () -> validateIsbn("9011234560"));
    }

    @Test
    @DisplayName("유효하지 않은 ISBN-10 - 숫자 외 문자 포함")
    void invalidIsbn10_containsNonDigit() {
        assertThrows(CoreException.class, () -> validateIsbn("10012X4560"));
    }

    @Test
    @DisplayName("유효하지 않은 ISBN-10 - 체크 디지트가 0이 아님")
    void invalidIsbn10_wrongCheckDigit() {
        assertThrows(CoreException.class, () -> validateIsbn("1001234567"));
    }

    @Test
    @DisplayName("유효하지 않은 ISBN-10 - null 또는 빈 문자열")
    void invalidIsbn10_nullOrEmpty() {
        assertThrows(CoreException.class, () -> validateIsbn(null));
        assertThrows(CoreException.class, () -> validateIsbn(""));
    }

    // 유효성 검사 메서드
    private void validateIsbn(String isbn) {
        if (isbn == null || !isbn.matches("^(?:[1-8]\\d{2}|900)\\d{4,7}\\d{1,3}0$")) {
            throw new CoreException(ErrorType.INVALID_ISBN_FORMAT, isbn);
        }
    }
}
