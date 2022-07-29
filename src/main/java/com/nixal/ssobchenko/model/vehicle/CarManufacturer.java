package com.nixal.ssobchenko.model.vehicle;

import java.util.ArrayList;
import java.util.List;

public enum CarManufacturer {
    KIA, BMW, VOLKSWAGEN, OPEL, FORD, AUDI, TOYOTA, MERCEDES, RENAULT, MAZDA;

    public static List<String> getNames() {
        final CarManufacturer[] values = CarManufacturer.values();
        final List<String> names = new ArrayList<>(values.length);
        for (CarManufacturer manufacturer : values) {
            names.add(manufacturer.name());
        }
        return names;
    }
}