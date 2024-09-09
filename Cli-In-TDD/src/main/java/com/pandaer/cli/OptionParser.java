package com.pandaer.cli;

import java.util.List;

interface OptionParser<T> {
    T parse(List<String> argList, String flag);
}
