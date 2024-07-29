package com.example.demo.controller.dto.borrower;

import lombok.Builder;

@Builder
public record CreateBorrowerDto(String name, String email) {
}
