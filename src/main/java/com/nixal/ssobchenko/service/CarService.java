package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.Car;
import com.nixal.ssobchenko.model.CarBodyType;
import com.nixal.ssobchenko.model.CarManufacturer;
import com.nixal.ssobchenko.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.Optional;

public class CarService extends VehicleService<Car> {

    public CarService(CrudRepository<Car> repository) {
        super(repository);
    }

    public Car createAndSaveCar(String model, CarManufacturer carManufacturer, String price, CarBodyType carBodyType) {
        final Car car = new Car(model, carManufacturer, new BigDecimal(price), carBodyType);
        LOGGER.debug("Created car {}", car.getId());
        repository.save(car);
        return car;
    }

    @Override
    protected Car create() {
        return new Car(
                "" + RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextInt(900000)),
                getRandomBodyType()
        );
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
        return repository.update(car);
    }

    public Car getOrCreateCar(String id) {
        return repository.findById(id).orElse(createAndSave(1).get(0));
    }

    public BigDecimal getPriceForCarIfItLessThan(String id, String lessPrice) {
        return repository.findById(id)
                .map(Car::getPrice)
                .filter(price -> price.compareTo(new BigDecimal(lessPrice)) < 0)
                .orElseGet(() -> {
                    LOGGER.info("The car price is bigger than {}", lessPrice);
                    return BigDecimal.ZERO;
                });
    }

    public Car getCarForId(String id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Can't find car with id " + id));
    }

    public Car getCarWithManufacturer(String id, CarManufacturer manufacturer) {
        Optional<Car> optionalCar = repository.findById(id)
                .or(() -> Optional.of(createAndSave(1).get(0)));
        optionalCar.ifPresent(car -> LOGGER.info("Car manufacturer was {}", car.getCarManufacturer()));
        if (optionalCar.get().getCarManufacturer().equals(manufacturer)) {
            return optionalCar.get();
        } else {
            optionalCar.get().setCarManufacturer(manufacturer);
        }
        return optionalCar.get();
    }
}