package com.example.demo.service;

import com.example.demo.controller.dto.bookborrowing.BookBorrowingDto;
import com.example.demo.controller.dto.bookborrowing.CreateBookBorrowingDto;
import com.example.demo.controller.mapper.BookBorrowingMapper;
import com.example.demo.exception.BorrowBookException;
import com.example.demo.model.book.Book;
import com.example.demo.model.book.BookRepository;
import com.example.demo.model.bookborrowing.BookBorrowing;
import com.example.demo.model.bookborrowing.BookBorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BookBorrowingService {

    @Autowired
    private BookBorrowingRepository bookBorrowingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookBorrowingMapper bookBorrowingMapper;

    public List<BookBorrowingDto> getBookBorrowingList() {
        return bookBorrowingMapper.map(bookBorrowingRepository.findByReturnDateIsNull());
    }

    public BookBorrowingDto getBookBorrowing(UUID id) {
        return bookBorrowingMapper.map(bookBorrowingRepository.findByIdAndReturnDateIsNull(id).orElseThrow());
    }

    @Transactional
    public BookBorrowing borrowBook(CreateBookBorrowingDto dto) {
        Book book = bookRepository.findById(dto.bookId()).orElseThrow();
        if (!book.getIsAvailable()) {
            throw BorrowBookException.of();
        }
        BookBorrowing bookBorrowing = bookBorrowingRepository.saveAndFlush(bookBorrowingMapper.map(dto));

        book.setIsAvailable(false);
        bookRepository.saveAndFlush(book);
        return  bookBorrowing;
    }

    @Transactional
    public void returnBook(UUID id) {
        BookBorrowing bookBorrowing = bookBorrowingRepository.findByIdAndReturnDateIsNull(id).orElseThrow();
        bookBorrowing.setReturnDate(LocalDate.now());
        bookBorrowingRepository.saveAndFlush(bookBorrowing);
        Book book = bookBorrowing.getBook();
        book.setIsAvailable(true);
        bookRepository.saveAndFlush(book);
    }
}
