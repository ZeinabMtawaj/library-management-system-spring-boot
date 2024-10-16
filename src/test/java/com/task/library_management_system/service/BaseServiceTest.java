package com.task.library_management_system.service;

import com.task.library_management_system.entity.BaseEntity;
import com.task.library_management_system.reporitory.BaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public abstract class BaseServiceTest<T extends BaseEntity, S extends BaseService<T, R>, R extends BaseRepository<T>> {

    @Mock
    protected R repository;

    protected S service;
    protected T entity;

    @BeforeEach
    public void setUp() throws Exception {

        entity = createEntity();

        service = createService();

        injectRepositoryIntoService();
    }

    protected abstract T createEntity();

    protected abstract S createService();

    private void injectRepositoryIntoService() throws Exception {
        Field repositoryField = BaseService.class.getDeclaredField("repository");
        repositoryField.setAccessible(true);
        repositoryField.set(service, repository);
    }

    @Test
    public void testSave() {
        when(repository.save(entity)).thenReturn(entity);

        T savedEntity = service.save(entity);

        assertNotNull(savedEntity);
        assertEquals(entity.getId(), savedEntity.getId());
        verify(repository, times(1)).save(entity);
    }

    @Test
    public void testFindById() {
        when(repository.findByIdAndDeletedIsFalse(entity.getId())).thenReturn(Optional.of(entity));

        Optional<T> foundEntity = service.findById(entity.getId());

        assertTrue(foundEntity.isPresent());
        assertEquals(entity.getId(), foundEntity.get().getId());
        verify(repository, times(1)).findByIdAndDeletedIsFalse(entity.getId());
    }

    @Test
    public void testFindAll() {
        when(repository.findAllByDeletedIsFalse()).thenReturn(Collections.singletonList(entity));

        List<T> entities = service.findAll();

        assertFalse(entities.isEmpty());
        assertEquals(1, entities.size());
        assertEquals(entity.getId(), entities.get(0).getId());
        verify(repository, times(1)).findAllByDeletedIsFalse();
    }

    @Test
    public void testDelete() {
        service.delete(entity);

        assertTrue(entity.isDeleted());
        verify(repository, times(1)).save(entity);
    }
}
