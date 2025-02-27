# 도서 관리 시스템

이 프로젝트는 도서 정보를 관리하는 시스템입니다. 도서, 저자의 CRUD기능을 제공합니다.

## 프로젝트 실행 방법

### 1. 환경 설정

Spring Boot 3.4.3 버전과 Java 17을 사용하여 개발했습니다. 프로젝트를 실행하려면 아래의 요구사항을 충족해야 합니다.

- `JDK17` 이상
- `Gradle` 설치

### 2. 프로잭트 실행

1. 먼저 프로젝트를 클론한 후, Gradle 빌드를 실행하여 필요한 의존성을 다운로드
   ```
   ./gradlew build
   ```
2. 애플리케이션을 실행합니다.
   ```
   ./gradlew bootRun
   ```
- 애플리케이션이 실행되면 기본적으로 localhost:8080에서 서비스가 제공됩니다.
   
   > macOS/Linux: ./gradlew (실행 파일 앞에 ./을 붙여야 합니다)  
   > Windows: gradlew (실행 파일 앞에 ./을 붙이지 않습니다)

# Author API
**저자 관리 API - 새로운 저자 등록, 조회, 수정, 삭제 기능 제공**

## API 목록

| 기능         | 메서드  | URL                         |
|-------------|--------|----------------------------|
| 저자 생성   | `POST` | `/api/v1/Author`           |
| 모든 저자 조회 | `GET`  | `/api/v1/Author`           |
| 저자 상세 조회 | `GET`  | `/api/v1/Author/{id}`     |
| 저자 정보 수정 | `PUT`  | `/api/v1/Author/{id}`     |
| 저자 삭제   | `DELETE` | `/api/v1/Author/{id}`     |

---

## 1. 저자 생성
### ✅ 요청
- **URL:** `/api/v1/Author`
- **Method:** `POST`
- **Content-Type:** `application/json`
- **Request Body:**
```json
{
  "name": "홍길동",
  "email": "hong@example.com"
}
```

### ✅ 응답
- **Status:** `200 OK`
- **Response Body:**
```json
{
  "id": 1,
  "name": "홍길동",
  "email": "hong@example.com"
}
```

### 에러 응답
| 상태 코드 | 설명                      | 예시 Response                        |
|----------|--------------------------|--------------------------------------|
| `400`    | 잘못된 요청 (이메일 오류 등) | `{"message": "잘못된 이메일 형식입니다."}` |
| `409`    | 이메일 중복                | `{"message": "이미 등록된 이메일입니다."}` |

---

## 2. 모든 저자 조회
### ✅ 요청
- **URL:** `/api/v1/Author`
- **Method:** `GET`

### ✅ 응답
- **Status:** `200 OK`
- **Response Body:**
```json
[
  {
    "id": 1,
    "name": "홍길동",
    "email": "hong@example.com"
  },
  {
    "id": 2,
    "name": "이몽룡",
    "email": "lee@example.com"
  }
]
```

---

## 3. 저자 상세 조회
### ✅ 요청
- **URL:** `/api/v1/Author/{id}`
- **Method:** `GET`
- **Path Variables:**
   - `id`: 조회할 저자의 ID (필수)

### ✅ 응답
- **Status:** `200 OK`
- **Response Body:**
```json
{
  "id": 1,
  "name": "홍길동",
  "email": "hong@example.com"
}
```

### 에러 응답
| 상태 코드 | 설명          | 예시 Response                          |
|----------|------------|--------------------------------------|
| `404`    | 저자를 찾을 수 없음 | `{"message": "존재하지 않는 저자입니다."}` |

---

## 4. 저자 정보 수정
### ✅ 요청
- **URL:** `/api/v1/Author/{id}`
- **Method:** `PUT`
- **Path Variables:**
   - `id`: 수정할 저자의 ID (필수)
- **Request Body:**
```json
{
  "name": "홍길동",
  "email": "new_email@example.com"
}
```

### ✅ 응답
- **Status:** `200 OK`
- **Response Body:**
```json
{
  "id": 1,
  "name": "홍길동",
  "email": "new_email@example.com"
}
```

### 에러 응답
| 상태 코드 | 설명                      | 예시 Response                         |
|----------|--------------------------|--------------------------------------|
| `400`    | 잘못된 요청 (이메일 오류 등) | `{"message": "잘못된 이메일 형식입니다."}` |
| `404`    | 저자를 찾을 수 없음        | `{"message": "존재하지 않는 저자입니다."}` |
| `409`    | 이메일 중복                | `{"message": "이미 등록된 이메일입니다."}` |

---

## 5. 저자 삭제
### ✅ 요청
- **URL:** `/api/v1/Author/{id}`
- **Method:** `DELETE`
- **Path Variables:**
   - `id`: 삭제할 저자의 ID (필수)

### ✅ 응답
- **Status:** `204 No Content`

### 에러 응답
| 상태 코드 | 설명                          | 예시 Response                                |
|----------|------------------------------|--------------------------------------------|
| `404`    | 저자를 찾을 수 없음            | `{"message": "존재하지 않는 저자입니다."}` |
| `409`    | 저자 삭제 불가 (연관된 도서 존재) | `{"message": "해당 저자와 관련된 도서가 존재하여 삭제할 수 없습니다."}` |

