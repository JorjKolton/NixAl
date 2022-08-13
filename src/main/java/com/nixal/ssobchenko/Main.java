package com.nixal.ssobchenko;

import com.nixal.ssobchenko.model.vehicle.Bus;
import com.nixal.ssobchenko.model.vehicle.BusManufacturer;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        Bus bus = new Bus.Builder(505, BusManufacturer.MERCEDES,new BigDecimal("27000"))
                .setNumberOfSeats(24)
                .setCount(5)
                .build();

        System.out.println(bus);
    }
}