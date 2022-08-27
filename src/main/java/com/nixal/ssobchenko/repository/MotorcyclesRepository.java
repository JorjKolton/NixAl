package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.model.vehicle.Motorcycle;
import com.nixal.ssobchenko.util.ApplicationContext;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ApplicationContext.Singleton
public class MotorcyclesRepository implements CrudRepository<Motorcycle> {
    private final List<Motorcycle> motorcycles;

    private static MotorcyclesRepository instance;

    @ApplicationContext.Autowired
    private MotorcyclesRepository() {
        motorcycles = new LinkedList<>();
    }

    public static MotorcyclesRepository getInstance() {
        if (instance == null) {
            instance = new MotorcyclesRepository();
        }
        return instance;
    }

    @Override
    public Optional<Motorcycle> findById(String id) {
        return motorcycles.stream()
                .filter(motorcycle -> motorcycle.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Motorcycle> getAll() {
        return motorcycles;
    }

    @Override
    public boolean save(Motorcycle motorcycle) {
        if (motorcycle == null) {
            throw new IllegalArgumentException("Motorcycle must be not null");
        }
        if(motorcycle.getPrice().equals(BigDecimal.ZERO)) {
            motorcycle.setPrice(BigDecimal.valueOf(-1));
        }
        motorcycles.add(motorcycle);
        return true;
    }

    @Override
    public boolean saveAll(List<Motorcycle> motorcycle) {
        if (motorcycle == null) {
            return false;
        }
        return motorcycles.addAll(motorcycle);
    }

    @Override
    public boolean update(Motorcycle motorcycle) {
        final Optional<Motorcycle> found = findById(motorcycle.getId());
        if (found.isPresent()) {
            MotorcyclesCopy.copy(motorcycle, found.get());
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

    @Override
    public void deleteAll() {
        motorcycles.clear();
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