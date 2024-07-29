package com.example.demo.controller;

import com.example.demo.controller.dto.bookborrowing.BookBorrowingDto;
import com.example.demo.controller.dto.bookborrowing.CreateBookBorrowingDto;
import com.example.demo.model.bookborrowing.BookBorrowing;
import com.example.demo.service.BookBorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/book-borrowings")
public class BookBorrowingApi {

    @Autowired
    private BookBorrowingService bookBorrowingService;

    @GetMapping
    public ResponseEntity<List<BookBorrowingDto>> getBookBorrowingList() {
        return ResponseEntity.ok(bookBorrowingService.getBookBorrowingList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookBorrowingDto> getBookBorrowing(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(bookBorrowingService.getBookBorrowing(id));
    }

    @PostMapping
    public ResponseEntity<Void> borrowBook(@RequestBody CreateBookBorrowingDto dto) {
        BookBorrowing bookBorrowing = bookBorrowingService.borrowBook(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{bookBorrowingId}")
                .buildAndExpand(bookBorrowing.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> returnBook(@PathVariable("id") UUID id) {
        bookBorrowingService.returnBook(id);
        return ResponseEntity.noContent().build();
    }
}
