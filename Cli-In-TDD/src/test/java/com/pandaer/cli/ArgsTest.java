package com.pandaer.cli;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ArgsTest {

    //完整的测试例
        // -g this is a list -d 1 2 3


    static record MultiOptions(@Option("l") boolean logging, @Option("p")int port, @Option("d")String dir){}
    @Test
    void should_parse_multi_option() {
        MultiOptions multiOptions = Args.parse(MultiOptions.class,"-l","-p","8080","-d","/usr/logs");
        assertTrue(multiOptions.logging());
        assertEquals(8080, multiOptions.port());
        assertEquals("/usr/logs", multiOptions.dir());

    }


    @Test
    void should_throw_illegal_option_exception_if_without_annotation() {
        IllegalOptionException e = assertThrows(IllegalOptionException.class,() -> {
            Args.parse(MultiOptionsWithoutAnnotation.class,"-l","-p","8080","-d","/usr/logs");
        });
        assertEquals("port",e.getParameter());
    }

    static record MultiOptionsWithoutAnnotation(@Option("l") boolean logging, int port, @Option("d")String dir){}





    @Test
    void should_example_2() {
        ListOptions options = Args.parse(ListOptions.class,"-g","this","is","a","list","-d","1","-2","3");
        assertArrayEquals(new String[]{"this","is","a","list"},options.group());
        assertArrayEquals(new Integer[]{1,-2,3},options.decimals());
    }





    static record ListOptions(@Option("g") String[] group,@Option("d") Integer[] decimals){}

}
