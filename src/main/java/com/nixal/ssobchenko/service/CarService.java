package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.vehicle.Car;
import com.nixal.ssobchenko.model.vehicle.CarBodyType;
import com.nixal.ssobchenko.model.vehicle.CarManufacturer;
import com.nixal.ssobchenko.repository.HibernateCarsRepository;
import com.nixal.ssobchenko.util.ApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@ApplicationContext.Singleton
public class CarService extends VehicleService<Car> {
    private static CarService instance;

    @ApplicationContext.Autowired
    private CarService(HibernateCarsRepository repository) {
        super(repository);
    }

    public static CarService getInstance() {
        if (instance == null) {
            instance = new CarService(HibernateCarsRepository.getInstance());
        }
        return instance;
    }

    public Car createAndSaveCar(int model, CarManufacturer carManufacturer, String price, CarBodyType carBodyType) {
        final Car car = new Car.Builder(model, carManufacturer, new BigDecimal(price))
                .setCarBodyType(carBodyType)
                .build();
        LOGGER.debug("Created car {}", car.getId());
        repository.save(car);
        return car;
    }

    @Override
    protected Car create() {
        return new Car.Builder(
                RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextInt(5000)))
                .setCarBodyType(getRandomBodyType())
                .build();
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

    public boolean changeCar(Car car, int model, CarManufacturer carManufacturer, String price, CarBodyType carBodyType) {
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

    public final Function<Map<String, String>, Car> getCarFromString =
            map -> {
                Car car = new Car.Builder(
                        Integer.parseInt(map.get("model")),
                        CarManufacturer.valueOf(map.get("manufacturer").toUpperCase()),
                        new BigDecimal(map.get("price")))
                        .setCarBodyType(CarBodyType.valueOf(map.get("bodyType").toUpperCase()))
                        .build();
                car.setCreated(LocalDateTime.parse(map.get("created"),
                        DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX")));
                car.getEngine().setVolume(Integer.parseInt(map.get("volume")));
                car.getEngine().setBrand(map.get("brand"));
                return car;
            };
}