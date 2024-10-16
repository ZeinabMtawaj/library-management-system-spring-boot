package com.task.library_management_system.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.ISBN;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class BookDto extends BaseDto {

    @NotNull(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;

    @NotNull(message = "Author is required")
    @Size(min = 1, max = 255, message = "Author must be between 1 and 255 characters")
    private String author;

    @ISBN(type = ISBN.Type.ISBN_13, message = "Invalid ISBN-13 format")
    private String isbn;

    @NotNull(message = "Publication year is required")
    @Min(value = 1450, message = "Publication year must be no earlier than 1450")
    @Max(value = 2024, message = "Publication year cannot be in the future")
    private Integer publicationYear;
}
