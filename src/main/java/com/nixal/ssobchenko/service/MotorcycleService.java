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

public record MotorcycleService(MotorcyclesRepository motorcyclesRepository) {

    private static final Logger LOGGER = LoggerFactory.getLogger(MotorcycleService.class);
    private static final Random RANDOM = new Random();

    public List<Motorcycle> createAndSaveMotorcycles(int count) {
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
            motorcyclesRepository.saveAll(result);
            LOGGER.debug("Created motorcycle {}", motorcycle.getId());
        }
        return result;
    }

    public Motorcycle createAndSaveMotorcycle(String model, MotorcycleManufacturer motorcycleManufacturer, String price,
                                              MotorcycleBodyType bodyType) {
        final Motorcycle motorcycle = new Motorcycle(model, motorcycleManufacturer, new BigDecimal(price), bodyType);
        LOGGER.debug("Created motorcycle {}", motorcycle.getId());
        motorcyclesRepository.save(motorcycle);
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

    public boolean changeMotorcycle(Motorcycle motorcycle, String model, MotorcycleManufacturer manufacturer, String price,
                                 MotorcycleBodyType bodyType) {
        motorcycle.setModel(model);
        motorcycle.setMotorcycleManufacturer(manufacturer);
        motorcycle.setPrice(new BigDecimal(price));
        motorcycle.setBodyType(bodyType);
        return motorcyclesRepository.update(motorcycle);
    }

    public boolean deleteMotorcycle(Motorcycle motorcycle) {
        if (motorcyclesRepository.delete(motorcycle.getId())) {
            LOGGER.debug("Deleted motorcycle {}", motorcycle.getId());
            return true;
        } else {
            LOGGER.debug("Motorcycle wasn't deleted {}", motorcycle.getId());
            return false;
        }
    }

    public void print(Motorcycle motorcycle, String name) {
        System.out.println(name + " = " + motorcyclesRepository.getById(motorcycle.getId()));
    }

    public void printAll() {
        for (Motorcycle motorcycle : motorcyclesRepository.getAll()) {
            System.out.println(motorcycle);
        }
    }
}