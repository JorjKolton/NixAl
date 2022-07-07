package com.nixal.ssobchenko.repository;

import java.util.List;

public interface CrudRepository<T> {
    T getById(String id);

    List<T> getAll();

    boolean create(T type);

    boolean create(List<T> type);

    boolean update(T type);

    boolean delete(String id);
}