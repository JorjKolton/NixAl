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
        List<Bus> buses = new ArrayList<>(BUS_SERVICE.createAndSave(10));
        buses.sort(new Bus.PriceComparator().reversed()
                .thenComparing(new Vehicle.ModelComparator()
                        .thenComparing(new Vehicle.IdComparator())));

        for (Bus b : buses) {
            System.out.println(b.getPrice());
        }
    }
}