package com.task.library_management_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.library_management_system.config.EntityCacheResolver;
import com.task.library_management_system.config.SecurityConfig;
import com.task.library_management_system.dto.BaseDto;
import com.task.library_management_system.entity.BaseEntity;
import com.task.library_management_system.reporitory.BaseRepository;
import com.task.library_management_system.service.BaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
@Import(SecurityConfig.class)
@WithMockUser(username = "librarian", authorities = {"LIBRARIAN", "ADMIN"})
public abstract class CrudControllerTest<T extends BaseEntity, D extends BaseDto, S extends BaseService<T, ? extends BaseRepository<T>>> {


    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected S service;

    @MockBean
    protected EntityCacheResolver entityCacheResolver;

    protected D dto;
    protected T entity;

    @BeforeEach
    void setUp() {

        entity = createEntity();
        dto = createDto();

        Mockito.when(service.findById(1L)).thenReturn(Optional.of(entity));
        Mockito.when(service.save(any())).thenReturn(entity);
        Mockito.when(service.findAll()).thenReturn(Collections.singletonList(entity));
    }

    protected abstract T createEntity();

    protected abstract D createDto();

    protected abstract String getEntityEndpoint();


    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(getEntityEndpoint()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.message").value("Entities retrieved successfully"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.entities").isArray())
                .andExpect(jsonPath("$.data.entities[0].id").value(1L));
        ;
    }


    @Test
    void testGetById() throws Exception {
        mockMvc.perform(get(getEntityEndpoint() + "/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.message").value("Entity retrieved successfully"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.entity.id").value(1L));
    }


    @Test
    void testCreate() throws Exception {
        mockMvc.perform(post(getEntityEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.message").value("Entity created successfully"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.data.entity.id").value(1L));
    }


    @Test
    void testUpdate() throws Exception {
        mockMvc.perform(put(getEntityEndpoint() + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.message").value("Entity updated successfully"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.entity.id").value(1L))
                .andExpect(jsonPath("$.data.entity").value(dto));
    }


    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(getEntityEndpoint() + "/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.message").value("Entity deleted successfully"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
    }
}

