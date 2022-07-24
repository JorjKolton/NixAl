package com.nixal.ssobchenko;

import com.nixal.ssobchenko.model.Garage;
import com.nixal.ssobchenko.model.vehicle.Bus;
import com.nixal.ssobchenko.model.vehicle.Vehicle;
import com.nixal.ssobchenko.repository.BusesRepository;
import com.nixal.ssobchenko.service.BusService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    private static final BusService BUS_SERVICE = new BusService(new BusesRepository());

    public static void main(String[] args) throws InterruptedException {
        Bus one = BUS_SERVICE.createAndSave(1).get(0);
        Bus two = BUS_SERVICE.createAndSave(1).get(0);
        Bus three = BUS_SERVICE.createAndSave(1).get(0);
        Bus four = BUS_SERVICE.createAndSave(1).get(0);
        Bus five = BUS_SERVICE.createAndSave(1).get(0);

        Garage<Vehicle> garage = new Garage<>();
        garage.add(one, 1);
        garage.add(two, 2);
        garage.add(three, 3);
        garage.add(four, 4);

        Iterator<Vehicle> vi = garage.iterator();
        while (vi.hasNext()) {
            System.out.println(vi.next());
        }

        System.out.println("--------------");

        garage.showLastRestylingData();

        Thread.sleep(5000);
        garage.add(five, 5);

        garage.showLastRestylingData();

        System.out.println("----------------");

        List<Bus> buses = new ArrayList<>(BUS_SERVICE.createAndSave(10));
        buses.sort(new Bus.PriceComparator().reversed());

        for (Bus b : buses) {
            System.out.println(b.getPrice());
        }
    }
}