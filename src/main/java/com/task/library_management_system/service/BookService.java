package com.task.library_management_system.service;

import com.task.library_management_system.entity.Book;
import com.task.library_management_system.reporitory.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService extends BaseService<Book, BookRepository> {
}

