package com.example.demo.service;

import com.example.demo.controller.dto.borrower.BorrowerDto;
import com.example.demo.controller.dto.borrower.CreateBorrowerDto;
import com.example.demo.controller.mapper.BorrowerMapper;
import com.example.demo.exception.NonExistingBorrowerException;
import com.example.demo.model.borrower.Borrower;
import com.example.demo.model.borrower.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private BorrowerMapper borrowerMapper;

    public List<BorrowerDto> getBorrowerList() {
        return borrowerMapper.map(borrowerRepository.findAll());
    }

    public BorrowerDto getBorrower(UUID id) {
        return borrowerMapper.map(borrowerRepository.findById(id).orElseThrow(NonExistingBorrowerException::of));
    }

    public Borrower addBorrower(CreateBorrowerDto dto) {
        return borrowerRepository.saveAndFlush(borrowerMapper.map(dto));
    }
}
