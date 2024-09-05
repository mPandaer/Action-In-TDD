package com.pandaer.cli;

import java.util.List;

class IntegerOptionParser implements OptionParser {

    @Override
    public Object parse(List<String> argList, String flag) {
        int index = argList.indexOf(flag);
        return Integer.parseInt(argList.get(index + 1));
    }
}