---
# Book API
**도서 관리 API - 도서 등록, 조회, 수정, 삭제 기능 제공**

## API 목록

| 기능         | 메서드  | URL                    |
|-------------|--------|------------------------|
| 도서 생성   | `POST`  | `/api/v1/books`       |
| 모든 도서 조회 | `GET`   | `/api/v1/books`       |
| 도서 상세 조회 | `GET`   | `/api/v1/books/{id}`  |
| 도서 정보 수정 | `PUT`   | `/api/v1/books/{id}`  |
| 도서 삭제   | `DELETE` | `/api/v1/books/{id}`  |

---

## 1. 도서 생성
### ✅ 요청
- **URL:** `/api/v1/books`
- **Method:** `POST`
- **Content-Type:** `application/json`
- **Request Body:**
```json
{
  "title": "자바의 정석",
  "author": "남궁성",
  "isbn": "1234567890"
}
```

### ✅ 응답
- **Status:** `200 OK`
- **Response Body:**
```json
{
  "id": 1,
  "title": "자바의 정석",
  "author": "남궁성",
  "isbn": "1234567890"
}
```

### 에러 응답
| 상태 코드 | 설명                      | 예시 Response                         |
|----------|--------------------------|--------------------------------------|
| `400`    | 잘못된 요청 (ISBN 형식 오류) | `{"message": "잘못된 ISBN 형식입니다."}` |
| `409`    | ISBN 중복                  | `{"message": "이미 등록된 ISBN입니다."}` |

---

## 2. 모든 도서 조회
### ✅ 요청
- **URL:** `/api/v1/books`
- **Method:** `GET`

### ✅ 응답
- **Status:** `200 OK`
- **Response Body:**
```json
[
  {
    "id": 1,
    "title": "자바의 정석",
    "author": "남궁성",
    "isbn": "1234567890"
  },
  {
    "id": 2,
    "title": "Effective Java",
    "author": "Joshua Bloch",
    "isbn": "1234567890"
  }
]
```

---

## 3. 도서 상세 조회
### ✅ 요청
- **URL:** `/api/v1/books/{id}`
- **Method:** `GET`
- **Path Variables:**
   - `id`: 조회할 도서의 ID (필수)

### ✅ 응답
- **Status:** `200 OK`
- **Response Body:**
```json
{
  "id": 1,
  "title": "자바의 정석",
  "author": "남궁성",
  "isbn": "1234567890"
}
```

### 에러 응답
| 상태 코드 | 설명           | 예시 Response                        |
|----------|-------------|------------------------------------|
| `404`    | 도서를 찾을 수 없음 | `{"message": "존재하지 않는 도서입니다."}` |

---

## 4. 도서 정보 수정
### ✅ 요청
- **URL:** `/api/v1/books/{id}`
- **Method:** `PUT`
- **Path Variables:**
   - `id`: 수정할 도서의 ID (필수)
- **Request Body:**
```json
{
  "title": "자바의 정석 (개정판)",
  "author": "남궁성",
  "isbn": "1234567890"
}
```

### ✅ 응답
- **Status:** `200 OK`
- **Response Body:**
```json
{
  "id": 1,
  "title": "자바의 정석 (개정판)",
  "author": "남궁성",
  "isbn": "1234567890"
}
```

### 에러 응답
| 상태 코드 | 설명                      | 예시 Response                         |
|----------|--------------------------|--------------------------------------|
| `400`    | 잘못된 요청 (ISBN 형식 오류) | `{"message": "잘못된 ISBN 형식입니다."}` |
| `404`    | 도서를 찾을 수 없음        | `{"message": "존재하지 않는 도서입니다."}` |
| `409`    | ISBN 중복                  | `{"message": "이미 등록된 ISBN입니다."}` |

---

## 5. 도서 삭제
### ✅ 요청
- **URL:** `/api/v1/books/{id}`
- **Method:** `DELETE`
- **Path Variables:**
   - `id`: 삭제할 도서의 ID (필수)

### ✅ 응답
- **Status:** `204 No Content`

### 에러 응답
| 상태 코드 | 설명           | 예시 Response                        |
|----------|-------------|------------------------------------|
| `404`    | 도서를 찾을 수 없음 | `{"message": "존재하지 않는 도서입니다."}` |

---

## Swagger 문서 접근 방법

- Swagger UI URL: http://localhost:8080/swagger-ui.html

## 기타 주의 사항

### 데이터베이스 구성

> H2 데이터베이스를 사용하고 있습니다.  
> 다른 데이터베이스를 사용하려면 application.properties에서 설정을 변경할 수 있습니다.

- DB URL: jdbc:h2:~/test
- Hibernate DDL 설정: ddl-auto: create
### H2 콘솔

- H2 콘솔 URL: http://localhost:8080/h2-console
- 이 콘솔을 통해 H2 데이터베이스를 웹 브라우저에서 직접 관리할 수 있습니다.
- 로그인 시 사용할 JDBC URL: jdbc:h2:~/test
- User Name: sa
- Password: (null)