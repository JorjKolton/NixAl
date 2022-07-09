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

public class CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);
    private static final Random RANDOM = new Random();
    private static final CarsRepository CARS_REPOSITORY = new CarsRepository();

    public List<Car> createCars(int count) {
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
            LOGGER.debug("Created car {}", car.getId());
        }
        return result;
    }

    public Car createCar(CarManufacturer carManufacturer, String price, CarBodyType carBodyType) {
        int maxModelsCount = 1000;
        final Car car = new Car(
                "" + RANDOM.nextInt(maxModelsCount),
                carManufacturer, new BigDecimal(price), carBodyType);
        LOGGER.debug("Created car {}", car.getId());
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

    public void saveCars(List<Car> cars) {
        CARS_REPOSITORY.create(cars);
    }

    public void saveCar(Car car) {
        CARS_REPOSITORY.create(car);
    }

    public void changeCar(Car car, String model, CarManufacturer carManufacturer, String price, CarBodyType carBodyType) {
        car.setModel(model);
        car.setCarManufacturer(carManufacturer);
        car.setPrice(new BigDecimal(price));
        car.setBodyType(carBodyType);
        CARS_REPOSITORY.update(car);
    }

    public void deleteCar(Car car) {
        if (CARS_REPOSITORY.delete(car.getId())) {
            LOGGER.debug("Deleted car {}", car.getId());
        } else {
            LOGGER.debug("Car wasn't deleted {}", car.getId());
        }
    }

    public void print(Car car, String name) {
        System.out.println(name + " = " + CARS_REPOSITORY.getById(car.getId()));
    }

    public void printAll() {
        for (Car car : CARS_REPOSITORY.getAll()) {
            System.out.println(car);
        }
    }
}