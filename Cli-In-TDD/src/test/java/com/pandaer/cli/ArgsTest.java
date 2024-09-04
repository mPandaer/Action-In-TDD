package com.pandaer.cli;

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

    static record Options(@Option("l") boolean logging,@Option("p")int port,@Option("d")String dir){}


}
