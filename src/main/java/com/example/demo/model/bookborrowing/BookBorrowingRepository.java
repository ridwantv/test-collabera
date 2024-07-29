package com.example.demo.model.bookborrowing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookBorrowingRepository extends JpaRepository<BookBorrowing, UUID> {

    Optional<BookBorrowing> findByIdAndReturnDateIsNull(UUID id);

    List<BookBorrowing> findByReturnDateIsNull();
}
