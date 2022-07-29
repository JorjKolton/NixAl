package com.nixal.ssobchenko.model.vehicle;

import java.util.ArrayList;
import java.util.List;

public enum MotorcycleManufacturer {
    HERO, IRBIS, BIMOTA, BENELLI, KTM, SUZUKI,
    HARLEY_DAVIDSON, TRIUMPH, KAWASAKI, HONDA, DUCATI, YAMAHA;

    public static List<String> getNames() {
        final MotorcycleManufacturer[] values = MotorcycleManufacturer.values();
        final List<String> names = new ArrayList<>(values.length);
        for (MotorcycleManufacturer manufacturer : values) {
            names.add(manufacturer.name());
        }
        return names;
    }
}