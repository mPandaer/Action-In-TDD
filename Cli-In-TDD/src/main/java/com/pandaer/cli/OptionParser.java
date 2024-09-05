package com.pandaer.cli;

import java.util.List;

interface OptionParser {
    Object parse(List<String> argList, String flag);
}
