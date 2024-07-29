package com.example.demo.service;

import com.example.demo.controller.dto.book.BookDto;
import com.example.demo.controller.dto.book.CreateBookDto;
import com.example.demo.controller.mapper.BookMapper;
import com.example.demo.exception.AddBookException;
import com.example.demo.exception.NonExistingBookException;
import com.example.demo.model.book.Book;
import com.example.demo.model.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<BookDto> getBookList() {
        return bookMapper.map(bookRepository.findAll());
    }

    public BookDto getBook(UUID id) {
        return bookMapper.map(bookRepository.findById(id).orElseThrow(NonExistingBookException::of));
    }

    @Transactional
    public Book addBook(CreateBookDto dto) {
        String isbn = dto.isbn();
        Book existingBook = bookRepository.findByIsbn(isbn)
                .stream()
                .findFirst()
                .orElse(null);
        if (existingBook != null) {
            if (!dto.author().equals(existingBook.getAuthor()) || !dto.title().equals(existingBook.getTitle())) {
                throw AddBookException.of();
            }
        }
        return bookRepository.saveAndFlush(bookMapper.map(dto));
    }
}
