package com.pandaer.cli;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ArgsTest {

    //完整的测试例子
        // -l -p 8080 -d /usr/logs
        // -g this is a list -d 1 2 3

    //实现思路
        // [-l] [-p,8080] [-d /usr/logs]
        // map
        // index

    // 任务拆分变成待办列表 尽快让测试通过
    // 简单情况 Single Args

    // TODO bool -l
    static record BoolOption(@Option("l") boolean logging){}
    @Test
    void should_set_bool_option_to_true_if_flag_present() {
        BoolOption boolOption = Args.parse(BoolOption.class, "-l");
        assertTrue(boolOption.logging());
    }

    @Test
    void should_set_bool_option_to_true_if_flag_not_present() {
        BoolOption boolOption = Args.parse(BoolOption.class);
        assertFalse(boolOption.logging());
    }


        // TODO int -p 8080
        // TODO string -d /usr/logs
    // multi options
        // TODO  -l -p 8080 -d /usr/logs
    // bad path
        // TODO -bool -l t
        // TODO -int -p or -p 8080 9090
        // TODO -string -d or -d /usr/logs /var/logs
    // default value
        // TODO -bool false
        // TODO -int 0
        // TODO -string ""







    @Disabled
    @Test
    void should_example_1() {
        Options options = Args.parse(Options.class,"-l","-p","8080","-d","/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080,options.port());
        assertEquals("/usr/logs",options.dir());
    }

    @Disabled
    @Test
    void should_example_2() {
        ListOptions options = Args.parse(ListOptions.class,"-g","this","is","a","list","-d","1","2","3");
        assertEquals(new String[]{"this","is","a","list"},options.group());
        assertEquals(new int[]{1,2,3},options.decimals());
    }



    static record Options(@Option("l") boolean logging,@Option("p")int port,@Option("d")String dir){}

    static record ListOptions(@Option("g") String[] group,@Option("d") int[] decimals){}

}
