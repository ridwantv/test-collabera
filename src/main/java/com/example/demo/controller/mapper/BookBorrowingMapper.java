package com.example.demo.controller.mapper;

import com.example.demo.controller.dto.bookborrowing.BookBorrowingDto;
import com.example.demo.controller.dto.bookborrowing.CreateBookBorrowingDto;
import com.example.demo.model.bookborrowing.BookBorrowing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class, LocalDate.class})
public interface BookBorrowingMapper {

    BookBorrowingDto map(BookBorrowing bookBorrowing);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "book.id", source = "bookId")
    @Mapping(target = "borrower.id", source = "borrowerId")
    @Mapping(target = "borrowDate", expression = "java(LocalDate.now())")
    @Mapping(target = "returnDate", ignore = true)
    BookBorrowing map(CreateBookBorrowingDto createBookBorrowingDto);

    List<BookBorrowingDto> map(List<BookBorrowing> bookBorrowingList);
}
