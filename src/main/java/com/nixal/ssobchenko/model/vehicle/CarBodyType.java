package com.nixal.ssobchenko.model.vehicle;

import java.util.ArrayList;
import java.util.List;

public enum CarBodyType {
    SEDAN, CROSSOVER, HATCHBACK, STATION_WAGON, COUPE, PICKUP,
    ROADSTER, SPORT_CAR, SUPERCAR, CABRIOLET, MUSCLE_CAR, MICRO, LIMOUSINE;

    public static List<String> getNames() {
        final CarBodyType[] values = CarBodyType.values();
        final List<String> names = new ArrayList<>(values.length);
        for (CarBodyType bodyType : values) {
            names.add(bodyType.name());
        }
        return names;
    }
}