package com.pandaer.cli;


import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        Class<?> type = parameter.getType();
        return PARSERS.get(type).parse(argList, "-" + parameter.getAnnotation(Option.class).value());
    }

    private static Map<Class<?>, OptionParser> PARSERS = Map.of(boolean.class, new BooleanOptionParser(), int.class, new IntegerOptionParser(), String.class, new StringOptionParser());


}
