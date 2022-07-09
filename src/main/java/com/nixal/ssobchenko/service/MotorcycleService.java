package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.Motorcycle;
import com.nixal.ssobchenko.model.MotorcycleBodyType;
import com.nixal.ssobchenko.model.MotorcycleManufacturer;
import com.nixal.ssobchenko.repository.MotorcyclesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MotorcycleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MotorcycleService.class);
    private static final Random RANDOM = new Random();
    private static final MotorcyclesRepository MOTORCYCLES_REPOSITORY = new MotorcyclesRepository();

    public List<Motorcycle> createMotorcycles(int count) {
        int maxPrice = 90000;
        int maxModelsCount = 1000;
        List<Motorcycle> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Motorcycle motorcycle = new Motorcycle(
                    "" + RANDOM.nextInt(maxModelsCount),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextInt(maxPrice)),
                    getRandomBodyType()
            );
            result.add(motorcycle);
            LOGGER.debug("Created motorcycle {}", motorcycle.getId());
        }
        return result;
    }

    public Motorcycle createMotorcycle(MotorcycleManufacturer motorcycleManufacturer, String price,
                                       MotorcycleBodyType bodyType) {
        int maxModelsCount = 1000;
        final Motorcycle motorcycle = new Motorcycle("" + RANDOM.nextInt(maxModelsCount),
                motorcycleManufacturer, new BigDecimal(price), bodyType);
        LOGGER.debug("Created motorcycle {}", motorcycle.getId());
        return motorcycle;
    }

    private MotorcycleManufacturer getRandomManufacturer() {
        final MotorcycleManufacturer[] values = MotorcycleManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private MotorcycleBodyType getRandomBodyType() {
        final MotorcycleBodyType[] values = MotorcycleBodyType.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveMotorcycles(List<Motorcycle> cars) {
        MOTORCYCLES_REPOSITORY.create(cars);
    }

    public void saveMotorcycle(Motorcycle motorcycle) {
        MOTORCYCLES_REPOSITORY.create(motorcycle);
    }

    public void changeMotorcycle(Motorcycle motorcycle, String model, MotorcycleManufacturer manufacturer, String price,
                                 MotorcycleBodyType bodyType) {
        motorcycle.setModel(model);
        motorcycle.setMotorcycleManufacturer(manufacturer);
        motorcycle.setPrice(new BigDecimal(price));
        motorcycle.setBodyType(bodyType);
    }

    public void deleteMotorcycle(Motorcycle motorcycle) {
        if (MOTORCYCLES_REPOSITORY.delete(motorcycle.getId())) {
            LOGGER.debug("Deleted motorcycle {}", motorcycle.getId());
        } else {
            LOGGER.debug("Motorcycle wasn't deleted {}", motorcycle.getId());
        }
    }

    public void print(Motorcycle motorcycle, String name) {
        System.out.println(name + " = " + MOTORCYCLES_REPOSITORY.getById(motorcycle.getId()));
    }

    public void printAll() {
        for (Motorcycle motorcycle : MOTORCYCLES_REPOSITORY.getAll()) {
            System.out.println(motorcycle);
        }
    }
}