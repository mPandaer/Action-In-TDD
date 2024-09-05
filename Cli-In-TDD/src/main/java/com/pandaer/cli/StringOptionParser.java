package com.pandaer.cli;

import java.util.List;

class StringOptionParser implements OptionParser {

    @Override
    public Object parse(List<String> argList, String flag) {
        int index = argList.indexOf(flag);
        return argList.get(index + 1);
    }
}
