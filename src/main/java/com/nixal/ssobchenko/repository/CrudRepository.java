package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.model.vehicle.Vehicle;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends Vehicle> {
    Optional<T> findById(String id);

    List<T> getAll();

    boolean save(T type);

    boolean saveAll(List<T> type);

    boolean update(T type);

    boolean delete(String id);

    void deleteAll();
}