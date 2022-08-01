package com.nixal.ssobchenko.model.vehicle;

import java.util.ArrayList;
import java.util.List;

public enum BusManufacturer {
    VOLKSWAGEN, FORD, TOYOTA, MERCEDES, RENAULT, MAZDA,
    DAIMLER, TATA, SCANIA, HEULIEZ, DAF;

    public static List<String> getNames() {
        final BusManufacturer[] values = BusManufacturer.values();
        final List<String> names = new ArrayList<>(values.length);
        for (BusManufacturer manufacturer : values) {
            names.add(manufacturer.name());
        }
        return names;
    }
}