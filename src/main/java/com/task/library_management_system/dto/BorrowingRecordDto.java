package com.task.library_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class BorrowingRecordDto extends BaseDto {

    private BookDto book;

    private PatronDto patron;

    private Instant borrowDate;
    private Instant returnDate;
}
