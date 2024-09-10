package com.pandaer.cli;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

class SingleValuedOptionParser<T> implements OptionParser<T> {

    Function<String, T> valueParser;
    T defaultValue;

    public SingleValuedOptionParser(T defaultValue,Function<String, T> valueParser) {
        this.valueParser = valueParser;
        this.defaultValue = defaultValue;
    }

    @Override
    public T parse(List<String> argList, String flag) {
        int index = argList.indexOf(flag);
        if (index == -1) return defaultValue;
        int nextFlagIndex = IntStream.range(index + 1, argList.size())
                .filter(it -> argList.get(it).startsWith("-")).findFirst().orElse(argList.size());

        List<String> values = argList.subList(index + 1, nextFlagIndex);
        if (values.size() < 1) throw new MissValueException(flag);
        if (values.size() > 1) throw new TooManyArgumentsException(flag);
        String value = argList.get(index + 1);
        return valueParser.apply(value);
    }

}
