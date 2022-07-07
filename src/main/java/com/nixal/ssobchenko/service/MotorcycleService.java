package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.BodyType;
import com.nixal.ssobchenko.model.Motorcycle;
import com.nixal.ssobchenko.model.Manufacturer;
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

    public Motorcycle createMotorcycle(Manufacturer manufacturer, String price, BodyType bodyType) {
        int maxModelsCount = 1000;
        final Motorcycle motorcycle = new Motorcycle("" + RANDOM.nextInt(maxModelsCount),
                manufacturer, new BigDecimal(price), bodyType);
        LOGGER.debug("Created motorcycle {}", motorcycle.getId());
        return motorcycle;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.getMotorcyclesManufacturers();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private BodyType getRandomBodyType() {
        final BodyType[] values = BodyType.getMotorcyclesBodyTypes();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveMotorcycles(List<Motorcycle> cars) {
        MOTORCYCLES_REPOSITORY.create(cars);
    }

    public void saveMotorcycle(Motorcycle motorcycle) {
        MOTORCYCLES_REPOSITORY.create(motorcycle);
    }

    public void changeMotorcycle(Motorcycle motorcycle, String model, Manufacturer manufacturer, String price, BodyType bodyType) {
        motorcycle.setModel(model);
        motorcycle.setManufacturer(manufacturer);
        motorcycle.setPrice(new BigDecimal(price));
        motorcycle.setBodyType(bodyType);
    }

    public void changeMotorcycle(Motorcycle motorcycle, String model, Manufacturer manufacturer, String price) {
        motorcycle.setModel(model);
        motorcycle.setManufacturer(manufacturer);
        motorcycle.setPrice(new BigDecimal(price));
    }

    public void changeMotorcycle(Motorcycle motorcycle, String model, Manufacturer manufacturer) {
        motorcycle.setModel(model);
        motorcycle.setManufacturer(manufacturer);
    }

    public void changeMotorcycleModel(Motorcycle motorcycle, String model) {
        motorcycle.setModel(model);
    }

    public void changeMotorcyclePrice(Motorcycle motorcycle, String price) {
        motorcycle.setPrice(new BigDecimal(price));
    }

    public void changeMotorcycleManufacturer(Motorcycle motorcycle, Manufacturer manufacturer) {
        motorcycle.setManufacturer(manufacturer);
    }

    public void changeMotorcycleBodyType(Motorcycle motorcycle, BodyType bodyType) {
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