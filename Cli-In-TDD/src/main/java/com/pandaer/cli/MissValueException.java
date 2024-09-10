package com.pandaer.cli;

public class MissValueException extends RuntimeException {
    private final String option;

    public MissValueException(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
