package com.task.library_management_system.service;

import com.task.library_management_system.controller.exceptions.ResourceNotFoundException;
import com.task.library_management_system.entity.BorrowingRecord;
import com.task.library_management_system.reporitory.BorrowingRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class BorrowingRecordService extends BaseService<BorrowingRecord, BorrowingRecordRepository> {

    private final BookService bookService;
    private final PatronService patronService;

    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        var book = bookService.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));
        var patron = patronService.findById(patronId).orElseThrow(() -> new ResourceNotFoundException("Patron not found with ID: " + patronId));
        var existingBorrowingRecord = repository.findByPatronIdAndBookIdAndDeletedIsFalseAndReturnDateIsNull(patronId, bookId);
        if (existingBorrowingRecord.isPresent()) {
            throw new IllegalStateException("A borrowing record for this book and patron already exists.");
        }
        var borrowingRecord = BorrowingRecord.builder().borrowDate(Instant.now()).book(book).patron(patron).build();
        return save(borrowingRecord);
    }

    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        var borrowingRecord = repository.findByPatronIdAndBookIdAndDeletedIsFalseAndReturnDateIsNull(patronId, bookId).orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found for book ID: " + bookId + " and patron ID: " + patronId));
        borrowingRecord.setReturnDate(Instant.now());
        return save(borrowingRecord);
    }
}
