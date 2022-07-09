package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.model.Car;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CarsRepository implements CrudRepository<Car> {
    private final List<Car> cars;

    public CarsRepository() {
        cars = new LinkedList<>();
    }

    @Override
    public Car getById(String id) {
        for (Car car : cars) {
            if (car.getId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    @Override
    public List<Car> getAll() {
        return cars;
    }

    @Override
    public boolean create(Car car) {
        cars.add(car);
        return true;
    }

    @Override
    public boolean create(List<Car> car) {
        return cars.addAll(car);
    }

    @Override
    public boolean update(Car car) {
        final Car found = getById(car.getId());
        if (found != null) {
            CarsCopy.copy(car, found);
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