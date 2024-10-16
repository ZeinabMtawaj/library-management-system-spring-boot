package com.task.library_management_system.service;

import com.task.library_management_system.entity.Book;
import com.task.library_management_system.reporitory.BookRepository;

public class BookServiceTest extends BaseServiceTest<Book, BookService, BookRepository> {

    @Override
    protected Book createEntity() {
        return new Book("The Great Gatsby", "F. Scott Fitzgerald", "1234567890", 1925);
    }

    @Override
    protected BookService createService() {
        return new BookService();
    }
}
