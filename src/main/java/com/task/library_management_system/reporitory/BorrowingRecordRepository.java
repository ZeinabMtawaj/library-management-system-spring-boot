package com.task.library_management_system.reporitory;

import com.task.library_management_system.entity.BorrowingRecord;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends BaseRepository<BorrowingRecord> {
    Optional<BorrowingRecord> findByPatronIdAndBookIdAndDeletedIsFalseAndReturnDateIsNull(Long patronId, Long bookId);

}