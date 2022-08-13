package com.example;

import com.example.model.product.Product;
import com.example.util.ProductUtils;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductUtils utils = ProductUtils.getInstance();
        List<Product> products = new ArrayList<>();
        products.add(utils.generateRandomProduct());
        products.add(utils.generateRandomProduct());
        products.add(utils.generateRandomProduct());
        products.add(utils.generateRandomProduct());
        products.add(utils.generateRandomProduct());
        products.add(utils.generateRandomProduct());
        products.add(utils.generateRandomProduct());
        products.forEach(utils::saveProduct);

        utils.getAll().forEach(System.out::println);
        System.out.println("notifications sent: " + utils.filterNotifiableProductsAndSendNotifications());
    }
}