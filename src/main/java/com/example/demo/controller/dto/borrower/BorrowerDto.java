package com.example.demo.controller.dto.borrower;

import java.util.UUID;

public record BorrowerDto(UUID id, String name, String email) {
}
