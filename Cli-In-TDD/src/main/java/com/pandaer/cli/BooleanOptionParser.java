package com.pandaer.cli;

import java.util.List;

class BooleanOptionParser implements OptionParser {

    @Override
    public Object parse(List<String> argList, String flag) {
        return argList.contains(flag);
    }
}
