package com.nixal.ssobchenko.model.vehicle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Buses")
public class Bus extends Vehicle {
    private int numberOfSeats;
    @Column(name = "manufacturer")
    @Enumerated(EnumType.STRING)
    private BusManufacturer busManufacturer;

    private Bus(Builder builder) {
        super(builder.id, builder.model, builder.price, VehicleType.BUS, builder.created);
        this.numberOfSeats = builder.numberOfSeats;
        this.busManufacturer = builder.busManufacturer;
        this.count = builder.count;
    }

    public static class Builder {
        private final int model;
        private final BusManufacturer busManufacturer;
        private final BigDecimal price;
        private int numberOfSeats;
        private int count;
        private String id;
        private LocalDateTime created;

        public Builder(int model, BusManufacturer busManufacturer, BigDecimal price) {
            this.model = model;
            this.busManufacturer = busManufacturer;
            this.price = price;
            id = UUID.randomUUID().toString();
            created = LocalDateTime.now();
        }

        public Bus.Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Bus.Builder setCreated(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public Builder setNumberOfSeats(int numberOfSeats) {
            this.numberOfSeats = numberOfSeats;
            return this;
        }

        public Builder setCount(int count) {
            if (count < 0) {
                throw new IllegalArgumentException("Count can't be less than zero");
            }
            this.count = count;
            return this;
        }

        public Bus build() {
            if (busManufacturer == null || price == null) {
                throw new IllegalArgumentException("BusManufacturer or price can't be null");
            }
            return new Bus(this);
        }
    }

    public static class NumberOfSeatsComparator implements Comparator<Bus> {

        @Override
        public int compare(Bus o1, Bus o2) {
            return Integer.compare(o1.getNumberOfSeats(), o2.getNumberOfSeats());
        }
    }

    @Override
    public String toString() {
        return "Bus{" + busManufacturer + " with " +
                numberOfSeats + " seats" +
                ", id ='" + id + '\'' +
                ", model ='" + model + '\'' +
                ", price = " + price + currency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bus bus = (Bus) o;
        return id != null && Objects.equals(id, bus.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}