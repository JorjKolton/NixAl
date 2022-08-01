package com.nixal.ssobchenko.command;

import com.nixal.ssobchenko.model.vehicle.VehicleType;
import com.nixal.ssobchenko.service.BusService;
import com.nixal.ssobchenko.service.CarService;
import com.nixal.ssobchenko.service.MotorcycleService;
import com.nixal.ssobchenko.util.UserInputUtil;

import java.util.List;

public class Create implements Command {
    private static final CarService CAR_SERVICE = CarService.getInstance();
    private static final BusService BUS_SERVICE = BusService.getInstance();
    private static final MotorcycleService MOTORCYCLE_SERVICE = MotorcycleService.getInstance();

    @Override
    public void execute() {
        final VehicleType[] values = VehicleType.values();
        final List<String> names = VehicleType.getNames();
        final int userInput = UserInputUtil.getUserInput("What you want to create:", names);
        final VehicleType value = values[userInput];

        switch (value) {
            case CAR -> {
                CAR_SERVICE.createAndSave(1);
                System.out.println("Car was created" + "\n");
            }
            case BUS -> {
                BUS_SERVICE.createAndSave(1);
                System.out.println("Bus was created" + "\n");
            }
            case MOTORCYCLE -> {
                MOTORCYCLE_SERVICE.createAndSave(1);
                System.out.println("Motorcycle was created" + "\n");
            }
            default -> throw new IllegalArgumentException("Cannot build " + value);
        }
    }
}