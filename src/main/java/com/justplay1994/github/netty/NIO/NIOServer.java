package com.justplay1994.github.netty.NIO;

public class NIOServer {
    public static void main(String[] args){
        int port = 8080;
        MultiplexerNIOServer nioServer = new MultiplexerNIOServer(port);

        new Thread(nioServer, "NIO-MultiplexerNIOServer-001").start();
    }
}
