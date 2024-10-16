package com.task.library_management_system.controller;

import com.task.library_management_system.controller.model.Response;
import com.task.library_management_system.dto.BorrowingRecordDto;
import com.task.library_management_system.service.BorrowingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class BorrowController extends BaseController {

    private final BorrowingRecordService borrowingRecordService;

    @Transactional
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<Response> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        var borrowing = borrowingRecordService.borrowBook(bookId, patronId);
        var response = buildResponse("Book borrowed successfully", HttpStatus.CREATED, Map.of("Borrowing", modelMapper.map(borrowing, BorrowingRecordDto.class)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Transactional
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<Response> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        var borrowing = borrowingRecordService.returnBook(bookId, patronId);
        var response = buildResponse("Book returned successfully", HttpStatus.OK, Map.of("Borrowing", modelMapper.map(borrowing, BorrowingRecordDto.class)));
        return ResponseEntity.ok(response);
    }
}
