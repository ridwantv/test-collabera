package com.example.demo.controller.mapper;

import com.example.demo.controller.dto.borrower.BorrowerDto;
import com.example.demo.controller.dto.borrower.CreateBorrowerDto;
import com.example.demo.model.borrower.Borrower;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class})
public interface BorrowerMapper {

    BorrowerDto map(Borrower borrower);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    Borrower map(CreateBorrowerDto createBorrowerDto);

    List<BorrowerDto> map(List<Borrower> bookList);
}
