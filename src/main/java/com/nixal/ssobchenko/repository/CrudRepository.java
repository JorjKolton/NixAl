package com.nixal.ssobchenko.repository;

import java.util.List;

public interface CrudRepository<T> {
    T getById(String id);

    List<T> getAll();

    boolean save(T type);

    boolean saveAll(List<T> type);

    boolean update(T type);

    boolean delete(String id);
}