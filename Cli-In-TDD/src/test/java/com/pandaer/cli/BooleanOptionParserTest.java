package com.pandaer.cli;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BooleanOptionParserTest {

    // BooleanOptionParser
    //bad path
    @Test
    void should_not_accept_extra_argument_for_boolean_option() {
        TooManyArgumentsException exception = assertThrows(TooManyArgumentsException.class, () -> {
            new BooleanOptionParser().parse(Arrays.asList("-l", "t"), "-l");
        });

        assertEquals("-l",exception.getOption());
    }


    //default value
    // -bool false
    @Test
    void should_set_default_value_false_for_boolean_option() {
        assertFalse(new BooleanOptionParser().parse(Arrays.asList(), "-l"));

    }

    // happy path
    @Test
    void should_parse_bool_for_boolean_option() {
        assertTrue(new BooleanOptionParser().parse(Arrays.asList("-l"), "-l"));
    }
}