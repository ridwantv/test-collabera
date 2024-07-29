package com.example.demo.controller;

import com.example.demo.controller.dto.borrower.BorrowerDto;
import com.example.demo.controller.dto.borrower.CreateBorrowerDto;
import com.example.demo.model.TestData;
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
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BorrowerApiTest {

    private static final Faker FAKER = new Faker();

    @LocalServerPort
    protected int port;

    @Autowired
    private BorrowerRepository bookRepository;

    @BeforeEach
    @AfterEach
    void init() {
        bookRepository.deleteAll();
    }

    @Test
    void testGetList() {
        final int numberOfItem = FAKER.number().numberBetween(3, 10);
        for (int i = 0; i<numberOfItem; i++) {
            bookRepository.saveAndFlush(TestData.BORROWER_BUILDER().build());
        }
        List<BorrowerDto> dtoList = given().port(port).when()
                .get("/borrowers")
                .then()
                .extract().body().as(new TypeRef<>() {});
        assertThat(dtoList).hasSize(numberOfItem);
    }

    @Test
    void testGet() {
        Borrower borrower = bookRepository.saveAndFlush(TestData.BORROWER_BUILDER().build());
        BorrowerDto dto = given().port(port).when()
                .get("/borrowers/" + borrower.getId())
                .then()
                .extract().body().as(new TypeRef<>() {});
        assertThat(dto.id()).isEqualTo(borrower.getId());
        assertThat(dto.name()).isEqualTo(borrower.getName());
        assertThat(dto.email()).isEqualTo(borrower.getEmail());
    }

    @Test
    void testPost() {
        CreateBorrowerDto createBorrowerDto = TestData.CREATE_BORROWER_DTO().build();
        String location = given().port(port)
                .body(createBorrowerDto)
                .contentType(ContentType.JSON)
                .when()
                .post("/borrowers")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("location");
        BorrowerDto borrowerDto = given()
                .get(URI.create(location))
                .then()
                .extract().body().as(new TypeRef<>() {});
        assertThat(borrowerDto.name()).isEqualTo(createBorrowerDto.name());
        assertThat(borrowerDto.email()).isEqualTo(createBorrowerDto.email());
    }
}
