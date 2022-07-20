package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.Motorcycle;
import com.nixal.ssobchenko.model.MotorcycleBodyType;
import com.nixal.ssobchenko.model.MotorcycleManufacturer;
import com.nixal.ssobchenko.repository.CrudRepository;

import java.math.BigDecimal;

public class MotorcycleService extends VehicleService<Motorcycle> {

    public MotorcycleService(CrudRepository<Motorcycle> repository) {
        super(repository);
    }

    public Motorcycle createAndSaveMotorcycle(String model, MotorcycleManufacturer motorcycleManufacturer, String price,
                                              MotorcycleBodyType bodyType) {
        final Motorcycle motorcycle = new Motorcycle(model, motorcycleManufacturer, new BigDecimal(price), bodyType);
        LOGGER.debug("Created motorcycle {}", motorcycle.getId());
        repository.save(motorcycle);
        return motorcycle;
    }

    @Override
    protected Motorcycle create() {
        return new Motorcycle(
                "" + RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextInt(100000)),
                getRandomBodyType()
        );
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

    public boolean changeMotorcycle(Motorcycle motorcycle, String model, MotorcycleManufacturer manufacturer, String price,
                                 MotorcycleBodyType bodyType) {
        motorcycle.setModel(model);
        motorcycle.setMotorcycleManufacturer(manufacturer);
        motorcycle.setPrice(new BigDecimal(price));
        motorcycle.setBodyType(bodyType);
        return repository.update(motorcycle);
    }
}