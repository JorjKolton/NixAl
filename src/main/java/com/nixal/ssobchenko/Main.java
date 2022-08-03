package com.nixal.ssobchenko;

import com.nixal.ssobchenko.model.vehicle.CarBodyType;
import com.nixal.ssobchenko.model.vehicle.CarManufacturer;
import com.nixal.ssobchenko.model.vehicle.VehicleType;
import com.nixal.ssobchenko.service.CarService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final CarService CAR_SERVICE = CarService.getInstance();

    public static void main(String[] args) {
        final Map<String,Object> constructor = new HashMap<>();
        final List<String> details = Arrays.asList("Radiator", "Spring", "Starter", "Injection", "Fuse", "Filter");
        CAR_SERVICE.createAndSave(10).forEach(car -> car.setDetails(details));

        constructor.put("Model", 145);
        constructor.put("Manufacturer", CarManufacturer.AUDI);
        constructor.put("Price", new BigDecimal("45000"));
        constructor.put("BodyType", CarBodyType.COUPE);
        constructor.put("VehicleType", VehicleType.CAR);

//        CAR_SERVICE.showVehiclesExpensiveThan(800000);
//        System.out.println(CAR_SERVICE.getSumOfVehiclesPrices());
//        CAR_SERVICE.getMap().entrySet().forEach(System.out::println);
//        System.out.println(CAR_SERVICE.getStatisticsOfAllVehiclesPrices());
//        System.out.println(CAR_SERVICE.isDetailInStock("starter"));
//        System.out.println(CAR_SERVICE.areAllVehiclesHavePrices.test(CAR_SERVICE.getAll()));
//        System.out.println(CAR_SERVICE.getNewVehicle.apply(constructor));
    }
}