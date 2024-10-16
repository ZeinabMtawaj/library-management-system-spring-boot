package com.task.library_management_system.service;

import com.task.library_management_system.entity.Contact;
import com.task.library_management_system.entity.Patron;
import com.task.library_management_system.reporitory.PatronRepository;

public class PatronTestService extends BaseServiceTest<Patron, PatronService, PatronRepository> {

    @Override
    protected Patron createEntity() {
        return new Patron("The Great Gatsby", Contact.builder().phone("+963776198541").email("z@example.com").build());
    }

    @Override
    protected PatronService createService() {
        return new PatronService();
    }
}

