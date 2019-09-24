package com.justplay1994.github.netty.tcp.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.Charset;

/**
 * @author huangzezhou
 * @date 2019/9/24
 */

public class TcpClient {

    public static void main(String[] args) throws Exception {
        // 223.71.98.214:6006
        // $721,京ABY721,0,C01,,$

        new TcpClient().connect("127.0.0.1", 28301);
//        new TcpClient().connect("10.173.134.104", 8401);
    }

    public void connect(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("logging",new LoggingHandler(LogLevel.DEBUG));
                            socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                            socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
                            socketChannel.pipeline().addLast("my_handler",new TcpClientHandler());
                        }
                    });
            //发起异步连接操作
            ChannelFuture f = b.connect(host, port).sync();

            f.channel().writeAndFlush("$721,京ABY721,0,C01,,$");
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        }finally {
            //优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }
    }

}
