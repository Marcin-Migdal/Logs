package com.project.logs.repository;

import com.project.logs.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByDateAndUserId(String date, Long userId);

    Optional<Log> findById(Long id);
}
