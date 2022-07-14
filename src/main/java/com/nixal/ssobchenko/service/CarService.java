package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.Car;
import com.nixal.ssobchenko.model.CarBodyType;
import com.nixal.ssobchenko.model.CarManufacturer;
import com.nixal.ssobchenko.repository.CarsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public record CarService(CarsRepository carsRepository) {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);
    private static final Random RANDOM = new Random();

    public List<Car> createAndSaveCars(int count) {
        int maxPrice = 900000;
        int maxModelsCount = 1000;
        List<Car> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Car car = new Car(
                    "" + RANDOM.nextInt(maxModelsCount),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextInt(maxPrice)),
                    getRandomBodyType()
            );
            result.add(car);
            carsRepository.saveAll(result);
            LOGGER.debug("Created car {}", car.getId());
        }
        return result;
    }

    public Car createAndSaveCar(String model, CarManufacturer carManufacturer, String price, CarBodyType carBodyType) {
        final Car car = new Car(model, carManufacturer, new BigDecimal(price), carBodyType);
        LOGGER.debug("Created car {}", car.getId());
        carsRepository.save(car);
        return car;
    }

    private CarManufacturer getRandomManufacturer() {
        final CarManufacturer[] values = CarManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private CarBodyType getRandomBodyType() {
        final CarBodyType[] values = CarBodyType.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public boolean changeCar(Car car, String model, CarManufacturer carManufacturer, String price, CarBodyType carBodyType) {
        car.setModel(model);
        car.setCarManufacturer(carManufacturer);
        car.setPrice(new BigDecimal(price));
        car.setBodyType(carBodyType);
        return carsRepository.update(car);
    }

    public boolean deleteCar(Car car) {
        if (carsRepository.delete(car.getId())) {
            LOGGER.debug("Deleted car {}", car.getId());
            return true;
        } else {
            LOGGER.debug("Car wasn't deleted {}", car.getId());
            return false;
        }
    }

    public void print(Car car, String name) {
        System.out.println(name + " = " + carsRepository.getById(car.getId()));
    }

    public void printAll() {
        for (Car car : carsRepository.getAll()) {
            System.out.println(car);
        }
    }
}