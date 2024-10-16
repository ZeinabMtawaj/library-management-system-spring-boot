package com.task.library_management_system.reporitory;

import com.task.library_management_system.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    Optional<T> findByIdAndDeletedIsFalse(Long id);

    List<T> findAllByDeletedIsFalse();
}

