package com.nixal.ssobchenko.model.vehicle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Invoices")
public class Invoice {
    @Id
    private String id;
    private LocalDateTime created;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vehicle> vehicles;
    private BigDecimal price;

    private Invoice(Builder builder) {
        id = builder.id;
        created = builder.created;
        vehicles = builder.vehicles;
        price = builder.price;
    }

    public static class Builder {
        private String id;
        private LocalDateTime created;
        private final List<Vehicle> vehicles;
        private BigDecimal price;

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
            price = vehicles.stream()
                    .map(Vehicle::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Invoice invoice = new Invoice(this);
            vehicles.forEach(vehicle -> vehicle.setInvoice(invoice));

            return invoice;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invoice invoice = (Invoice) o;

        if (id != null ? !id.equals(invoice.id) : invoice.id != null) return false;
        if (created != null ? !created.equals(invoice.created) : invoice.created != null) return false;
        return price != null ? price.equals(invoice.price) : invoice.price == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}