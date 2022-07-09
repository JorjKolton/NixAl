package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.model.Motorcycle;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MotorcyclesRepository implements CrudRepository<Motorcycle> {
    private final List<Motorcycle> motorcycles;

    public MotorcyclesRepository() {
        motorcycles = new LinkedList<>();
    }

    @Override
    public Motorcycle getById(String id) {
        for (Motorcycle motorcycle : motorcycles) {
            if (motorcycle.getId().equals(id)) {
                return motorcycle;
            }
        }
        return null;
    }

    @Override
    public List<Motorcycle> getAll() {
        return motorcycles;
    }

    @Override
    public boolean create(Motorcycle motorcycle) {
        motorcycles.add(motorcycle);
        return true;
    }

    @Override
    public boolean create(List<Motorcycle> motorcycles) {
        return this.motorcycles.addAll(motorcycles);
    }

    @Override
    public boolean update(Motorcycle motorcycle) {
        final Motorcycle found = getById(motorcycle.getId());
        if (found != null) {
            MotorcyclesCopy.copy(motorcycle, found);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Motorcycle> iterator = motorcycles.iterator();
        while (iterator.hasNext()) {
            final Motorcycle motorcycle = iterator.next();
            if (motorcycle.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private static class MotorcyclesCopy {
        static void copy(final Motorcycle from, final Motorcycle to) {
            to.setMotorcycleManufacturer(from.getMotorcycleManufacturer());
            to.setModel(from.getModel());
            to.setBodyType(from.getBodyType());
            to.setPrice(from.getPrice());
        }
    }
}