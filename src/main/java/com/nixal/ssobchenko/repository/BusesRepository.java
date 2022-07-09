package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.model.Bus;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BusesRepository implements CrudRepository<Bus> {
    private final List<Bus> buses;

    public BusesRepository() {
        buses = new LinkedList<>();
    }

    @Override
    public Bus getById(String id) {
        for (Bus bus : buses) {
            if (bus.getId().equals(id)) {
                return bus;
            }
        }
        return null;
    }

    @Override
    public List<Bus> getAll() {
        return buses;
    }

    @Override
    public boolean create(Bus bus) {
        buses.add(bus);
        return true;
    }

    @Override
    public boolean create(List<Bus> bus) {
        return buses.addAll(bus);
    }

    @Override
    public boolean update(Bus bus) {
        final Bus found = getById(bus.getId());
        if (found != null) {
            BusesCopy.copy(bus, found);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Bus> iterator = buses.iterator();
        while (iterator.hasNext()) {
            final Bus bus = iterator.next();
            if (bus.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private static class BusesCopy {
        static void copy(final Bus from, final Bus to) {
            to.setBusManufacturer(from.getBusManufacturer());
            to.setModel(from.getModel());
            to.setNumberOfSeats(from.getNumberOfSeats());
            to.setPrice(from.getPrice());
        }
    }
}