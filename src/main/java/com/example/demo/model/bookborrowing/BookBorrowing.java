package com.example.demo.model.bookborrowing;

import com.example.demo.model.book.Book;
import com.example.demo.model.borrower.Borrower;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "book_borrowing")
@Getter
@Setter
@Builder
@AllArgsConstructor(access= AccessLevel.PROTECTED)
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class BookBorrowing {

    @Id
    @NotNull
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = Book.COLUMN_ID, updatable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_id", referencedColumnName = Borrower.COLUMN_ID, updatable = false)
    private Borrower borrower;

    private LocalDate borrowDate;

    private LocalDate returnDate;
}
