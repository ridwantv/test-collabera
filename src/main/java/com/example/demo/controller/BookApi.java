package com.example.demo.controller;

import com.example.demo.controller.dto.book.BookDto;
import com.example.demo.controller.dto.book.CreateBookDto;
import com.example.demo.model.book.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookApi {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getBookList() {
        return ResponseEntity.ok(bookService.getBookList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @PostMapping
    public ResponseEntity<Void> addBook(@RequestBody CreateBookDto dto) {
        Book book = bookService.addBook(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{bookId}")
                .buildAndExpand(book.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
