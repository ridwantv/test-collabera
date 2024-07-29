package com.example.demo.controller.mapper;

import com.example.demo.controller.dto.book.BookDto;
import com.example.demo.controller.dto.book.CreateBookDto;
import com.example.demo.model.book.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class})
public interface BookMapper {

    BookDto map(Book book);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "isAvailable", expression = "java(true)")
    Book map(CreateBookDto createBookDto);

    List<BookDto> map(List<Book> bookList);
}
