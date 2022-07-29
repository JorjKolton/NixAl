package com.nixal.ssobchenko.util;

import com.nixal.ssobchenko.command.Action;
import com.nixal.ssobchenko.command.Command;

import java.util.List;

public class UserInputStart {
    final Action[] actions = Action.values();
    final List<String> names = Action.getNames();
    Command command;

    public void start() {
        do {
            command = executeCommand(actions, names);
        } while (command != null);
    }

    private static Command executeCommand(Action[] actions, List<String> names) {
        int userInput = UserInputUtil.getUserInput("What you want:", names);
        final Action action = actions[userInput];
        return action.execute();
    }
}