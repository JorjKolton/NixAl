package com.nixal.ssobchenko.command;

import com.nixal.ssobchenko.model.Factory;

public class FactoryS implements Command {
    private static final Factory FACTORY = new Factory();

    @Override
    public void execute() {
        FACTORY.startFactory();
    }
}