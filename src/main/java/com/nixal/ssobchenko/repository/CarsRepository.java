package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.model.Car;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CarsRepository implements CrudRepository<Car> {
    private final List<Car> cars;

    public CarsRepository() {
        cars = new LinkedList<>();
    }

    @Override
    public Optional<Car> findById(String id) {
        for (Car car : cars) {
            if (car.getId().equals(id)) {
                return Optional.of(car);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Car> getAll() {
        return cars;
    }

    @Override
    public boolean save(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car must be not null");
        }
        if(car.getPrice().equals(BigDecimal.ZERO)) {
            car.setPrice(BigDecimal.valueOf(-1));
        }
        cars.add(car);
        return true;
    }

    @Override
    public boolean saveAll(List<Car> car) {
        if (car == null) {
            return false;
        }
        return cars.addAll(car);
    }

    @Override
    public boolean update(Car car) {
        final Optional<Car> found = findById(car.getId());
        if (found.isPresent()) {
            CarsCopy.copy(car, found.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Car> iterator = cars.iterator();
        while (iterator.hasNext()) {
            final Car car = iterator.next();
            if (car.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private static class CarsCopy {
        static void copy(final Car from, final Car to) {
            to.setCarManufacturer(from.getCarManufacturer());
            to.setModel(from.getModel());
            to.setBodyType(from.getBodyType());
            to.setPrice(from.getPrice());
        }
    }
}