package com.task.library_management_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BORROWING_RECORDS")
public class BorrowingRecord extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "BOOK_ID", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "PATRON_ID", nullable = false)
    private Patron patron;

    @Column(name = "BORROW_DATE", nullable = false)
    private Instant borrowDate;

    @Column(name = "RETURN_DATE")
    private Instant returnDate;
}
