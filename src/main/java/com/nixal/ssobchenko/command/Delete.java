package com.nixal.ssobchenko.command;

import com.nixal.ssobchenko.model.vehicle.Bus;
import com.nixal.ssobchenko.model.vehicle.Car;
import com.nixal.ssobchenko.model.vehicle.Motorcycle;
import com.nixal.ssobchenko.model.vehicle.Vehicle;
import com.nixal.ssobchenko.model.vehicle.VehicleType;
import com.nixal.ssobchenko.service.BusService;
import com.nixal.ssobchenko.service.CarService;
import com.nixal.ssobchenko.service.MotorcycleService;
import com.nixal.ssobchenko.util.UserInputUtil;

import java.util.ArrayList;
import java.util.List;

public class Delete implements Command {
    private static final CarService CAR_SERVICE = CarService.getInstance();
    private static final BusService BUS_SERVICE = BusService.getInstance();
    private static final MotorcycleService MOTORCYCLE_SERVICE = MotorcycleService.getInstance();

    @Override
    public void execute() {
        final VehicleType[] values = VehicleType.values();
        final List<String> names = VehicleType.getNames();
        final int userInput = UserInputUtil.getUserInput("What you want to delete:", names);
        final VehicleType value = values[userInput];

        switch (value) {
            case CAR -> carDeleteMethod();
            case BUS -> busDeleteMethod();
            case MOTORCYCLE -> motorcycleDeleteMethod();
        }
    }

    private <T extends Vehicle> List<String> getVehicles(List<T> vehicles) {
        final List<String> names = new ArrayList<>(vehicles.size() + 1);
        for (T vehicle : vehicles) {
            names.add(vehicle.toString());
        }
        names.add("Delete all");
        return names;
    }

    private void carDeleteMethod() {
        final List<Car> cars = CAR_SERVICE.getAll();
        if (cars.isEmpty()) {
            System.out.println("No created cars" + "\n");
        } else {
            final int userInputs = UserInputUtil
                    .getUserInput("Which car do you want to delete:", getVehicles(cars));
            if (userInputs < cars.size()) {
                CAR_SERVICE.delete(cars.get(userInputs));
                System.out.println("Car was deleted" + "\n");
            } else {
                CAR_SERVICE.deleteAll();
                System.out.println("Cars were deleted" + "\n");
            }
        }
    }

    private void busDeleteMethod() {
        final List<Bus> buses = BUS_SERVICE.getAll();
        if (buses.isEmpty()) {
            System.out.println("No created buses" + "\n");
        } else {
            final int userInputs = UserInputUtil
                    .getUserInput("Which bus do you want to delete:", getVehicles(buses));
            if (userInputs < buses.size()) {
                BUS_SERVICE.delete(buses.get(userInputs));
                System.out.println("Bus was deleted" + "\n");
            } else {
                BUS_SERVICE.deleteAll();
                System.out.println("Buses were deleted" + "\n");
            }
        }
    }

    private void motorcycleDeleteMethod() {
        final List<Motorcycle> motorcycles = MOTORCYCLE_SERVICE.getAll();
        if (motorcycles.isEmpty()) {
            System.out.println("No created motorcycles" + "\n");
        } else {
            final int userInputs = UserInputUtil
                    .getUserInput("Which motorcycle do you want to delete:", getVehicles(motorcycles));
            if (userInputs < motorcycles.size()) {
                MOTORCYCLE_SERVICE.delete(motorcycles.get(userInputs));
                System.out.println("Motorcycle was deleted" + "\n");
            } else {
                MOTORCYCLE_SERVICE.deleteAll();
                System.out.println("Motorcycles were deleted" + "\n");
            }
        }
    }
}