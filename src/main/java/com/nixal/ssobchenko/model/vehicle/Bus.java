package com.nixal.ssobchenko.model.vehicle;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Comparator;

@Getter
@Setter
public class Bus extends Vehicle {
    private int numberOfSeats;
    private BusManufacturer busManufacturer;

    public Bus(int model, BusManufacturer busManufacturer, BigDecimal price, int numberOfSeats) {
        super(model, price, VehicleType.BUS);
        this.numberOfSeats = numberOfSeats;
        this.busManufacturer = busManufacturer;
    }

    public static class NumberOfSeatsComparator implements Comparator<Bus> {

        @Override
        public int compare(Bus o1, Bus o2) {
            return Integer.compare(o1.getNumberOfSeats(), o2.getNumberOfSeats());
        }
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