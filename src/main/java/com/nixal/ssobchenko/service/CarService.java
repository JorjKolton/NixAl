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
import java.util.Optional;
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

    public Car getOrCreateCar(String id) {
        return carsRepository.findById(id).orElse(createAndSaveCars(1).get(0));
    }

    public BigDecimal getPriceForCarIfItLessThan(String id, String lessPrice) {
        return carsRepository.findById(id)
                .map(Car::getPrice)
                .filter(price -> price.compareTo(new BigDecimal(lessPrice)) < 0)
                .orElseGet(() -> {
                    LOGGER.info("The car price is bigger than {}", lessPrice);
                    return BigDecimal.ZERO;
                });
    }

    public Car getCarForId(String id) {
        return carsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Can't find car with id " + id));
    }

    public Car getCarWithManufacturer(String id, CarManufacturer manufacturer) {
        Optional<Car> optionalCar = carsRepository.findById(id)
                .or(() -> Optional.of(createAndSaveCars(1).get(0)));
        optionalCar.ifPresent(car -> LOGGER.info("Car manufacturer was {}", car.getCarManufacturer()));
        if (optionalCar.get().getCarManufacturer().equals(manufacturer)) {
            return optionalCar.get();
        } else {
            optionalCar.get().setCarManufacturer(manufacturer);
        }
        return optionalCar.get();
    }

    public void print(String id, String name) {
        carsRepository.findById(id).ifPresentOrElse(
                car -> System.out.println(name + " = " + car),
                () -> System.out.println("Cannot find auto with id " + id)
        );
    }

    public void printAll() {
        for (Car car : carsRepository.getAll()) {
            System.out.println(car);
        }
    }
}