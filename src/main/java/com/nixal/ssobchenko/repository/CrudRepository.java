package com.nixal.ssobchenko.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(String id);

    List<T> getAll();

    boolean save(T type);

    boolean saveAll(List<T> type);

    boolean update(T type);

    boolean delete(String id);
}