package com.task.library_management_system.controller;

import com.task.library_management_system.dto.BookDto;
import com.task.library_management_system.entity.Book;
import com.task.library_management_system.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/books")
public class BookController extends CrudController<Book, BookDto, BookService> {

    @Override
    public Class<Book> getEntityClass() {
        return Book.class;
    }

    @Override
    protected Class<BookDto> getDtoClass() {
        return BookDto.class;
    }
}
