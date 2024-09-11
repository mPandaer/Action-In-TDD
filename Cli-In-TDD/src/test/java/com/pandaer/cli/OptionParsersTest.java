package com.pandaer.cli;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class OptionParsersTest {

    @Nested
    class UnaryOptionParserTest {
        // SingleValuedOptionParser
        // bad path
        //  -p 8080 9090
        @Test
        void should_throw_too_many_arguments_exception_for_parse_single_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
                OptionParsers.unary(0, Integer::parseInt).parse(Arrays.asList("-p", "8080", "9090"), "-p");
            });
            assertEquals("-p",e.getOption());
        }

        // -p
        @ParameterizedTest
        @ValueSource(strings = {"-p -l","-p"})
        void should_throw_too_miss_value_exception_for_parse_single_option(String args) {
            MissValueException e = assertThrows(MissValueException.class, () -> {
                OptionParsers.unary(0, Integer::parseInt).parse(Arrays.asList(args.split(" ")), "-p");
            });
            assertEquals("-p",e.getOption());
        }


        // default value
        @Test
        void should_get_default_value_for_parse_int_option() {
            Function<String,Object> whatever = (it) -> null;
            Object defaultValue = new Object();
            Object value = OptionParsers.unary( defaultValue, whatever).parse(Arrays.asList(), "-p");
            assertSame(defaultValue,value);
        }

        // happy path
        @Test
        void should_get_default_value_for_parse_string_option() {
            Object parsed = new Object();
            Function<String,Object> parse = (it) -> parsed;
            Object whatever = new Object();
            Object value = OptionParsers.unary(whatever, parse).parse(Arrays.asList("-d","/usr/logs"), "-d");
            assertSame(parsed,value);
        }

        // bad path format exception
        @Test
        void should_throw_exception_for_parse_error_num_format_option() {
            IllegalValueException exception = assertThrows(IllegalValueException.class, () -> {
                OptionParsers.unary(0, Integer::parseInt).parse(Arrays.asList("-p", "a"), "-p");
            });

            assertEquals("a",exception.getValue());
        }
    }

    @Nested
    class BooleanOptionParserTest {

        // BooleanOptionParser
        //bad path
        @Test
        void should_not_accept_extra_argument_for_boolean_option() {
            TooManyArgumentsException exception = assertThrows(TooManyArgumentsException.class, () -> {
                OptionParsers.bool().parse(Arrays.asList("-l", "t"), "-l");
            });

            assertEquals("-l",exception.getOption());
        }


        //default value
        // -bool false
        @Test
        void should_set_default_value_false_for_boolean_option() {
            assertFalse(OptionParsers.bool().parse(Arrays.asList(), "-l"));

        }

        // happy path
        @Test
        void should_parse_bool_for_boolean_option() {
            assertTrue(OptionParsers.bool().parse(Arrays.asList("-l"), "-l"));
        }
    }

    @Nested
    class ListOptionParserTest {
        // -g this is a list {"this","is","a","list"}
        @Test
        void should_parse_array_for_parse_list_option() {
            String[] parsedArray = OptionParsers.list(String[]::new,String::valueOf).parse(Arrays.asList("-g", "this", "is", "a", "list"), "-g");
            assertArrayEquals(new String[]{"this","is","a","list"},parsedArray);
        }

        // default value
        @Test
        void should_empty_array_for_parse_list_option() {
            String[] parsedArray = OptionParsers.list(String[]::new,String::valueOf).parse(Arrays.asList(), "-g");
            assertEquals(0,parsedArray.length);
        }


        // bad path -d a throw exception
        @Test
        void should_throw_exception_for_parse_list_option() {
            Function<String,String> parser = (it) -> {throw new RuntimeException();};
            IllegalValueException exception = assertThrows(IllegalValueException.class, () -> {
                OptionParsers.list(String[]::new, parser).parse(Arrays.asList("-g", "this", "is", "a", "list"), "-g");
            });

            assertEquals(exception.getValue(),"this");
        }
    }
}