package com.example.demo.controller.dto.bookborrowing;

import com.example.demo.controller.dto.book.BookDto;
import com.example.demo.controller.dto.borrower.BorrowerDto;

import java.time.LocalDate;
import java.util.UUID;

public record BookBorrowingDto(
        UUID id,
        BookDto book,
        BorrowerDto borrower,
        LocalDate borrowDate,
        LocalDate returnDate) {
}
