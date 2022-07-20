package com.nixal.ssobchenko;

import com.nixal.ssobchenko.model.Bus;
import com.nixal.ssobchenko.repository.BusesRepository;
import com.nixal.ssobchenko.service.BusService;
import com.nixal.ssobchenko.service.DiscountService;

public class Main {
    private static final BusService BUS_SERVICE = new BusService(new BusesRepository());

    public static void main(String[] args) {
        Bus one = BUS_SERVICE.createAndSave(1).get(0);
        BUS_SERVICE.printAll();

        DiscountService<Bus> discountService = new DiscountService<>(one);
        discountService.setPriceWithDiscount(10, 30);

        BUS_SERVICE.printAll();
    }
}