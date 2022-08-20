package com.ssobchenko.model.device;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class Television extends Device {
    private final int diagonal;
    private final String country;


    private Television(Builder builder) {
        super(builder.series, builder.screenType, builder.price);
        this.diagonal = builder.diagonal;
        this.country = builder.country;
    }

    public static class Builder {
        private final String series;
        private final String screenType;
        private final String country;
        private int diagonal;
        private int price;

        public Builder(String series, String screenType, String country) {
            this.series = series;
            this.screenType = screenType;
            this.country = country;
        }

        public Builder setPrice(int price) {
            if (price < 0) {
                throw new IllegalArgumentException("Price can't be less than zero");
            }
            this.price = price;
            return this;
        }

        public Builder setDiagonal(int diagonal) {
            if (price < 0) {
                throw new IllegalArgumentException("Diagonal can't be less than zero");
            }
            this.diagonal = diagonal;
            return this;
        }

        public Television build() {
            if (series == null || screenType == null || country == null) {
                throw new IllegalArgumentException("Series, screen type or country can't be null");
            }
            return new Television(this);
        }
    }

    @Override
    public String toString() {
        return "Television{" +
                "id='" + id + '\'' +
                ", series='" + series + '\'' +
                ", screenType='" + screenType + '\'' +
                ", price=" + price +
                ", diagonal=" + diagonal +
                ", country='" + country + '\'' +
                '}';
    }
}