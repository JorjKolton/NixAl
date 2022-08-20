package com.ssobchenko.service;

import com.ssobchenko.model.Customer;

import java.util.Random;

public class PersonService {
    private static final Random RANDOM = new Random();

    public static Customer createRandomCustomer() {
        return new Customer.Builder(RANDOM.nextInt(10, 45)).build();
    }
}