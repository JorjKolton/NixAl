package com.ssobchenko.exeption;

public class InvalidLineException extends Exception {

    public InvalidLineException(String message, String line) {
        super(message + " {" + line + "}");
    }
}