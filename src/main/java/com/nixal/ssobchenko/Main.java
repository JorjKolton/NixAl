package com.nixal.ssobchenko;

import com.nixal.ssobchenko.model.TreeOfVehicles;
import com.nixal.ssobchenko.model.vehicle.Car;
import com.nixal.ssobchenko.service.CarService;
import com.nixal.ssobchenko.util.UserInputStart;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        UserInputStart userInputStart = new UserInputStart();
        TreeOfVehicles<Car> tree = new TreeOfVehicles<>();
        CarService carService = CarService.getInstance();

        List<Car> cars = new ArrayList<>(carService.createAndSave(15));
        for (Car car : cars) {
            tree.add(car);
        }

        tree.showLeftBranchTotalPrice();
        tree.showRightBranchTotalPrice();

        tree.print();

//        userInputStart.start();
    }
}