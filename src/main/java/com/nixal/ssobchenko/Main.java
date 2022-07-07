package com.nixal.ssobchenko;

import com.nixal.ssobchenko.model.BodyType;
import com.nixal.ssobchenko.model.Bus;
import com.nixal.ssobchenko.model.Car;
import com.nixal.ssobchenko.model.Manufacturer;
import com.nixal.ssobchenko.model.Motorcycle;
import com.nixal.ssobchenko.service.BusService;
import com.nixal.ssobchenko.service.CarService;
import com.nixal.ssobchenko.service.MotorcycleService;

public class Main {
    private static final CarService CAR_SERVICE = new CarService();
    private static final BusService BUS_SERVICE = new BusService();
    private static final MotorcycleService MOTORCYCLE_SERVICE = new MotorcycleService();

    public static void main(String[] args) {
        System.out.println("Create all products:");

        Car car1 = CAR_SERVICE.createCar(Manufacturer.AUDI, "26700", BodyType.COUPE);
        Car car2 = CAR_SERVICE.createCar(Manufacturer.BMW, "35000", BodyType.CABRIOLET);
        CAR_SERVICE.saveCar(car1);
        CAR_SERVICE.saveCar(car2);
        CAR_SERVICE.print(car1, "Car1");
        CAR_SERVICE.print(car2, "Car2");

        Motorcycle motorcycle1 = MOTORCYCLE_SERVICE.createMotorcycle
                                (Manufacturer.DUCATI, "17000", BodyType.DIRT_BIKE);
        Motorcycle motorcycle2 = MOTORCYCLE_SERVICE.createMotorcycle
                                (Manufacturer.HONDA, "8000", BodyType.CRUISER);
        MOTORCYCLE_SERVICE.saveMotorcycle(motorcycle1);
        MOTORCYCLE_SERVICE.saveMotorcycle(motorcycle2);
        MOTORCYCLE_SERVICE.print(motorcycle1, "Motorcycle1");
        MOTORCYCLE_SERVICE.print(motorcycle2, "Motorcycle2");

        Bus bus1 = BUS_SERVICE.createBus(Manufacturer.HEULIEZ, "150000", 52);
        Bus bus2 = BUS_SERVICE.createBus(Manufacturer.SCANIA, "183000", 43);
        BUS_SERVICE.saveBus(bus1);
        BUS_SERVICE.saveBus(bus2);
        BUS_SERVICE.print(bus1, "Bus1");
        BUS_SERVICE.print(bus2, "Bus2");

        System.out.println("Change car1:");
        System.out.println("Car1 before changes = " + car1);
        CAR_SERVICE.changeCarBodyType(car1, BodyType.SEDAN);
        System.out.println("Car1 after changes = " + car1);

        System.out.println("Cars before delete:");
        CAR_SERVICE.printAll();
        CAR_SERVICE.deleteCar(car1);
        System.out.println("Cars after delete:");
        CAR_SERVICE.printAll();
    }
}