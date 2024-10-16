package com.task.library_management_system.controller;

import com.task.library_management_system.dto.PatronDto;
import com.task.library_management_system.entity.Patron;
import com.task.library_management_system.service.PatronService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/patrons")
public class PatronController extends CrudController<Patron, PatronDto, PatronService> {

    @Override
    public Class<Patron> getEntityClass() {
        return Patron.class;
    }

    @Override
    protected Class<PatronDto> getDtoClass() {
        return PatronDto.class;
    }
}
