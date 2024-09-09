package com.pandaer.cli;

import java.util.List;

class BooleanOptionParser implements OptionParser<Boolean> {

    @Override
    public Boolean parse(List<String> argList, String flag) {
        int index = argList.indexOf(flag);
        if (index + 1 < argList.size() &&
                !argList.get(index + 1).startsWith("-")) throw new TooManyArgumentsException(flag);
        return index != -1;
    }
}
