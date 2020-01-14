package com.project.logs.logs;

import com.project.logs.logs.model.Log;
import com.project.logs.logs.repository.LogRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class LogInMemoryRepository implements LogRepository {

    private final Map<Long, Log> logs = new ConcurrentHashMap<>();
    private Long nextId = 1L;

    @Override
    public List<Log> findByDateAndUserId(String date, Long userId) {
        return logs.values().stream()
                .filter(log -> log.getDate().equals(date) && log.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Log> findById(Long id) {
        return Optional.ofNullable(logs.get(id));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Log> findAll() {
        return null;
    }

    @Override
    public List<Log> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Log> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Log> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        logs.remove(aLong);
    }

    @Override
    public void delete(Log log) {

    }

    @Override
    public void deleteAll(Iterable<? extends Log> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Log> S save(S s) {
        Long id = s.getId();
        if (id == null) {
            id = getNextId();
            s.setId(id);
        }
        logs.put(id, s);
        return s;
    }

    @Override
    public <S extends Log> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Log> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Log> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Log getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends Log> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Log> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Log> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Log> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Log> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Log> boolean exists(Example<S> example) {
        return false;
    }

    private Long getNextId() {
        return nextId++;
    }
}
