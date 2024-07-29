package com.example.demo.controller;

import com.example.demo.controller.dto.bookborrowing.BookBorrowingDto;
import com.example.demo.controller.dto.bookborrowing.CreateBookBorrowingDto;
import com.example.demo.model.TestData;
import com.example.demo.model.book.Book;
import com.example.demo.model.book.BookRepository;
import com.example.demo.model.bookborrowing.BookBorrowing;
import com.example.demo.model.bookborrowing.BookBorrowingRepository;
import com.example.demo.model.borrower.Borrower;
import com.example.demo.model.borrower.BorrowerRepository;
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
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookBorrowingApiTest {

    private static final Faker FAKER = new Faker();

    @LocalServerPort
    protected int port;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private BookBorrowingRepository bookBorrowingRepository;

    @BeforeEach
    @AfterEach
    void init() {
        bookBorrowingRepository.deleteAll();
        bookRepository.deleteAll();
        borrowerRepository.deleteAll();
    }

    @Test
    void testGetList() {
        final int numberOfItem = FAKER.number().numberBetween(3, 10);
        for (int i = 0; i<numberOfItem; i++) {
            createBookBorrowing(false);
        }
        List<BookBorrowingDto> dtoList = given().port(port).when()
                .get("/book-borrowings")
                .then()
                .extract().body().as(new TypeRef<>() {});
        assertThat(dtoList).hasSize(numberOfItem);
    }

    @Test
    void testGet() {
        BookBorrowing bookBorrowing = createBookBorrowing(false);
        BookBorrowingDto dto = given().port(port).when()
                .get("/book-borrowings/" + bookBorrowing.getId())
                .then()
                .extract()
                .body().as(new TypeRef<>() {});
        assertThat(dto.book().id()).isEqualTo(bookBorrowing.getBook().getId());
        assertThat(dto.borrower().id()).isEqualTo(bookBorrowing.getBorrower().getId());
        assertThat(dto.borrowDate()).isEqualTo(bookBorrowing.getBorrowDate());
        assertThat(dto.returnDate()).isEqualTo(bookBorrowing.getReturnDate());
    }

    @Test
    void testPost() {
        Book book = bookRepository.saveAndFlush(TestData.BOOK_BUILDER().build());
        Borrower borrower = borrowerRepository.saveAndFlush(TestData.BORROWER_BUILDER().build());
        CreateBookBorrowingDto createBookBorrowingDto = new CreateBookBorrowingDto(book.getId(), borrower.getId());
        String location = given().port(port)
                .body(createBookBorrowingDto)
                .contentType(ContentType.JSON)
                .when()
                .post("/book-borrowings")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("location");
        BookBorrowingDto bookBorrowingDto = given()
                .get(URI.create(location))
                .then()
                .extract().body().as(new TypeRef<>() {});
        assertThat(bookBorrowingDto.book().id()).isEqualTo(createBookBorrowingDto.bookId());
        assertThat(bookBorrowingDto.borrower().id()).isEqualTo(createBookBorrowingDto.borrowerId());
        book = bookRepository.findById(bookBorrowingDto.book().id()).orElseThrow();
        assertThat(book.getIsAvailable()).isFalse();
    }

    @Test
    void testDelete() {
        BookBorrowing bookBorrowing = createBookBorrowing(true);
        given().port(port).when()
                .delete("/book-borrowings/" + bookBorrowing.getId())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        BookBorrowing afterBookReturned = bookBorrowingRepository.findById(bookBorrowing.getId()).orElseThrow();
        Book book = bookRepository.findById(bookBorrowing.getBook().getId()).orElseThrow();

        assertThat(afterBookReturned.getId()).isEqualTo(bookBorrowing.getId());
        assertThat(afterBookReturned.getReturnDate()).isNotNull();
        assertThat(book.getIsAvailable()).isTrue();
    }

    private BookBorrowing createBookBorrowing(Boolean isBorrowed) {
        Book book = bookRepository.saveAndFlush(TestData.BOOK_BUILDER().isAvailable( ! isBorrowed ).build());
        Borrower borrower = borrowerRepository.saveAndFlush(TestData.BORROWER_BUILDER().build());
        return bookBorrowingRepository.saveAndFlush(
                BookBorrowing.builder()
                        .id(UUID.randomUUID())
                        .book(book)
                        .borrower(borrower)
                        .borrowDate(LocalDate.now())
                        .returnDate(null)
                        .build());
    }
}
