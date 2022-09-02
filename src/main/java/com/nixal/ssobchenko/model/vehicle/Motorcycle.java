package com.nixal.ssobchenko.model.vehicle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Motorcycles")
public class Motorcycle extends Vehicle {
    @Column(name = "body_type")
    @Enumerated(EnumType.STRING)
    private MotorcycleBodyType bodyType;
    @Column(name = "manufacturer")
    @Enumerated(EnumType.STRING)
    private MotorcycleManufacturer motorcycleManufacturer;

    private Motorcycle(Builder builder) {
        super(builder.id, builder.model, builder.price, VehicleType.MOTORCYCLE, builder.created);
        this.bodyType = builder.bodyType;
        this.motorcycleManufacturer = builder.motorcycleManufacturer;
    }

    public static class Builder {
        private final int model;
        private final MotorcycleManufacturer motorcycleManufacturer;
        private final BigDecimal price;
        private MotorcycleBodyType bodyType;
        private int count;
        private String id;
        private LocalDateTime created;

        public Builder(int model, MotorcycleManufacturer motorcycleManufacturer, BigDecimal price) {
            this.model = model;
            this.motorcycleManufacturer = motorcycleManufacturer;
            this.price = price;
            id = UUID.randomUUID().toString();
            created = LocalDateTime.now();
        }

        public Builder setMotorcycleBodyType(MotorcycleBodyType bodyType) {
            if (bodyType == null || bodyType.name().length() > 20) {
                throw new IllegalArgumentException("BodyType can't be null or longer than 20 characters");
            }
            this.bodyType = bodyType;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setCreated(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public Builder setCount(int count) {
            if (count < 0) {
                throw new IllegalArgumentException("Count can't be less than zero");
            }
            this.count = count;
            return this;
        }

        public Motorcycle build() {
            if (motorcycleManufacturer == null || price == null) {
                throw new IllegalArgumentException("CarManufacturer or price can't be null");
            }
            return new Motorcycle(this);
        }
    }

    @Override
    public String toString() {
        return "Motorcycle {" + bodyType + " from " +
                motorcycleManufacturer + ", id = '" + id + '\'' +
                ", model = '" + model + '\'' +
                ", price = " + price + currency + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Motorcycle that = (Motorcycle) o;

        if (bodyType != that.bodyType) return false;
        return motorcycleManufacturer == that.motorcycleManufacturer;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (bodyType != null ? bodyType.hashCode() : 0);
        result = 31 * result + (motorcycleManufacturer != null ? motorcycleManufacturer.hashCode() : 0);
        return result;
    }
}