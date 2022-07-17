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

public record BusService(BusesRepository busesRepository) {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusService.class);
    private static final Random RANDOM = new Random();

    public List<Bus> createAndSaveBuses(int count) {
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
            busesRepository.saveAll(result);
            LOGGER.debug("Created bus {}", bus.getId());
        }
        return result;
    }

    public Bus createAndSaveBus(String model, BusManufacturer busManufacturer, String price, int numberOfSeats) {
        final Bus bus = new Bus(model, busManufacturer, new BigDecimal(price), numberOfSeats);
        LOGGER.debug("Created bus {}", bus.getId());
        busesRepository.save(bus);
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

    public boolean changeBus(Bus bus, String model, BusManufacturer busManufacturer, String price, int numberOfSeats) {
        bus.setModel(model);
        bus.setBusManufacturer(busManufacturer);
        bus.setPrice(new BigDecimal(price));
        bus.setNumberOfSeats(numberOfSeats);
        return busesRepository.update(bus);
    }

    public boolean deleteBus(Bus bus) {
        if (busesRepository.delete(bus.getId())) {
            LOGGER.debug("Deleted bus {}", bus.getId());
            return true;
        } else {
            LOGGER.debug("Bus wasn't deleted {}", bus.getId());
            return false;
        }
    }

    public void print(Bus bus, String name) {
        System.out.println(name + " = " + busesRepository.findById(bus.getId()));
    }

    public void printAll() {
        for (Bus bus : busesRepository.getAll()) {
            System.out.println(bus);
        }
    }
}