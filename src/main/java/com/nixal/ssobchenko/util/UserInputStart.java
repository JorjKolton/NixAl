package com.nixal.ssobchenko.util;

import com.nixal.ssobchenko.command.Action;
import com.nixal.ssobchenko.command.Command;

import java.util.List;

public abstract class UserInputStart {
    private  static final Action[] actions = Action.values();
    private static final List<String> names = Action.getNames();
    static Command command;

    public static void start() {
        do {
            command = executeCommand();
        } while (command != null);
    }

    private static Command executeCommand() {
        int userInput = UserInputUtil.getUserInput("What you want:", names);
        final Action action = actions[userInput];
        return action.execute();
    }
}