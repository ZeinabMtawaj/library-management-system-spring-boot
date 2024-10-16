package com.task.library_management_system.controller;

import com.task.library_management_system.dto.BookDto;
import com.task.library_management_system.entity.Book;
import com.task.library_management_system.service.BookService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(BookController.class)
public class BookControllerTest extends CrudControllerTest<Book, BookDto, BookService> {


    @Override
    protected Book createEntity() {
        return Book.builder()
                .id(1L)
                .title("Test Book")
                .isbn("978-3-16-148410-0")
                .publicationYear(2023)
                .author("Test Author")
                .build();
    }

    @Override
    protected BookDto createDto() {
        return BookDto.builder()
                .id(1L)
                .title("Test Book")
                .isbn("978-3-16-148410-0")
                .publicationYear(2023)
                .author("Test Author")
                .build();
    }

    @Override
    protected String getEntityEndpoint() {
        return "/api/books";
    }

}
