package com.example.demo.controller.dto.bookborrowing;

import java.util.UUID;

public record CreateBookBorrowingDto(
        UUID bookId,
        UUID borrowerId) {
}
