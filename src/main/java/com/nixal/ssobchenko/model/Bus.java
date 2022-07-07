package com.nixal.ssobchenko.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Bus extends Vehicle {
    private int numberOfSeats;

    public Bus(String model, Manufacturer manufacturer, BigDecimal price, int numberOfSeats) {
        super(model, manufacturer, price);
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String toString() {
        return "Bus{" + manufacturer + " with " +
                numberOfSeats + " seats" +
                ", id ='" + id + '\'' +
                ", model ='" + model + '\'' +
                ", price = " + price + "$" +
                '}';
    }
}