package com.nixal.ssobchenko.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Bus extends Vehicle {
    private int numberOfSeats;
    private BusManufacturer busManufacturer;

    public Bus(String model, BusManufacturer busManufacturer, BigDecimal price, int numberOfSeats) {
        super(model, price);
        this.numberOfSeats = numberOfSeats;
        this.busManufacturer = busManufacturer;
    }

    @Override
    public String toString() {
        return "Bus{" + busManufacturer + " with " +
                numberOfSeats + " seats" +
                ", id ='" + id + '\'' +
                ", model ='" + model + '\'' +
                ", price = " + price + "$" +
                '}';
    }
}