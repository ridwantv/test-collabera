package com.example.demo.controller.dto.book;

import java.util.UUID;

public record BookDto(UUID id, String title, String author, String isbn, Boolean isAvailable) {
}
