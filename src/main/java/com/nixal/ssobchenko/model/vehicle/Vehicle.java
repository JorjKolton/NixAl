package com.nixal.ssobchenko.model.vehicle;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Vehicle {
    @Id
    @SerializedName("_id")
    protected String id;
    protected int model;
    protected BigDecimal price;
    @Column(name = "vehicle_type")
    @Enumerated(EnumType.STRING)
    protected VehicleType type;
    @Transient
    protected List<String> details;
    @Transient
    protected String currency;
    @Transient
    protected int count;
    protected LocalDateTime created;
    @Transient
    protected Engine engine;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    protected Invoice invoice;

    protected Vehicle(String id, int model, BigDecimal price, VehicleType type, LocalDateTime created) {
        this.id = id;
        this.model = model;
        this.price = price;
        this.type = type;
        currency = "₴";
        this.created = created;
        engine = new Engine();
    }

    public static class IdComparator implements Comparator<Vehicle> {

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }

    public static class PriceComparator implements Comparator<Vehicle> {

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return o1.getPrice().compareTo(o2.getPrice());
        }
    }

    public static class ModelComparator implements Comparator<Vehicle> {

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return Integer.compare(o1.getModel(), o2.getModel());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (model != vehicle.model) return false;
        if (!id.equals(vehicle.id)) return false;
        if (!price.equals(vehicle.price)) return false;
        return type == vehicle.type;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + model;
        result = 31 * result + price.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}