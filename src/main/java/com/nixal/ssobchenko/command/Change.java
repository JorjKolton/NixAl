package com.nixal.ssobchenko.command;

import com.nixal.ssobchenko.model.vehicle.*;
import com.nixal.ssobchenko.service.BusService;
import com.nixal.ssobchenko.service.CarService;
import com.nixal.ssobchenko.service.MotorcycleService;
import com.nixal.ssobchenko.util.UserInputUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Change implements Command {
    private static final CarService CAR_SERVICE = CarService.getInstance();
    private static final BusService BUS_SERVICE = BusService.getInstance();
    private static final MotorcycleService MOTORCYCLE_SERVICE = MotorcycleService.getInstance();

    private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public void execute() {
        final VehicleType[] values = VehicleType.values();
        final List<String> names = VehicleType.getNames();
        final int userInput = UserInputUtil.getUserInput("What you want to change:", names);
        final VehicleType value = values[userInput];

        switch (value) {
            case CAR -> carChangeMethod();
            case BUS -> busChangeMethod();
            case MOTORCYCLE -> motorcycleChangeMethod();
        }
    }

    private <T extends Vehicle> List<String> getVehicles(List<T> vehicles) {
        final List<String> names = new ArrayList<>(vehicles.size());
        for (T vehicle : vehicles) {
            names.add(vehicle.toString());
        }
        return names;
    }

    private void carChangeMethod() {
        final Car car;
        final List<Car> cars = CAR_SERVICE.getAll();
        if (cars.isEmpty()) {
            System.out.println("No created cars" + "\n");
        } else {
            final int userInputs = UserInputUtil
                    .getUserInput("Which car do you want to change:", getVehicles(cars));
            car = cars.get(userInputs);
            changeCar(car);
            System.out.println("Car was changed" + "\n");
        }
    }

    private void changeCar(Car car) {
        final CarManufacturer[] manufacturers = CarManufacturer.values();
        final List<String> manufacturersNames = CarManufacturer.getNames();

        final CarBodyType[] bodyTypes = CarBodyType.values();
        final List<String> bodyTypeNames = CarBodyType.getNames();

        System.out.println("Enter a new car model:");
        final int carModel = SCANNER.nextInt();

        final int userManufacturerInput = UserInputUtil
                .getUserInput("Choose a new car manufacturer:", manufacturersNames);
        final CarManufacturer carManufacturer = manufacturers[userManufacturerInput];

        System.out.println("Enter a new car price:");
        final String carPrice = SCANNER.next();

        final int userBodyTypeInput = UserInputUtil
                .getUserInput("Choose a new car body type:", bodyTypeNames);
        final CarBodyType carBodyType = bodyTypes[userBodyTypeInput];

        CAR_SERVICE.changeCar(car, carModel, carManufacturer, carPrice, carBodyType);
    }

    private void busChangeMethod() {
        final Bus bus;
        final List<Bus> buses = BUS_SERVICE.getAll();
        if (buses.isEmpty()) {
            System.out.println("No created buses" + "\n");
        } else {
            final int userInputs = UserInputUtil
                    .getUserInput("Which bus do you want to change:", getVehicles(buses));
            bus = buses.get(userInputs);
            changeBus(bus);
            System.out.println("Bus was changed" + "\n");
        }
    }

    private void changeBus(Bus bus) {
        final BusManufacturer[] manufacturers = BusManufacturer.values();
        final List<String> manufacturersNames = BusManufacturer.getNames();

        System.out.println("Enter a new bus model:");
        final int busModel = SCANNER.nextInt();

        final int userManufacturerInput = UserInputUtil
                .getUserInput("Choose a new bus manufacturer:", manufacturersNames);
        final BusManufacturer busManufacturer = manufacturers[userManufacturerInput];

        System.out.println("Enter a new bus price:");
        final String busPrice = SCANNER.next();

        System.out.println("Enter a new bus number of seats:");
        final int numberOfSeats = SCANNER.nextInt();

        BUS_SERVICE.changeBus(bus, busModel, busManufacturer, busPrice, numberOfSeats);
    }

    private void motorcycleChangeMethod() {
        final Motorcycle motorcycle;
        final List<Motorcycle> motorcycles = MOTORCYCLE_SERVICE.getAll();
        if (motorcycles.isEmpty()) {
            System.out.println("No created motorcycles" + "\n");
        } else {
            final int userInputs = UserInputUtil
                    .getUserInput("Which motorcycle do you want to change:", getVehicles(motorcycles));
            motorcycle = motorcycles.get(userInputs);
            changeMotorcycle(motorcycle);
            System.out.println("Motorcycle was changed" + "\n");
        }
    }

    private void changeMotorcycle(Motorcycle motorcycle) {
        final MotorcycleManufacturer[] manufacturers = MotorcycleManufacturer.values();
        final List<String> manufacturersNames = MotorcycleManufacturer.getNames();

        final MotorcycleBodyType[] bodyTypes = MotorcycleBodyType.values();
        final List<String> bodyTypeNames = MotorcycleBodyType.getNames();

        System.out.println("Enter a new motorcycle model:");
        final int motorcycleModel = SCANNER.nextInt();

        final int userManufacturerInput = UserInputUtil
                .getUserInput("Choose a new motorcycle manufacturer:", manufacturersNames);
        final MotorcycleManufacturer motorcycleManufacturer = manufacturers[userManufacturerInput];

        System.out.println("Enter a new motorcycle price:");
        final String motorcyclePrice = SCANNER.next();

        final int userBodyTypeInput = UserInputUtil
                .getUserInput("Choose a new motorcycle body type:", bodyTypeNames);
        final MotorcycleBodyType motorcycleBodyType = bodyTypes[userBodyTypeInput];

        MOTORCYCLE_SERVICE.changeMotorcycle(motorcycle, motorcycleModel, motorcycleManufacturer,
                motorcyclePrice, motorcycleBodyType);
    }
}