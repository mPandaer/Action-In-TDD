package com.pandaer.cli;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArgsTest {

    // -l -p 8080 -d /usr/logs

    // [-l] [-p,8080] [-d /usr/logs]
    // map
    // index

    @Test
    void should() {
        Options options = Args.parse(Options.class,"-l","-p","8080","-d","/usr/logs");
        System.out.println(options.logging());
        System.out.println(options.port());
    }

    @Test
    void should_example_1() {
        Options options = Args.parse(Options.class,"-l","-p","8080","-d","/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080,options.port());
        assertEquals("/usr/logs",options.dir());
    }

    @Test
    void should_example_2() {
        ListOptions options = Args.parse(ListOptions.class,"-l","-p","8080","-d","/usr/logs");
        assertEquals(new String[]{"this","is","a","list"},options.group());
        assertEquals(new int[]{1,2,3},options.decimals());
    }



    static record Options(@Option("l") boolean logging,@Option("p")int port,@Option("d")String dir){}

    static record ListOptions(@Option("g") String[] group,@Option("d") int[] decimals){}

}
