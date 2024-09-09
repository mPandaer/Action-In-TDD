package com.pandaer.cli;

import java.util.List;
import java.util.function.Function;

class SingleValuedOptionParser<T> implements OptionParser<T> {

    Function<String, T> valueParser;

    public SingleValuedOptionParser(Function<String, T> valueParser) {
        this.valueParser = valueParser;
    }

    @Override
    public T parse(List<String> argList, String flag) {
        int index = argList.indexOf(flag);
        String value = argList.get(index + 1);
        return valueParser.apply(value);
    }

}
