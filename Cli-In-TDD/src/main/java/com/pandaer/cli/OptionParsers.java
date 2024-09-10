package com.pandaer.cli;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

class OptionParsers {


    public static OptionParser<Boolean> bool() {
        return ((argList, flag) -> values(argList,flag,0).map(it -> true).orElse(false));
    }

    public static <T> OptionParser<T> unary(T defaultValue, Function<String, T> valueParser) {
        return ((argList, flag) -> values(argList, flag, 1).map(it -> parseValue(it.get(0), valueParser)).orElse(defaultValue));
    }


    private static Optional<List<String>> values(List<String> argList, String flag, int expectSize) {
        int index = argList.indexOf(flag);
        if (index == -1) {
            return Optional.empty();
        }else {
            List<String> values = values(argList, index);
            if (values.size() < expectSize) throw new MissValueException(flag);
            if (values.size() > expectSize) throw new TooManyArgumentsException(flag);
           return Optional.of(values);
        }
    }

    private static <T> T parseValue(String value, Function<String, T> valueParser) {
        return valueParser.apply(value);
    }

    private static List<String> values(List<String> argList, int index) {
        return argList.subList(index + 1, IntStream.range(index + 1, argList.size())
                .filter(it -> argList.get(it).startsWith("-")).findFirst().orElse(argList.size()));
    }

}
