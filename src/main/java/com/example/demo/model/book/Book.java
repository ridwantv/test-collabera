package com.example.demo.model.book;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "book")
@Getter
@Setter
@Builder
@AllArgsConstructor(access= AccessLevel.PROTECTED)
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Book {

    public static final String COLUMN_ID = "id";

    @Id
    @NotNull
    private UUID id;

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String isbn;

    @NotNull
    private Boolean isAvailable;
}
