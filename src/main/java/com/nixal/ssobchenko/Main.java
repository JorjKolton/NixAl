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
        // TODO: 17.07.2022
    }
}