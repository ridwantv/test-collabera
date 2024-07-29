package com.example.demo.controller;

import com.example.demo.controller.dto.book.BookDto;
import com.example.demo.controller.dto.book.CreateBookDto;
import com.example.demo.model.TestData;
import com.example.demo.model.book.Book;
import com.example.demo.model.book.BookRepository;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookApiTest {

    private static final Faker FAKER = new Faker();

    @LocalServerPort
    protected int port;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    @AfterEach
    void init() {
        bookRepository.deleteAll();
    }

    @Test
    void testGetList() {
        final int numberOfItem = FAKER.number().numberBetween(3, 10);
        for (int i = 0; i<numberOfItem; i++) {
            bookRepository.saveAndFlush(TestData.BOOK_BUILDER().build());
        }
        List<BookDto> dtoList = given().port(port).when()
                .get("/books")
                .then()
                .extract().body().as(new TypeRef<>() {});
        assertThat(dtoList).hasSize(numberOfItem);
    }

    @Test
    void testGet() {
        Book book = bookRepository.saveAndFlush(TestData.BOOK_BUILDER().build());
        BookDto dto = given().port(port).when()
                .get("/books/" + book.getId())
                .then()
                .extract().body().as(new TypeRef<>() {});
        assertThat(dto.id()).isEqualTo(book.getId());
        assertThat(dto.title()).isEqualTo(book.getTitle());
        assertThat(dto.author()).isEqualTo(book.getAuthor());
        assertThat(dto.isbn()).isEqualTo(book.getIsbn());
        assertThat(dto.isAvailable()).isEqualTo(book.getIsAvailable());
    }

    @Test
    void testPost() {
        CreateBookDto createBookDto = TestData.CREATE_BOOK_DTO_BUILDER().build();
        String location = given().port(port)
                .body(createBookDto)
                .contentType(ContentType.JSON)
                .when()
                .post("/books")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("location");
        BookDto bookDto = given()
                .get(URI.create(location))
                .then()
                .extract().body().as(new TypeRef<>() {});
        assertThat(bookDto.title()).isEqualTo(createBookDto.title());
        assertThat(bookDto.author()).isEqualTo(createBookDto.author());
        assertThat(bookDto.isbn()).isEqualTo(createBookDto.isbn());
    }

    @Test
    void testPostInvalidBook() {
        Book book = bookRepository.saveAndFlush(TestData.BOOK_BUILDER().build());
        CreateBookDto createBookDto = CreateBookDto
                .builder()
                .isbn(book.getIsbn())
                .title(book.getTitle() + " " + FAKER.book().title())
                .author(book.getAuthor() + " " + FAKER.name().lastName())
                .build();
        given().port(port)
                .body(createBookDto)
                .contentType(ContentType.JSON)
                .when()
                .post("/books")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
