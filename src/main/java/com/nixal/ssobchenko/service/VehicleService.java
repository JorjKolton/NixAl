package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.vehicle.*;
import com.nixal.ssobchenko.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class VehicleService<T extends Vehicle> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);
    protected static final Random RANDOM = new Random();
    protected final CrudRepository<T> repository;
    private static final String MODEL = "Model";
    private static final String MANUFACTURER = "Manufacturer";
    private static final String PRICE = "Price";

    protected VehicleService(CrudRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> createAndSave(int count) {
        List<T> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final T vehicle = create();
            result.add(vehicle);
            LOGGER.debug("Created vehicle {}", vehicle.getId());
        }
        repository.saveAll(result);
        return result;
    }

    protected abstract T create();

    public List<T> getAll() {
        return repository.getAll();
    }

    public void showVehiclesExpensiveThan(int price) {
        final List<T> vehicles = getAll();
        final List<T> expensiveThanVehicles;
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles yet");
        } else {
            expensiveThanVehicles = vehicles.stream()
                    .filter(car -> car.getPrice().doubleValue() > price)
                    .toList();
            if (expensiveThanVehicles.isEmpty()) {
                System.out.printf("No vehicles expensive than %d", price);
            } else {
                expensiveThanVehicles.forEach(System.out::println);
            }
        }
    }

    public Optional<BigDecimal> getSumOfVehiclesPrices() {
        final List<T> vehicles = getAll();
        if (vehicles.isEmpty()) {
            return Optional.empty();
        } else {
            return repository.getAll().stream()
                    .map(Vehicle::getPrice)
                    .reduce(BigDecimal::add);
        }
    }

    public Map<String, VehicleType> getMap() {
        return repository.getAll().stream()
                .distinct()
                .sorted(new Vehicle.ModelComparator())
                .collect(Collectors.toMap(
                        Vehicle::getId,
                        Vehicle::getType,
                        (item, identicalItem) -> item,
                        LinkedHashMap::new
                ));
    }

    public DoubleSummaryStatistics getStatisticsOfAllVehiclesPrices() {
        return repository.getAll().stream()
                .mapToDouble(vehicle -> vehicle.getPrice().doubleValue())
                .summaryStatistics();
    }

    public boolean isDetailInStock(String detail) {
        return repository.getAll().stream()
                .flatMap(vehicle -> vehicle.getDetails().stream())
                .distinct()
                .anyMatch(s -> s.equalsIgnoreCase(detail));
    }

    public final Predicate<List<T>> areAllVehiclesHavePrices =
            list -> list.stream()
                    .mapToDouble(vehicle -> vehicle.getPrice().doubleValue())
                    .allMatch(p -> p > 0);

    public final Function<Map<String, Object>, Vehicle> getNewVehicle =
            map -> {
                Vehicle vehicle;
                if (map.containsValue(VehicleType.CAR)) {
                    vehicle = new Car(
                            (Integer) map.get(MODEL),
                            (CarManufacturer) map.get(MANUFACTURER),
                            (BigDecimal) map.get(PRICE),
                            (CarBodyType) map.get("BodyType")
                    );
                } else if (map.containsValue(VehicleType.BUS)) {
                    vehicle = new Bus(
                            (Integer) map.get(MODEL),
                            (BusManufacturer) map.get(MANUFACTURER),
                            (BigDecimal) map.get(PRICE),
                            (Integer) map.get("Seats")
                    );
                } else {
                    vehicle = new Motorcycle(
                            (Integer) map.get(MODEL),
                            (MotorcycleManufacturer) map.get(MANUFACTURER),
                            (BigDecimal) map.get(PRICE),
                            (MotorcycleBodyType) map.get("BodyType")
                    );
                }
                return vehicle;
            };

    public boolean delete(T vehicle) {
        if (repository.delete(vehicle.getId())) {
            LOGGER.debug("Deleted vehicle {}", vehicle.getId());
            return true;
        } else {
            LOGGER.debug("Vehicle wasn't deleted {}", vehicle.getId());
            return false;
        }
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void print(T vehicle, String name) {
        repository.findById(vehicle.getId()).ifPresentOrElse(
                foundVehicle -> System.out.println(name + " = " + foundVehicle),
                () -> System.out.println("Cannot find vehicle with id " + vehicle.getId())
        );
    }

    public void printAll() {
        for (T vehicle : repository.getAll()) {
            System.out.println(vehicle);
        }
    }
}