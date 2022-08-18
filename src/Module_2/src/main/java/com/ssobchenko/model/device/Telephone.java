package com.ssobchenko.model.device;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class Telephone extends Device {
    private final String model;

    private Telephone(Builder builder) {
        super(builder.series, builder.screenType, builder.price);
        this.model = builder.model;
    }

    public static class Builder {
        private final String series;
        private final String screenType;
        private final String model;
        private int price;

        public Builder(String series, String screenType, String model) {
            this.series = series;
            this.screenType = screenType;
            this.model = model;
        }

        public Builder setPrice(int price) {
            if (price < 0) {
                throw new IllegalArgumentException("Price can't be less than zero");
            }
            this.price = price;
            return this;
        }

        public Telephone build() {
            if (series == null || screenType == null || model == null) {
                throw new IllegalArgumentException("Series, screen type or model can't be null");
            }
            return new Telephone(this);
        }
    }

    @Override
    public String toString() {
        return "Telephone{" +
                "id='" + id + '\'' +
                ", series='" + series + '\'' +
                ", screenType='" + screenType + '\'' +
                ", price=" + price +
                ", model='" + model + '\'' +
                '}';
    }
}