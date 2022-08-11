package com.example.util;

import com.example.model.product.NotifiableProduct;
import com.example.model.product.Product;
import com.example.model.product.ProductBundle;
import com.example.repository.ProductRepository;

import java.util.List;
import java.util.Random;

public class ProductUtils {
    private final ProductRepository repository;
    private static final Random RANDOM = new Random();

    private static ProductUtils instance;

    private ProductUtils() {
        repository = ProductRepository.getInstance();
    }

    public static ProductUtils getInstance() {
        if (instance == null) {
            instance = new ProductUtils();
        }
        return instance;
    }

    public void saveProduct(Product product) {
        repository.save(product);
    }

    public int filterNotifiableProductsAndSendNotifications() {
        int notifications = 0;
        for (Product product : repository.getAll()) {
            if (product.getClass().equals(NotifiableProduct.class)) {
                //sending some notifications here
                notifications++;
            }
        }
        return notifications;
    }

    public List<Product> getAll() {
        return repository.getAll();
    }

    public Product generateRandomProduct() {
        if (RANDOM.nextBoolean()) {
            ProductBundle productBundle = new ProductBundle();
            productBundle.setAmount(RANDOM.nextInt(15));
            productBundle.setAvailable(RANDOM.nextBoolean());
            productBundle.setPrice(RANDOM.nextDouble());
            productBundle.setId(RANDOM.nextLong());
            productBundle.setTitle(RANDOM.nextFloat() + "" + RANDOM.nextDouble());
            return productBundle;
        } else {
            NotifiableProduct product = new NotifiableProduct();
            product.setId(RANDOM.nextLong());
            product.setTitle(RANDOM.nextFloat() + "" + RANDOM.nextDouble());
            product.setAvailable(RANDOM.nextBoolean());
            product.setChannel(RANDOM.nextBoolean() + "" + RANDOM.nextDouble());
            product.setPrice(RANDOM.nextDouble());
            return product;
        }
    }
}