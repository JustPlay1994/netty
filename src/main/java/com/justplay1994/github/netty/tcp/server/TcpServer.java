package com.justplay1994.github.netty.tcp.server;

import com.justplay1994.github.netty.tcp.client.TcpClient;
import com.justplay1994.github.netty.tcp.client.TcpClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author huangzezhou
 * @date 2019/9/24
 */

public class TcpServer {

    public static void main(String[] args) throws Exception {
        // 223.71.98.214:6006
        // $721,京ABY721,0,C01,,$

        new TcpServer().connect(28301);
//        new TcpClient().connect("10.173.134.104", 8401);
    }

    public void connect(int port) throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss, worker).channel(NioServerSocketChannel.class)
                    //用它来建立新accept的连接
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("logging",new LoggingHandler(LogLevel.DEBUG));
                            ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                            ch.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
                            ch.pipeline().addLast("my_handler",new TcpServertHandler());
                        }
                    });

            //发起异步连接操作
            ChannelFuture f = server.bind(new InetSocketAddress("127.0.0.1", port)).sync().channel().closeFuture().sync();

//            f.channel().writeAndFlush("$721,京ABY721,0,C01,,$");
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        }finally {
            //优雅退出，释放NIO线程组
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
