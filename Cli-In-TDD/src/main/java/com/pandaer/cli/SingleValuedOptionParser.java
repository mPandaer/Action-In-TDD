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
        if (index + 1 >= argList.size() || argList.get(index+1).startsWith("-")) throw new MissValueException(flag);
        if (index + 2 < argList.size() &&
                !(argList.get(index + 2).startsWith("-"))) throw new TooManyArgumentsException(flag);
        String value = argList.get(index + 1);
        return valueParser.apply(value);
    }

}
