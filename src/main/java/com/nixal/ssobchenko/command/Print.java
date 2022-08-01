package com.nixal.ssobchenko.command;

import com.nixal.ssobchenko.model.vehicle.VehicleType;
import com.nixal.ssobchenko.service.BusService;
import com.nixal.ssobchenko.service.CarService;
import com.nixal.ssobchenko.service.MotorcycleService;
import com.nixal.ssobchenko.util.UserInputUtil;

import java.util.List;

public class Print implements Command {
    private static final CarService CAR_SERVICE = CarService.getInstance();
    private static final BusService BUS_SERVICE = BusService.getInstance();
    private static final MotorcycleService MOTORCYCLE_SERVICE = MotorcycleService.getInstance();

    @Override
    public void execute() {
        final VehicleType[] values = VehicleType.values();
        final List<String> names = VehicleType.getNames();
        final int userInput = UserInputUtil.getUserInput("What you want to print:", names);
        final VehicleType value = values[userInput];

        switch (value) {
            case CAR -> carPrintMethod();
            case BUS -> busPrintMethod();
            case MOTORCYCLE -> motorcyclePrintMethod();
        }
    }

    private void carPrintMethod() {
        if (CAR_SERVICE.getAll().isEmpty()) {
            System.out.println("No created cars");
        } else {
            CAR_SERVICE.printAll();
        }
    }

    private void busPrintMethod() {
        if (BUS_SERVICE.getAll().isEmpty()) {
            System.out.println("No created buses");
        } else {
            BUS_SERVICE.printAll();
        }
    }

    private void motorcyclePrintMethod() {
        if (MOTORCYCLE_SERVICE.getAll().isEmpty()) {
            System.out.println("No created motorcycles");
        } else {
            MOTORCYCLE_SERVICE.printAll();
        }
    }
}