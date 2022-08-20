package com.ssobchenko.model.device;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public abstract class Device {
    protected final String id;
    protected String series;
    protected String screenType;
    protected int price;

    protected Device(String series, String screenType, int price) {
        this.id = UUID.randomUUID().toString();
        this.series = series;
        this.screenType = screenType;
        this.price = price;
    }
}