package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.Bus;
import com.nixal.ssobchenko.model.BusManufacturer;
import com.nixal.ssobchenko.repository.BusesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusService.class);
    private static final Random RANDOM = new Random();
    private static final BusesRepository BUSES_REPOSITORY = new BusesRepository();

    public List<Bus> createBuses(int count) {
        int maxPrice = 900000;
        int maxModelsCount = 1000;
        List<Bus> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Bus bus = new Bus(
                    "" + RANDOM.nextInt(maxModelsCount),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextInt(maxPrice)),
                    getRandomNumberOfSeats()
            );
            result.add(bus);
            LOGGER.debug("Created bus {}", bus.getId());
        }
        return result;
    }

    public Bus createBus(BusManufacturer busManufacturer, String price, int numberOfSeats) {
        int maxModelsCount = 1000;
        final Bus bus = new Bus(
                "" + RANDOM.nextInt(maxModelsCount),
                busManufacturer, new BigDecimal(price), numberOfSeats);
        LOGGER.debug("Created bus {}", bus.getId());
        return bus;
    }

    private BusManufacturer getRandomManufacturer() {
        final BusManufacturer[] values = BusManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private int getRandomNumberOfSeats() {
        int minSeats = 7;
        int maxSeats = 101;
        return RANDOM.nextInt(maxSeats - minSeats) + minSeats;
    }

    public void saveBuses(List<Bus> buses) {
        BUSES_REPOSITORY.create(buses);
    }

    public void saveBus(Bus bus) {
        BUSES_REPOSITORY.create(bus);
    }

    public void changeBus(Bus bus, String model, BusManufacturer busManufacturer, String price, int numberOfSeats) {
        bus.setModel(model);
        bus.setBusManufacturer(busManufacturer);
        bus.setPrice(new BigDecimal(price));
        bus.setNumberOfSeats(numberOfSeats);
    }

    public void deleteBus(Bus bus) {
        if (BUSES_REPOSITORY.delete(bus.getId())) {
            LOGGER.debug("Deleted bus {}", bus.getId());
        } else {
            LOGGER.debug("Bus wasn't deleted {}", bus.getId());
        }
    }

    public void print(Bus bus, String name) {
        System.out.println(name + " = " + BUSES_REPOSITORY.getById(bus.getId()));
    }

    public void printAll() {
        for (Bus bus : BUSES_REPOSITORY.getAll()) {
            System.out.println(bus);
        }
    }
}