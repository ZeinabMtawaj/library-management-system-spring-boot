package com.task.library_management_system.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookDtoValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    public void testValidBookDto() {
        var bookDto = new BookDto("Valid Title", "Valid Author", "978-3-16-148410-0", 2021);
        var violations = validator.validate(bookDto);
        assertTrue(violations.isEmpty(), "Should be valid");
    }

    @Test
    public void testEmptyTitle() {
        var bookDto = new BookDto("", "Valid Author", "978-3-16-148410-0", 2021);
        var violations = validator.validate(bookDto);
        assertEquals(1, violations.size());
        assertEquals("Title must be between 1 and 255 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void testTooLongTitle() {
        var longTitle = "A".repeat(256); // 256 characters long
        var bookDto = new BookDto(longTitle, "Valid Author", "978-3-16-148410-0", 2021);
        var violations = validator.validate(bookDto);
        assertEquals(1, violations.size());
        assertEquals("Title must be between 1 and 255 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmptyAuthor() {
        var bookDto = new BookDto("Valid Title", "", "978-3-16-148410-0", 2021);
        var violations = validator.validate(bookDto);
        assertEquals(1, violations.size());
        assertEquals("Author must be between 1 and 255 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void testTooLongAuthor() {
        var longAuthor = "A".repeat(256);
        var bookDto = new BookDto("Valid Title", longAuthor, "978-3-16-148410-0", 2021);
        var violations = validator.validate(bookDto);
        assertEquals(1, violations.size());
        assertEquals("Author must be between 1 and 255 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidIsbn() {
        var bookDto = new BookDto("Valid Title", "Valid Author", "123", 2021);
        var violations = validator.validate(bookDto);
        assertEquals(1, violations.size());
        assertEquals("Invalid ISBN-13 format", violations.iterator().next().getMessage());
    }

    @Test
    public void testPublicationYearTooEarly() {
        var bookDto = new BookDto("Valid Title", "Valid Author", "978-3-16-148410-0", 1449);
        var violations = validator.validate(bookDto);
        assertEquals(1, violations.size());
        assertEquals("Publication year must be no earlier than 1450", violations.iterator().next().getMessage());
    }

    @Test
    public void testPublicationYearTooLate() {
        var bookDto = new BookDto("Valid Title", "Valid Author", "978-3-16-148410-0", 2025);
        var violations = validator.validate(bookDto);
        assertEquals(1, violations.size());
        assertEquals("Publication year cannot be in the future", violations.iterator().next().getMessage());
    }
}
