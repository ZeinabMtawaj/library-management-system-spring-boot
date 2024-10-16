package com.task.library_management_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.library_management_system.config.SecurityConfig;
import com.task.library_management_system.controller.exceptions.ResourceNotFoundException;
import com.task.library_management_system.dto.BorrowingRecordDto;
import com.task.library_management_system.entity.BorrowingRecord;
import com.task.library_management_system.service.BorrowingRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BorrowController.class)
@WithMockUser(username = "librarian", authorities = {"LIBRARIAN"})
@Import(SecurityConfig.class)
class BorrowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private BorrowingRecord borrowingRecord;
    private BorrowingRecordDto borrowingRecordDto;

    @BeforeEach
    void setUp() {
        borrowingRecord = BorrowingRecord.builder().build();
        borrowingRecordDto = new BorrowingRecordDto();
    }

    @Test
    void testBorrowBook_Success() throws Exception {
        when(borrowingRecordService.borrowBook(1L, 1L)).thenReturn(borrowingRecord);
        when(modelMapper.map(any(BorrowingRecord.class), any())).thenReturn(borrowingRecordDto);

        mockMvc.perform(post("/api/borrow/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Book borrowed successfully"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.name()))
                .andExpect(jsonPath("$.data.Borrowing").exists());
    }

    @Test
    void testBorrowBook_BookNotFound() throws Exception {
        when(borrowingRecordService.borrowBook(1L, 1L))
                .thenThrow(new ResourceNotFoundException("Book not found with ID: 1"));

        mockMvc.perform(post("/api/borrow/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book not found with ID: 1"));
    }

    @Test
    void testBorrowBook_PatronNotFound() throws Exception {
        when(borrowingRecordService.borrowBook(1L, 1L))
                .thenThrow(new ResourceNotFoundException("Patron not found with ID: 1"));

        mockMvc.perform(post("/api/borrow/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patron not found with ID: 1"))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.name()));
    }

    @Test
    void testReturnBook_Success() throws Exception {
        when(borrowingRecordService.returnBook(1L, 1L)).thenReturn(borrowingRecord);
        when(modelMapper.map(any(BorrowingRecord.class), any())).thenReturn(borrowingRecordDto);

        mockMvc.perform(put("/api/return/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book returned successfully"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.name()))
                .andExpect(jsonPath("$.data.Borrowing").exists());
    }

    @Test
    void testReturnBook_BorrowingRecordNotFound() throws Exception {
        when(borrowingRecordService.returnBook(1L, 1L))
                .thenThrow(new ResourceNotFoundException("Borrowing record not found for book ID: 1 and patron ID: 1"));

        mockMvc.perform(put("/api/return/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // Expect 404 Not Found
                .andExpect(jsonPath("$.message").value("Borrowing record not found for book ID: 1 and patron ID: 1"))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.name()));
    }

}

