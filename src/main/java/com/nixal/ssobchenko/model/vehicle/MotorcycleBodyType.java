package com.nixal.ssobchenko.model.vehicle;

import java.util.ArrayList;
import java.util.List;

public enum MotorcycleBodyType {
    STREET, CHOPPER, CRUISER, DIRT_BIKE, ENDURO_BIKE, MOTOCROSS, NAKED_BIKE, TOURING;

    public static List<String> getNames() {
        final MotorcycleBodyType[] values = MotorcycleBodyType.values();
        final List<String> names = new ArrayList<>(values.length);
        for (MotorcycleBodyType bodyType : values) {
            names.add(bodyType.name());
        }
        return names;
    }
}