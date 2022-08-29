package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.vehicle.Motorcycle;
import com.nixal.ssobchenko.model.vehicle.MotorcycleBodyType;
import com.nixal.ssobchenko.model.vehicle.MotorcycleManufacturer;
import com.nixal.ssobchenko.repository.DBMotorcyclesRepository;
import com.nixal.ssobchenko.util.ApplicationContext;

import java.math.BigDecimal;

@ApplicationContext.Singleton
public class MotorcycleService extends VehicleService<Motorcycle> {
    private static MotorcycleService instance;

    @ApplicationContext.Autowired
    private MotorcycleService(DBMotorcyclesRepository repository) {
        super(repository);
    }

    public static MotorcycleService getInstance() {
        if (instance == null) {
            instance = new MotorcycleService(DBMotorcyclesRepository.getInstance());
        }
        return instance;
    }

    public Motorcycle createAndSaveMotorcycle(int model, MotorcycleManufacturer motorcycleManufacturer, String price,
                                              MotorcycleBodyType bodyType) {
        final Motorcycle motorcycle = new Motorcycle.Builder(model, motorcycleManufacturer, new BigDecimal(price))
                .setMotorcycleBodyType(bodyType)
                .build();
        LOGGER.debug("Created motorcycle {}", motorcycle.getId());
        repository.save(motorcycle);
        return motorcycle;
    }

    @Override
    protected Motorcycle create() {
        return new Motorcycle.Builder(
                RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextInt(1000)))
                .setMotorcycleBodyType(getRandomBodyType())
                .build();
    }

    private MotorcycleManufacturer getRandomManufacturer() {
        final MotorcycleManufacturer[] values = MotorcycleManufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private MotorcycleBodyType getRandomBodyType() {
        final MotorcycleBodyType[] values = MotorcycleBodyType.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public boolean changeMotorcycle(Motorcycle motorcycle, int model, MotorcycleManufacturer manufacturer, String price,
                                    MotorcycleBodyType bodyType) {
        motorcycle.setModel(model);
        motorcycle.setMotorcycleManufacturer(manufacturer);
        motorcycle.setPrice(new BigDecimal(price));
        motorcycle.setBodyType(bodyType);
        return repository.update(motorcycle);
    }
}