package com.example.demo.controller.dto.book;

import lombok.Builder;

@Builder
public record CreateBookDto(String title, String author, String isbn) {
}
