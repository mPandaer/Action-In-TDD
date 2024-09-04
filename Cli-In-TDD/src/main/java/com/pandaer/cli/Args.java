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
            Object[] values = Arrays.stream(constructor.getParameters()).map(it -> parseOptions(it, argList)).toArray();
            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static Object parseOptions(Parameter parameter, List<String> argList) {
        Option option = parameter.getAnnotation(Option.class);
        Object value = null;
        String flag = "-" + option.value();
        if (parameter.getType() == boolean.class) {
            value = argList.contains(flag);
        }

        if (parameter.getType() == int.class) {
            int index = argList.indexOf(flag);
            value = Integer.parseInt(argList.get(index + 1));
        }

        if (parameter.getType() == String.class) {
            int index = argList.indexOf(flag);
            value = argList.get(index + 1);
        }
        return value;
    }
}
