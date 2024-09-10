package com.pandaer.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class SingleValuedOptionParserTest {
    // SingleValuedOptionParser
    // bad path
    //  -p 8080 9090
    @Test
    void should_throw_too_many_arguments_exception_for_parse_single_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new SingleValuedOptionParser<>(0, Integer::parseInt).parse(Arrays.asList("-p", "8080", "9090"), "-p");
        });
        assertEquals("-p",e.getOption());
    }

    // -p
    @ParameterizedTest
    @ValueSource(strings = {"-p -l","-p"})
    void should_throw_too_miss_value_exception_for_parse_single_option(String args) {
        MissValueException e = assertThrows(MissValueException.class, () -> {
            new SingleValuedOptionParser<>(0, Integer::parseInt).parse(Arrays.asList(args.split(" ")), "-p");
        });
        assertEquals("-p",e.getOption());
    }


    // default value
    @Test
    void should_get_default_value_for_parse_int_option() {
        Function<String,Object> whatever = (it) -> null;
        Object defaultValue = new Object();
        Object value = new SingleValuedOptionParser<>( defaultValue, whatever).parse(Arrays.asList(), "-p");
        assertSame(defaultValue,value);
    }

    // happy path
    @Test
    void should_get_default_value_for_parse_string_option() {
        Object parsed = new Object();
        Function<String,Object> parse = (it) -> parsed;
        Object whatever = new Object();
        Object value = new SingleValuedOptionParser<>(whatever, parse).parse(Arrays.asList("-d","/usr/logs"), "-d");
        assertSame(parsed,value);
    }
}