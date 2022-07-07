package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.BodyType;
import com.nixal.ssobchenko.model.Car;
import com.nixal.ssobchenko.model.Manufacturer;
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

    public Car createCar(Manufacturer manufacturer, String price, BodyType bodyType) {
        int maxModelsCount = 1000;
        final Car car = new Car(
                "" + RANDOM.nextInt(maxModelsCount),
                manufacturer, new BigDecimal(price), bodyType);
        LOGGER.debug("Created car {}", car.getId());
        return car;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.getCarsManufacturers();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private BodyType getRandomBodyType() {
        final BodyType[] values = BodyType.getCarsBodyTypes();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveCars(List<Car> cars) {
        CARS_REPOSITORY.create(cars);
    }

    public void saveCar(Car car) {
        CARS_REPOSITORY.create(car);
    }

    public void changeCar(Car car, String model, Manufacturer manufacturer, String price, BodyType bodyType) {
        car.setModel(model);
        car.setManufacturer(manufacturer);
        car.setPrice(new BigDecimal(price));
        car.setBodyType(bodyType);
    }

    public void changeCar(Car car, String model, Manufacturer manufacturer, String price) {
        car.setModel(model);
        car.setManufacturer(manufacturer);
        car.setPrice(new BigDecimal(price));
    }

    public void changeCar(Car car, String model, Manufacturer manufacturer) {
        car.setModel(model);
        car.setManufacturer(manufacturer);
    }

    public void changeCarModel(Car car, String model) {
        car.setModel(model);
    }

    public void changeCarPrice(Car car, String price) {
        car.setPrice(new BigDecimal(price));
    }

    public void changeCarManufacturer(Car car, Manufacturer manufacturer) {
        car.setManufacturer(manufacturer);
    }

    public void changeCarBodyType(Car car, BodyType bodyType) {
        car.setBodyType(bodyType);
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