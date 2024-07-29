package com.example.demo.model;

import com.example.demo.controller.dto.book.CreateBookDto;
import com.example.demo.controller.dto.borrower.CreateBorrowerDto;
import com.example.demo.model.book.Book;
import com.example.demo.model.borrower.Borrower;
import net.datafaker.Faker;

import java.util.UUID;

public class TestData {
    private static final Faker FAKER = new Faker();

    public static CreateBookDto.CreateBookDtoBuilder CREATE_BOOK_DTO_BUILDER() {
        return CreateBookDto.builder()
                .title(FAKER.book().title())
                .author(FAKER.book().author())
                .isbn(FAKER.code().isbn10());
    }

    public static CreateBorrowerDto.CreateBorrowerDtoBuilder CREATE_BORROWER_DTO() {
        return CreateBorrowerDto.builder()
                .name(FAKER.name().fullName())
                .email(FAKER.internet().emailAddress());
    }

    public static Book.BookBuilder BOOK_BUILDER() {
        return Book.builder()
                .id(UUID.randomUUID())
                .title(FAKER.book().title())
                .author(FAKER.book().author())
                .isbn(FAKER.code().isbn10())
                .isAvailable(true);
    }

    public static Borrower.BorrowerBuilder BORROWER_BUILDER() {
        return Borrower.builder()
                .id(UUID.randomUUID())
                .name(FAKER.name().fullName())
                .email(FAKER.internet().emailAddress());
    }
}
