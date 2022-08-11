package com.example.model.product;

import com.example.model.product.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class ProductBundle extends Product {
    protected int amount;

    @Override
    public String getBasicInfo() {
        return "ProductBundle{" +
                "id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", amountInBundle=" + amount +
                '}';
    }
}