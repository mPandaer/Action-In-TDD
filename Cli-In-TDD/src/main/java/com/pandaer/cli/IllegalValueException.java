package com.pandaer.cli;

public class IllegalValueException extends RuntimeException {
    private final String value;

    public IllegalValueException(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
