package com.task.library_management_system.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatronDtoValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    public void testValidPatronDto() {
        var contact = new ContactDto("+963995466033", "john.smith@example.com");
        var patronDto = new PatronDto("John Smith", contact);
        var violations = validator.validate(patronDto);
        assertTrue(violations.isEmpty(), "Should be valid");
    }

    @Test
    public void testEmptyName() {
        var contact = new ContactDto("+963995466033", "john.smith@example.com");
        var patronDto = new PatronDto("", contact);
        var violations = validator.validate(patronDto);
        assertEquals(1, violations.size());
        assertEquals("Name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void testTooLongName() {
        var longName = "A".repeat(101);
        var contact = new ContactDto("+963995466033", "john.smith@example.com");
        var patronDto = new PatronDto(longName, contact);
        var violations = validator.validate(patronDto);
        assertEquals(1, violations.size());
        assertEquals("Name cannot exceed 100 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullContact() {
        var patronDto = new PatronDto("John Smith", null);
        var violations = validator.validate(patronDto);
        assertTrue(violations.isEmpty(), "Contact can be null in this context");
    }

    @Test
    public void testValidContact() {
        var contact = new ContactDto("+963995466033", "john.smith@example.com");
        var patronDto = new PatronDto("John Smith", contact);
        var violations = validator.validate(contact);
        assertTrue(violations.isEmpty(), "Should be valid");
    }

    @Test
    public void testInvalidPhone() {
        var contact = new ContactDto("+96399546603", "john.smith@example.com");
        var patronDto = new PatronDto("John Smith", contact);
        var violations = validator.validate(contact);
        assertEquals(1, violations.size());
        assertEquals("Invalid phone number", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidEmail() {
        var contact = new ContactDto("+963995466033", "invalid-email");
        var patronDto = new PatronDto("John Smith", contact);
        var violations = validator.validate(contact);
        assertEquals(1, violations.size());
        assertEquals("Invalid email format", violations.iterator().next().getMessage());
    }
}

