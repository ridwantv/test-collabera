package com.example.demo.model.borrower;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "borrower")
@Getter
@Setter
@Builder
@AllArgsConstructor(access= AccessLevel.PROTECTED)
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Borrower {

    public static final String COLUMN_ID = "id";

    @Id
    @NotNull
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String email;
}
