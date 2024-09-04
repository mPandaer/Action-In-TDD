package com.pandaer.cli;


import java.lang.reflect.Constructor;

public class Args {

    public static <T> T parse(Class<T> clazz, String... args) {
        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        try {
            return (T) constructor.newInstance(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
