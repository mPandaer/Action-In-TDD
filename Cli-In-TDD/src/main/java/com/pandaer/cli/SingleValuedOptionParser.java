package com.pandaer.cli;

import java.util.List;
import java.util.function.Function;

class SingleValuedOptionParser<T> implements OptionParser<T> {

    Function<String, T> valueParser;
    T defaultValue;

    private SingleValuedOptionParser(Function<String, T> valueParser) {
        this.valueParser = valueParser;
    }

    public SingleValuedOptionParser(T defaultValue,Function<String, T> valueParser) {
        this.valueParser = valueParser;
        this.defaultValue = defaultValue;
    }

    @Override
    public T parse(List<String> argList, String flag) {
        int index = argList.indexOf(flag);
        if (index == -1) return defaultValue;
        if (isReachEndOfList(argList, index) || isFollowAFlag(argList, index)) throw new MissValueException(flag);
        if (isSecondArgumentAFlag(argList, index)) throw new TooManyArgumentsException(flag);
        String value = argList.get(index + 1);
        return valueParser.apply(value);
    }

    private static boolean isSecondArgumentAFlag(List<String> argList, int index) {
        return index + 2 < argList.size() &&
                !(argList.get(index + 2).startsWith("-"));
    }

    private static boolean isFollowAFlag(List<String> argList, int index) {
        return argList.get(index + 1).startsWith("-");
    }

    private static boolean isReachEndOfList(List<String> argList, int index) {
        return index + 1 >= argList.size();
    }

}
