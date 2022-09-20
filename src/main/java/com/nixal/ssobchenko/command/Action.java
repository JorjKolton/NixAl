package com.nixal.ssobchenko.command;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum Action {
    FACTORY("Start factory", new FactoryS()),
    CREATE("Create vehicle", new Create()),
    CHANGE("Change vehicle", new Change()),
    PRINT("Print vehicles", new Print()),
    DELETE("Delete vehicle", new Delete()),
    EXIT("Exit", null);

    private final String name;
    private final Command command;

    Action(String name, Command command) {
        this.name = name;
        this.command = command;
    }

    public Command execute() {
        if (command != null) {
            command.execute();
        }
        return command;
    }

    public static List<String> getNames() {
        final Action[] actions = Action.values();
        final List<String> names = new ArrayList<>(actions.length);
        for (Action action : actions) {
            names.add(action.getName());
        }
        return names;
    }
}