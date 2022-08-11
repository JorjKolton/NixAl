package com.example.model.product;

import lombok.Data;

@Data
public abstract class Product implements IInfo {
    protected long id;
    protected boolean available;
    protected String title;
    protected double price;

    public String getBasicInfo() {
        return "Product{" +
                "id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}