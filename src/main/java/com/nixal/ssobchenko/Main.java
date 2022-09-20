package com.nixal.ssobchenko;

import com.nixal.ssobchenko.model.Factory;

public class Main {
    private static final Factory FACTORY = new Factory();

    public static void main(String[] args) {
        FACTORY.startFactory();
    }
}