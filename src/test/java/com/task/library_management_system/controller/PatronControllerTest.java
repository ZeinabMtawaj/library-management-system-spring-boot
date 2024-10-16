package com.task.library_management_system.controller;

import com.task.library_management_system.dto.ContactDto;
import com.task.library_management_system.dto.PatronDto;
import com.task.library_management_system.entity.Contact;
import com.task.library_management_system.entity.Patron;
import com.task.library_management_system.service.PatronService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(PatronController.class)
public class PatronControllerTest extends CrudControllerTest<Patron, PatronDto, PatronService> {

    @Override
    protected Patron createEntity() {
        return Patron.builder()
                .id(1L)
                .name("Test Patron")
                .contact(Contact.builder()
                        .phone("+963941074327")
                        .email("z@example.com")
                        .build())
                .build();
    }

    @Override
    protected PatronDto createDto() {
        return PatronDto.builder()
                .id(1L)
                .name("Test Patron")
                .contact(ContactDto.builder()
                        .phone("+963941074327")
                        .email("z@example.com")
                        .build())
                .build();
    }

    @Override
    protected String getEntityEndpoint() {
        return "/api/patrons";
    }

}
