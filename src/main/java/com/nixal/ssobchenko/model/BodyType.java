package com.nixal.ssobchenko.model;

import lombok.Getter;

@Getter
public enum BodyType {

    SEDAN(VehicleType.CAR),
    CROSSOVER(VehicleType.CAR),
    HATCHBACK(VehicleType.CAR),
    STATION_WAGON(VehicleType.CAR),
    COUPE(VehicleType.CAR),
    PICKUP(VehicleType.CAR),
    ROADSTER(VehicleType.CAR),
    SPORT_CAR(VehicleType.CAR),
    SUPERCAR(VehicleType.CAR),
    CABRIOLET(VehicleType.CAR),
    MUSCLE_CAR(VehicleType.CAR),
    MICRO(VehicleType.CAR),
    LIMOUSINE(VehicleType.CAR),
    STREET(VehicleType.MOTORCYCLE),
    CHOPPER(VehicleType.MOTORCYCLE),
    CRUISER(VehicleType.MOTORCYCLE),
    DIRT_BIKE(VehicleType.MOTORCYCLE),
    ENDURO_BIKE(VehicleType.MOTORCYCLE),
    MOTOCROSS(VehicleType.MOTORCYCLE),
    NAKED_BIKE(VehicleType.MOTORCYCLE),
    TOURING(VehicleType.MOTORCYCLE);

    private final VehicleType vehicleType;

    BodyType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public static BodyType[] getCarsBodyTypes() {
        return new BodyType[]{SEDAN, CROSSOVER, HATCHBACK, STATION_WAGON, COUPE,
                PICKUP, ROADSTER, SPORT_CAR, SUPERCAR, CABRIOLET, MUSCLE_CAR, MICRO, LIMOUSINE};
    }

    public static BodyType[] getMotorcyclesBodyTypes() {
        return new BodyType[]{STREET, CHOPPER, CRUISER, DIRT_BIKE,
                ENDURO_BIKE, MOTOCROSS, NAKED_BIKE, TOURING};
    }
}