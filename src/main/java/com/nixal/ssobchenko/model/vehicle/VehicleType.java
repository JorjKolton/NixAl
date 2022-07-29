package com.nixal.ssobchenko.model.vehicle;

import java.util.ArrayList;
import java.util.List;

public enum VehicleType {
    CAR, BUS, MOTORCYCLE;

    public static List<String> getNames() {
        final VehicleType[] values = VehicleType.values();
        final List<String> names = new ArrayList<>(values.length);
        for (VehicleType vehicleType : values) {
            names.add(vehicleType.name());
        }
        return names;
    }
}