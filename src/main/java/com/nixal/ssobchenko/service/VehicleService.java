package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.Vehicle;
import com.nixal.ssobchenko.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class VehicleService<T extends Vehicle> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);
    protected static final Random RANDOM = new Random();
    protected final CrudRepository<T> repository;

    protected VehicleService(CrudRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> createAndSave(int count) {
        List<T> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final T vehicle = create();
            result.add(vehicle);
            repository.saveAll(result);
            LOGGER.debug("Created vehicle {}", vehicle.getId());
        }
        return result;
    }

    protected abstract T create();

    public boolean delete(T vehicle) {
        if (repository.delete(vehicle.getId())) {
            LOGGER.debug("Deleted vehicle {}", vehicle.getId());
            return true;
        } else {
            LOGGER.debug("Vehicle wasn't deleted {}", vehicle.getId());
            return false;
        }
    }

    public void print(T vehicle, String name) {
        repository.findById(vehicle.getId()).ifPresentOrElse(
                foundVehicle -> System.out.println(name + " = " + foundVehicle),
                () -> System.out.println("Cannot find vehicle with id " + vehicle.getId())
        );
    }

    public void printAll() {
        for (T vehicle : repository.getAll()) {
            System.out.println(vehicle);
        }
    }
}