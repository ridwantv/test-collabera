package com.example.demo.controller;

import com.example.demo.controller.dto.borrower.BorrowerDto;
import com.example.demo.controller.dto.borrower.CreateBorrowerDto;
import com.example.demo.model.borrower.Borrower;
import com.example.demo.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/borrowers")
public class BorrowerApi {

    @Autowired
    private BorrowerService borrowerService;

    @GetMapping
    public ResponseEntity<List<BorrowerDto>> getBorrowerList() {
        return ResponseEntity.ok(borrowerService.getBorrowerList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowerDto> getBorrower(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(borrowerService.getBorrower(id));
    }

    @PostMapping
    public ResponseEntity<Void> addBorrower(@RequestBody CreateBorrowerDto dto) {
        Borrower borrower = borrowerService.addBorrower(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{borrowerId}")
                .buildAndExpand(borrower.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
