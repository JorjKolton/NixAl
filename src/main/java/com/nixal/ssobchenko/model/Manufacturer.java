package com.nixal.ssobchenko.model;

import lombok.Getter;

@Getter
public enum Manufacturer {

    KIA(VehicleType.CAR),
    BMW(VehicleType.CAR),
    VOLKSWAGEN(VehicleType.CAR_BUS),
    OPEL(VehicleType.CAR),
    AUDI(VehicleType.CAR),
    TOYOTA(VehicleType.CAR_BUS),
    MERCEDES(VehicleType.CAR_BUS),
    RENAULT(VehicleType.CAR_BUS),
    FORD(VehicleType.CAR_BUS),
    DAIMLER(VehicleType.BUS),
    TATA(VehicleType.BUS),
    MAZDA(VehicleType.CAR_BUS),
    SCANIA(VehicleType.BUS),
    HEULIEZ(VehicleType.BUS),
    DAF(VehicleType.BUS),
    HERO(VehicleType.MOTORCYCLE),
    IRBIS(VehicleType.MOTORCYCLE),
    BIMOTA(VehicleType.MOTORCYCLE),
    BENELLI(VehicleType.MOTORCYCLE),
    KTM(VehicleType.MOTORCYCLE),
    SUZUKI(VehicleType.MOTORCYCLE),
    HARLEY_DAVIDSON(VehicleType.MOTORCYCLE),
    TRIUMPH(VehicleType.MOTORCYCLE),
    KAWASAKI(VehicleType.MOTORCYCLE),
    HONDA(VehicleType.MOTORCYCLE),
    DUCATI(VehicleType.MOTORCYCLE),
    YAMAHA(VehicleType.MOTORCYCLE);

    private final VehicleType vehicleType;

    Manufacturer(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public static Manufacturer[] getCarsManufacturers() {
        return new Manufacturer[]{KIA, BMW, VOLKSWAGEN, OPEL, FORD,
                AUDI, TOYOTA, MERCEDES, RENAULT, MAZDA};
    }

    public static Manufacturer[] getBusesManufacturers() {
        return new Manufacturer[]{VOLKSWAGEN, FORD, TOYOTA, MERCEDES, RENAULT, MAZDA,
                DAIMLER, TATA, SCANIA, HEULIEZ, DAF};
    }

    public static Manufacturer[] getMotorcyclesManufacturers() {
        return new Manufacturer[]{HERO, IRBIS, BIMOTA, BENELLI, KTM, SUZUKI,
                HARLEY_DAVIDSON, TRIUMPH, KAWASAKI, HONDA, DUCATI, YAMAHA};
    }
}