package com.task.library_management_system.service;

import com.task.library_management_system.entity.BaseEntity;
import com.task.library_management_system.reporitory.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends BaseEntity, R extends BaseRepository<T>> {

    @Autowired
    protected R repository;

    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public Optional<T> findById(Long id) {
        return repository.findByIdAndDeletedIsFalse(id);
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAllByDeletedIsFalse();
    }

    @Transactional
    public void delete(T entity) {
        entity.setDeleted(true);
        save(entity);
    }
}