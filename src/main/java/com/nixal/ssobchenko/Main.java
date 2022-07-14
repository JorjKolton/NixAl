package com.nixal.ssobchenko;

import com.nixal.ssobchenko.model.CarBodyType;
import com.nixal.ssobchenko.model.Bus;
import com.nixal.ssobchenko.model.BusManufacturer;
import com.nixal.ssobchenko.model.Car;
import com.nixal.ssobchenko.model.CarManufacturer;
import com.nixal.ssobchenko.model.Motorcycle;
import com.nixal.ssobchenko.model.MotorcycleBodyType;
import com.nixal.ssobchenko.model.MotorcycleManufacturer;
import com.nixal.ssobchenko.repository.BusesRepository;
import com.nixal.ssobchenko.repository.CarsRepository;
import com.nixal.ssobchenko.repository.MotorcyclesRepository;
import com.nixal.ssobchenko.service.BusService;
import com.nixal.ssobchenko.service.CarService;
import com.nixal.ssobchenko.service.MotorcycleService;

public class Main {
    private static final CarService CAR_SERVICE = new CarService(new CarsRepository());
    private static final BusService BUS_SERVICE = new BusService(new BusesRepository());
    private static final MotorcycleService MOTORCYCLE_SERVICE = new MotorcycleService(new MotorcyclesRepository());

    public static void main(String[] args) {
        System.out.println("Create all products:");

        Car car1 = CAR_SERVICE.createAndSaveCar("100", CarManufacturer.AUDI, "26700", CarBodyType.COUPE);
        Car car2 = CAR_SERVICE.createAndSaveCar("700", CarManufacturer.BMW, "35000", CarBodyType.CABRIOLET);
        CAR_SERVICE.print(car1, "Car1");
        CAR_SERVICE.print(car2, "Car2");

        Motorcycle motorcycle1 = MOTORCYCLE_SERVICE.createAndSaveMotorcycle("100",
                MotorcycleManufacturer.DUCATI, "17000", MotorcycleBodyType.CHOPPER);
        Motorcycle motorcycle2 = MOTORCYCLE_SERVICE.createAndSaveMotorcycle("101",
                MotorcycleManufacturer.KAWASAKI, "8000", MotorcycleBodyType.MOTOCROSS);
        MOTORCYCLE_SERVICE.print(motorcycle1, "Motorcycle1");
        MOTORCYCLE_SERVICE.print(motorcycle2, "Motorcycle2");

        Bus bus1 = BUS_SERVICE.createAndSaveBus("890", BusManufacturer.SCANIA, "150000", 52);
        Bus bus2 = BUS_SERVICE.createAndSaveBus("318", BusManufacturer.TOYOTA, "183000", 43);
        BUS_SERVICE.print(bus1, "Bus1");
        BUS_SERVICE.print(bus2, "Bus2");

        System.out.println("Change car1:");
        System.out.println("Car1 before changes = " + car1);
        CAR_SERVICE.changeCar(car1, car1.getModel(), CarManufacturer.AUDI, "27000", CarBodyType.SEDAN);
        System.out.println("Car1 after changes = " + car1);

        System.out.println("Cars before delete:");
        CAR_SERVICE.printAll();
        CAR_SERVICE.deleteCar(car1);
        System.out.println("Cars after delete:");
        CAR_SERVICE.printAll();
    }
}