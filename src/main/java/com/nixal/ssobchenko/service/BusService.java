package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.vehicle.Bus;
import com.nixal.ssobchenko.model.vehicle.BusManufacturer;
import com.nixal.ssobchenko.repository.DBBusesRepository;
import com.nixal.ssobchenko.util.ApplicationContext;

import java.math.BigDecimal;

@ApplicationContext.Singleton
public class BusService extends VehicleService<Bus> {
    private static BusService instance;

    @ApplicationContext.Autowired
    private BusService(DBBusesRepository repository) {
        super(repository);
    }

    public static BusService getInstance() {
        if (instance == null) {
            instance = new BusService(DBBusesRepository.getInstance());
        }
        return instance;
    }

    public Bus createAndSaveBus(int model, BusManufacturer busManufacturer, String price, int numberOfSeats) {
        final Bus bus = new Bus.Builder(model, busManufacturer, new BigDecimal(price))
                .setNumberOfSeats(numberOfSeats)
                .build();
        LOGGER.debug("Created bus {}", bus.getId());
        repository.save(bus);
        return bus;
    }

    @Override
    protected Bus create() {
        return new Bus.Builder(
                RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextInt(10000)))
                .setNumberOfSeats(getRandomNumberOfSeats())
                .build();
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