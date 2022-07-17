package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.model.Bus;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BusesRepository implements CrudRepository<Bus> {
    private final List<Bus> buses;

    public BusesRepository() {
        buses = new LinkedList<>();
    }

    @Override
    public Optional<Bus> findById(String id) {
        for (Bus bus : buses) {
            if (bus.getId().equals(id)) {
                return Optional.of(bus);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Bus> getAll() {
        return buses;
    }

    @Override
    public boolean save(Bus bus) {
        if (bus == null) {
            throw new IllegalArgumentException("Bus must be not null");
        }
        if(bus.getPrice().equals(BigDecimal.ZERO)) {
            bus.setPrice(BigDecimal.valueOf(-1));
        }
        buses.add(bus);
        return true;
    }

    @Override
    public boolean saveAll(List<Bus> bus) {
        if (bus == null) {
            return false;
        }
        return buses.addAll(bus);
    }

    @Override
    public boolean update(Bus bus) {
        final Optional<Bus> found = findById(bus.getId());
        if (found.isPresent()) {
            BusesCopy.copy(bus, found.get());
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