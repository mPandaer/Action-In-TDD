package com.pandaer.cli;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

class OptionParsers {


    public static OptionParser<Boolean> bool() {
        return ((argList, flag) -> getArgumentValueFromArguments(argList,flag,0).map(it -> true).orElse(false));
    }

    public static <T> OptionParser<T> unary(T defaultValue, Function<String, T> valueParser) {
        return ((argList, flag) -> getArgumentValueFromArguments(argList, flag, 1).map(it -> parseValue(it.get(0), valueParser)).orElse(defaultValue));
    }

    public static <T> OptionParser<T[]> list(IntFunction<T[]> generator, Function<String, T> valueParser) {
       return ((argList, flag) -> getArgumentValueFromArguments(argList,flag)
               .map(it -> it.stream()
                       .map(value -> parseValue(value,valueParser)).toArray(generator))
               .orElse(generator.apply(0)));
    }


    // 从参数列表中获取对应Flag的参数值
    private static Optional<List<String>> getArgumentValueFromArguments(List<String> argList, String flag) {
        int index = argList.indexOf(flag);
        return Optional.ofNullable( index == -1 ? null: doGetArgumentValueFromArguments(argList,index));
    }

    // 从参数列表中获取对应Flag的参数值
    private static Optional<List<String>> getArgumentValueFromArguments(List<String> argList, String flag, int expectSize) {
        return getArgumentValueFromArguments(argList,flag).map(it -> checkSize(flag,expectSize,it));
    }

    private static List<String> checkSize(String flag, int expectSize, List<String> values) {
        if (values.size() < expectSize) throw new MissValueException(flag);
        if (values.size() > expectSize) throw new TooManyArgumentsException(flag);
        return values;
    }

    // 将参数值解析为对应类型的值
    private static <T> T parseValue(String value, Function<String, T> valueParser) {
        try {
            return valueParser.apply(value);
        }catch (Exception e) {
            throw new IllegalValueException(value);
        }
    }

    // 将参数值从参数列表中提取出来
    private static List<String> doGetArgumentValueFromArguments(List<String> argList, int index) {
        return argList.subList(index + 1, IntStream.range(index + 1, argList.size())
                .filter(it -> argList.get(it).matches("^-[a-zA-Z-]+$")).findFirst().orElse(argList.size()));
    }

}
