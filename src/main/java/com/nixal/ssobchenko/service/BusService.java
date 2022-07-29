package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.vehicle.Bus;
import com.nixal.ssobchenko.model.vehicle.BusManufacturer;
import com.nixal.ssobchenko.repository.BusesRepository;

import java.math.BigDecimal;

public class BusService extends VehicleService<Bus>{
    private static BusService instance;

    private BusService(BusesRepository repository) {
        super(repository);
    }

    public static BusService getInstance() {
        if (instance == null) {
            instance = new BusService(BusesRepository.getInstance());
        }
        return instance;
    }

    public Bus createAndSaveBus(int model, BusManufacturer busManufacturer, String price, int numberOfSeats) {
        final Bus bus = new Bus(model, busManufacturer, new BigDecimal(price), numberOfSeats);
        LOGGER.debug("Created bus {}", bus.getId());
        repository.save(bus);
        return bus;
    }

    @Override
    protected Bus create() {
        return new Bus(
                RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextInt(900000)),
                getRandomNumberOfSeats()
        );
    }

    public boolean changeBus(Bus bus, int model, BusManufacturer busManufacturer, String price, int numberOfSeats) {
        bus.setModel(model);
        bus.setBusManufacturer(busManufacturer);
        bus.setPrice(new BigDecimal(price));
        bus.setNumberOfSeats(numberOfSeats);
        return repository.update(bus);
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
}