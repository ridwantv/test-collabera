package com.example.demo.model.borrower;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BorrowerRepository extends JpaRepository<Borrower, UUID> {
}
