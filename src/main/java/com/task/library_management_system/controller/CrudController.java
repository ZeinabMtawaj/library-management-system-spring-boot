package com.task.library_management_system.controller;

import com.task.library_management_system.config.EntityCacheResolver;
import com.task.library_management_system.controller.exceptions.ResourceNotFoundException;
import com.task.library_management_system.controller.model.Response;
import com.task.library_management_system.dto.BaseDto;
import com.task.library_management_system.entity.BaseEntity;
import com.task.library_management_system.reporitory.BaseRepository;
import com.task.library_management_system.service.BaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public abstract class CrudController<T extends BaseEntity, D extends BaseDto, S extends BaseService<T, ? extends BaseRepository<T>>> extends BaseController {


    @Autowired
    protected S service;

    @Autowired
    private EntityCacheResolver entityCacheResolver;

    @GetMapping
    @Transactional(readOnly = true)
    @Cacheable(cacheResolver = "entityCacheResolver", key = "'all'")
    public ResponseEntity<Response> getAll() {
        var entities = service.findAll();
        var dtos = entities.stream().map(e -> modelMapper.map(e, getDtoClass())).toList();
        var response = buildResponse("Entities retrieved successfully", HttpStatus.OK, Map.of("entities", dtos));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @Cacheable(cacheResolver = "entityCacheResolver", key = "#id")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        var entity = service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        var dto = modelMapper.map(entity, getDtoClass());
        var response = buildResponse("Entity retrieved successfully", HttpStatus.OK, Map.of("entity", dto));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    @Transactional
    @CachePut(cacheResolver = "entityCacheResolver", key = "#result.body.data['entity'].id.toString()")
    @CacheEvict(cacheResolver = "entityCacheResolver", key = "'all'")
    public ResponseEntity<Response> create(@RequestBody @Valid D dto) {
        var entity = modelMapper.map(dto, getEntityClass());
        var createdEntity = service.save(entity);
        var createdDto = modelMapper.map(createdEntity, getDtoClass());
        ;
        var response = buildResponse("Entity created successfully", HttpStatus.CREATED, Map.of("entity", createdDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Transactional
    @PutMapping("/{id}")
    @CachePut(cacheResolver = "entityCacheResolver", key = "#id.toString()")
    @CacheEvict(cacheResolver = "entityCacheResolver", key = "'all'")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody @Valid D dto) {
        var existingEntity = service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        modelMapper.map(dto, existingEntity);
        var updatedEntity = service.save(existingEntity);
        var updatedDto = modelMapper.map(updatedEntity, getDtoClass());
        var response = buildResponse("Entity updated successfully", HttpStatus.OK, Map.of("entity", updatedDto));
        return ResponseEntity.ok(response);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @Caching(evict = {
            @CacheEvict(cacheResolver = "entityCacheResolver", key = "#id.toString()"),
            @CacheEvict(cacheResolver = "entityCacheResolver", key = "'all'")
    })
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        var entity = service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        service.delete(entity);
        var response = buildResponse("Entity deleted successfully", HttpStatus.OK, null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public abstract Class<T> getEntityClass();

    protected abstract Class<D> getDtoClass();

}
