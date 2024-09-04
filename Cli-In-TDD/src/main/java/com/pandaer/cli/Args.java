package com.pandaer.cli;


import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {

    public static <T> T parse(Class<T> clazz, String... args) {

        try {
            List<String> argList = Arrays.stream(args).toList();
            Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
            Parameter parameter = constructor.getParameters()[0];
            Option option = parameter.getAnnotation(Option.class);
            return (T) constructor.newInstance(argList.contains("-" + option.value()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
