package com.nixal.ssobchenko.model.vehicle;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Invoice {
    private String id;
    private LocalDateTime created;
    private final List<Vehicle> vehicles;

    private Invoice(Builder builder) {
        id = builder.id;
        created = builder.created;
        vehicles = builder.vehicles;
    }

    public static class Builder {
        private String id;
        private LocalDateTime created;
        private final List<Vehicle> vehicles;

        public Builder(List<Vehicle> vehicles) {
            id = UUID.randomUUID().toString();
            created = LocalDateTime.now();
            this.vehicles = vehicles;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setCreated(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public Invoice build() {
            if (vehicles.isEmpty()) {
                throw new IllegalArgumentException("Invoice can't be empty");
            }
            return new Invoice(this);
        }
    }
}