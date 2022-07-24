package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.vehicle.Vehicle;

import java.math.BigDecimal;
import java.util.Random;

public class DiscountService<T extends Vehicle> {
    private static final Random RANDOM = new Random();
    private final T vehicle;

    public DiscountService(T vehicle) {
        this.vehicle = vehicle;
    }

    public void showInfo() {
        System.out.println(vehicle);
    }

    public void setPriceWithDiscount(int from, int to) {
        final String discount = String.valueOf((from + RANDOM.nextInt((to - from) + 1)) / 100.0);
        final BigDecimal discountSum = vehicle.getPrice().multiply(new BigDecimal(discount));
        vehicle.setPrice(vehicle.getPrice().subtract(discountSum));
    }

    public <K extends Number> void increasePrice(K number) {
        final String increaseNumber = String.valueOf(number);
        vehicle.setPrice(vehicle.getPrice().add(new BigDecimal(increaseNumber)));
    }
}