package com.justplay1994.github.netty.NIO;

public class NIOClient {
    public static void main(String[] args){
        new Thread(new NIOClientHandle("127.0.0.1",8080), "NIOClient-001").start();
    }
}
