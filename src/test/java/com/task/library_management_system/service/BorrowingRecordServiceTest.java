package com.task.library_management_system.service;

import com.task.library_management_system.controller.exceptions.ResourceNotFoundException;
import com.task.library_management_system.entity.Book;
import com.task.library_management_system.entity.BorrowingRecord;
import com.task.library_management_system.entity.Patron;
import com.task.library_management_system.reporitory.BorrowingRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordServiceTest {


    @Mock
    private BookService bookService;

    @Mock
    private PatronService patronService;

    @Mock
    private BorrowingRecordRepository repository;

    @InjectMocks
    private BorrowingRecordService borrowingRecordService;

    private BorrowingRecord borrowingRecord;
    private Book book;
    private Patron patron;

    @BeforeEach
    void setUp() throws Exception {
        book = new Book();
        book.setId(1L);

        patron = new Patron();
        patron.setId(1L);

        borrowingRecord = BorrowingRecord.builder()
                .borrowDate(Instant.now())
                .book(book)
                .patron(patron)
                .build();

        injectRepositoryIntoService();
    }

    private void injectRepositoryIntoService() throws Exception {
        Field repositoryField = BaseService.class.getDeclaredField("repository");
        repositoryField.setAccessible(true);
        repositoryField.set(borrowingRecordService, repository);
    }

    @Test
    void testBorrowBook_Success() {
        when(bookService.findById(1L)).thenReturn(Optional.of(book));
        when(patronService.findById(1L)).thenReturn(Optional.of(patron));
        when(repository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord result = borrowingRecordService.borrowBook(1L, 1L);

        assertNotNull(result);
        assertEquals(book, result.getBook());
        assertEquals(patron, result.getPatron());
        assertNotNull(result.getBorrowDate());

        verify(bookService).findById(1L);
        verify(patronService).findById(1L);
        verify(repository).save(any(BorrowingRecord.class));
    }

    @Test
    void testBorrowBook_BookNotFound() {
        when(bookService.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> borrowingRecordService.borrowBook(1L, 1L));

        assertEquals("Book not found with ID: 1", exception.getMessage());

        verify(patronService, never()).findById(anyLong());
        verify(repository, never()).save(any());
    }

    @Test
    void testBorrowBook_PatronNotFound() {
        when(bookService.findById(1L)).thenReturn(Optional.of(book));
        when(patronService.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> borrowingRecordService.borrowBook(1L, 1L));

        assertEquals("Patron not found with ID: 1", exception.getMessage());

        verify(bookService).findById(1L);
        verify(patronService).findById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void testReturnBook_Success() {
        when(repository.findByPatronIdAndBookIdAndDeletedIsFalseAndReturnDateIsNull(1L, 1L)).thenReturn(Optional.ofNullable(borrowingRecord));
        when(repository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord result = borrowingRecordService.returnBook(1L, 1L);

        assertNotNull(result.getReturnDate());

        verify(repository).findByPatronIdAndBookIdAndDeletedIsFalseAndReturnDateIsNull(1L, 1L);
        verify(repository).save(borrowingRecord);
    }

    @Test
    void testReturnBook_BorrowingRecordNotFound() {
        when(repository.findByPatronIdAndBookIdAndDeletedIsFalseAndReturnDateIsNull(1L, 1L))
                .thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                borrowingRecordService.returnBook(1L, 1L));

        assertEquals("Borrowing record not found for book ID: 1 and patron ID: 1", exception.getMessage());

        verify(repository).findByPatronIdAndBookIdAndDeletedIsFalseAndReturnDateIsNull(1L, 1L);
    }
}
